// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.tengfei.f9framework.filesearch;

import com.intellij.ide.actions.searcheverywhere.AbstractGotoSEContributor;
import com.intellij.ide.actions.searcheverywhere.FoundItemDescriptor;
import com.intellij.ide.actions.searcheverywhere.PersistentSearchEverywhereContributorFilter;
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereDataKeys;
import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.ide.util.gotoByName.*;
import com.intellij.lang.javascript.JavaScriptFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.util.ProgressIndicatorUtils;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.IdeUICustomization;
import com.intellij.util.Processor;
import com.intellij.util.indexing.FindSymbolParameters;
import com.tengfei.f9framework.module.F9ModuleFacade;
import com.tengfei.f9framework.module.F9StandardModule;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Konstantin Bulenkov
 * @author Mikhail Sokolov
 */
public class UrlFilesSearchEveryWhere extends AbstractGotoSEContributor {
    private final GotoFileModel myModelForRenderer;
    private final PersistentSearchEverywhereContributorFilter<FileType> myFilter;

    static Pattern urlCompilePattern = Pattern.compile("^https?://\\w+:\\d+/([\\w-]+)((/\\w+)+$)");

    public UrlFilesSearchEveryWhere(@NotNull AnActionEvent event) {
        super(event);
        Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        myModelForRenderer = new GotoFileModel(project);
        myFilter = createFileTypeFilter(project);
    }

    @NotNull
    @Override
    public String getGroupName() {
        return "Url Files";
    }

    public String includeNonProjectItemsText() {
        return IdeUICustomization.getInstance().projectMessage("checkbox.include.non.project.files");
    }

    @Override
    public int getSortWeight() {
        return 200;
    }

    @Override
    public int getElementPriority(@NotNull Object element, @NotNull String searchPattern) {
        return super.getElementPriority(element, searchPattern) + 2;
    }

    @NotNull
    @Override
    protected FilteringGotoByModel<FileType> createModel(@NotNull Project project) {
        GotoFileModel model = new GotoFileModel(project);
        if (myFilter != null) {
            model.setFilterItems(myFilter.getSelectedElements());
        }
        return model;
    }

    @NotNull
    @Override
    public List<AnAction> getActions(@NotNull Runnable onChanged) {
        return doGetActions(includeNonProjectItemsText(), myFilter, onChanged);
    }

    @NotNull
    @Override
    public ListCellRenderer<Object> getElementsRenderer() {
        //noinspection unchecked
        return new SERenderer() {
            @NotNull
            @Override
            protected ItemMatchers getItemMatchers(@NotNull JList list, @NotNull Object value) {
                ItemMatchers defaultMatchers = super.getItemMatchers(list, value);
                if (!(value instanceof PsiFileSystemItem) || myModelForRenderer == null) {
                    return defaultMatchers;
                }

                return GotoFileModel.convertToFileItemMatchers(defaultMatchers, (PsiFileSystemItem) value, myModelForRenderer);
            }
        };
    }

    @Override
    public boolean processSelectedItem(@NotNull Object selected, int modifiers, @NotNull String searchText) {
        if (selected instanceof PsiFile) {
            VirtualFile file = ((PsiFile) selected).getVirtualFile();
            if (file != null && myProject != null) {
                Pair<Integer, Integer> pos = getLineAndColumn(searchText);
                OpenFileDescriptor descriptor = new OpenFileDescriptor(myProject, file, pos.first, pos.second);
                descriptor.setUseCurrentWindow(openInCurrentWindow(modifiers));
                if (descriptor.canNavigate()) {
                    descriptor.navigate(true);
                    return true;
                }
            }
        }

        return super.processSelectedItem(selected, modifiers, searchText);
    }

    @Override
    public Object getDataForItem(@NotNull Object element, @NotNull String dataId) {
        if (CommonDataKeys.PSI_FILE.is(dataId) && element instanceof PsiFile) {
            return element;
        }

        if (SearchEverywhereDataKeys.ITEM_STRING_DESCRIPTION.is(dataId) && element instanceof PsiFile) {
            String path = ((PsiFile) element).getVirtualFile().getPath();
            path = FileUtil.toSystemIndependentName(path);
            if (myProject != null) {
                String basePath = myProject.getBasePath();
                if (basePath != null) {
                    path = FileUtil.getRelativePath(basePath, path, '/');
                }
            }
            return path;
        }

        return super.getDataForItem(element, dataId);
    }


    @NotNull
    public static PersistentSearchEverywhereContributorFilter<FileType> createFileTypeFilter(@NotNull Project project) {
        List<FileType> items = new ArrayList<>();
        items.add(HtmlFileType.INSTANCE);
        items.add(JavaScriptFileType.INSTANCE);
        return new PersistentSearchEverywhereContributorFilter<>(items, GotoFileConfiguration.getInstance(project), FileType::getName,
                FileType::getIcon);
    }


    @Override
    public void fetchWeightedElements(@NotNull String pattern,
                                      @NotNull ProgressIndicator progressIndicator,
                                      @NotNull Processor<? super FoundItemDescriptor<Object>> consumer) {
        if (!urlCompilePattern.matcher(pattern).matches()) {
            return;
        }
        String urlPattern = pattern;
        String simplePattern = pattern.substring(pattern.lastIndexOf('/'));
        if (myProject == null) {
            return; //nowhere to search
        }
        if (!isEmptyPatternSupported() && simplePattern.isEmpty()) {
            return;
        }

        Runnable fetchRunnable = () -> {
            if (!isDumbAware() && DumbService.isDumb(myProject)) {
                return;
            }

            FilteringGotoByModel<?> model = createModel(myProject);
            if (progressIndicator.isCanceled()) {
                return;
            }

            PsiElement context = myPsiContext != null && myPsiContext.isValid() ? myPsiContext : null;
            ChooseByNamePopup popup = ChooseByNamePopup.createPopup(myProject, model, context);
            try {
                ChooseByNameItemProvider provider = popup.getProvider();
                GlobalSearchScope scope = Registry.is("search.everywhere.show.scopes")
                        ? (GlobalSearchScope) Objects.requireNonNull(myScopeDescriptor.getScope())
                        : null;

                boolean everywhere = scope == null ? myEverywhere : scope.isSearchInLibraries();
                if (scope != null && provider instanceof ChooseByNameInScopeItemProvider) {
                    FindSymbolParameters parameters = FindSymbolParameters.wrap(simplePattern, scope);
                    ((ChooseByNameInScopeItemProvider) provider).filterElementsWithWeights(popup, parameters, progressIndicator,
                            item -> processElement(progressIndicator, consumer, model,
                                    item.getItem(), item.getWeight(), urlPattern)
                    );
                }
                else if (provider instanceof ChooseByNameWeightedItemProvider) {
                    ((ChooseByNameWeightedItemProvider) provider).filterElementsWithWeights(popup, simplePattern, everywhere, progressIndicator,
                            item -> processElement(progressIndicator, consumer, model,
                                    item.getItem(), item.getWeight(), urlPattern)
                    );
                }
                else {
                    provider.filterElements(popup, simplePattern, everywhere, progressIndicator,
                            element -> processElement(progressIndicator, consumer, model, element,
                                    getElementPriority(element, simplePattern), urlPattern)
                    );
                }
            }
            finally {
                Disposer.dispose(popup);
            }
        };


        Application application = ApplicationManager.getApplication();
        if (application.isUnitTestMode() && application.isDispatchThread()) {
            fetchRunnable.run();
        }
        else {
            ProgressIndicatorUtils.yieldToPendingWriteActions();
            ProgressIndicatorUtils.runInReadActionWithWriteActionPriority(fetchRunnable, progressIndicator);
        }
    }

    private boolean processElement(@NotNull ProgressIndicator progressIndicator,
                                   @NotNull Processor<? super FoundItemDescriptor<Object>> consumer,
                                   FilteringGotoByModel<?> model, Object element, int degree, String pattern) {
        if (progressIndicator.isCanceled()) {
            return false;
        }
        if (!urlCompilePattern.matcher(pattern).matches()) {
            return false;
        }
        if (!(element instanceof PsiFile)) {
            return true;
        }
        PsiFile psiFile = (PsiFile) (element);
        String presentableUrl = psiFile.getVirtualFile().getPresentableUrl();
        VirtualFile matcherFileByUrl = getMatcherFileByUrl(pattern);
        if(matcherFileByUrl == null) {
            return false;
        }
        if(presentableUrl.equals(matcherFileByUrl.getPresentableUrl())) {
            consumer.process(new FoundItemDescriptor<>(element, degree));
            return false;
        }
        return true;
    }

    private VirtualFile getMatcherFileByUrl(String url) {
        VirtualFile customizeFileByUrl = getCustomizeFileByUrl(url);
        VirtualFile productFileByUrl = getProductFileByUrl(url);
        VirtualFile standardFileByUrl = getStandardFileByUrl(url);

        if(customizeFileByUrl != null) {
            return customizeFileByUrl;
        } else if (productFileByUrl != null) {
            return productFileByUrl;
        } else {
            return standardFileByUrl;
        }
    }


    private VirtualFile getStandardFileByUrl(String url) {
        Matcher matcher = urlCompilePattern.matcher(url);
        matcher.matches();
        String moduleName = matcher.group(1);
        String relativePath = matcher.group(2);
        if (!relativePath.contains(".")) {
            relativePath = relativePath + ".html";
        }
        F9StandardModule standardModuleByName = F9ModuleFacade.getInstance(myProject).findStandardModuleByName(moduleName);
        String standardFilePath = standardModuleByName.getWebRootPath() + relativePath;
        return VfsUtil.findFileByIoFile(new File(standardFilePath), false);
    }

    private VirtualFile getProductFileByUrl(String url) {
        Matcher matcher = urlCompilePattern.matcher(url);
        matcher.matches();
        String moduleName = matcher.group(1);
        String relativePath = matcher.group(2);
        if (!relativePath.contains(".")) {
            relativePath = relativePath + ".html";
        }
        F9StandardModule standardModuleByName = F9ModuleFacade.getInstance(myProject).findStandardModuleByName(moduleName);
        if (StringUtil.isEmpty(standardModuleByName.getProductCustomizeName())) {
            return null;
        }
        String productUrlPath = standardModuleByName.getWebRootPath() + "/" + standardModuleByName.getProductCustomizeName() + relativePath;
        return VfsUtil.findFileByIoFile(new File(productUrlPath), false);
    }

    private VirtualFile getCustomizeFileByUrl(String url) {
        Matcher matcher = urlCompilePattern.matcher(url);
        matcher.matches();
        String moduleName = matcher.group(1);
        String relativePath = matcher.group(2);
        if (!relativePath.contains(".")) {
            relativePath = relativePath + ".html";
        }
        F9StandardModule standardModuleByName = F9ModuleFacade.getInstance(myProject).findStandardModuleByName(moduleName);

        if (standardModuleByName.getCustomizeModuleList().isEmpty()) {
            return null;
        }
        String customizeModuleWebRootPath = standardModuleByName.getCustomizeModuleList().get(0).getWebRoot();
        if(StringUtil.isEmpty(customizeModuleWebRootPath)) {
            return null;
        }
        String customizeFilePath = customizeModuleWebRootPath + relativePath;
        return VfsUtil.findFileByIoFile(new File(customizeFilePath), false);
    }


}
