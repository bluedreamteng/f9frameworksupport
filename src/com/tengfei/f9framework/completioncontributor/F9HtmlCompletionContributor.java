package com.tengfei.f9framework.completioncontributor;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import com.tengfei.f9framework.completioncontributor.dictionary.F9HtmlDictionary;
import com.tengfei.f9framework.icons.F9Icons;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class F9HtmlCompletionContributor extends CompletionContributor {
    private F9HtmlCompletionContributor() {
        this.extend(CompletionType.BASIC, PlatformPatterns.psiElement().withParent(XmlAttributeValue.class), new XmlAttributeValueProvider());
        this.extend(CompletionType.BASIC, PlatformPatterns.psiElement().withParent(XmlAttribute.class), new XmlAttributeProvider());
    }


    private static class XmlAttributeValueProvider extends CompletionProvider<CompletionParameters> {

        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
            XmlAttribute xmlAttribute = PsiTreeUtil.getParentOfType(parameters.getPosition(), XmlAttribute.class);
            String xmlAttributeName = xmlAttribute != null ? xmlAttribute.getName() : null;
            if (StringUtil.isNotEmpty(xmlAttributeName)) {
                List<String> xmlAttributeValues = F9HtmlDictionary.getXmlAttributeValues().get(xmlAttributeName);
                for (String xmlAttributeValue : xmlAttributeValues) {
                    LookupElement lookupElement = PrioritizedLookupElement.withPriority(LookupElementBuilder.create(xmlAttributeValue).withRenderer(XmlAttributeValueRender.getInstance()), 1);
                    result.addElement(lookupElement);
                }
            }
        }
    }


    private static class XmlAttributeProvider extends CompletionProvider<CompletionParameters> {
        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
            List<String> xmlAttributeValues = F9HtmlDictionary.getXmlAttributes();
            for (String xmlAttributeValue : xmlAttributeValues) {
                LookupElement lookupElement = PrioritizedLookupElement.withPriority(LookupElementBuilder.create(xmlAttributeValue).withRenderer(XmlAttributeValueRender.getInstance()), 1);
                result.addElement(lookupElement);
            }
        }
    }

    private static class XmlAttributeValueRender extends LookupElementRenderer<LookupElement> {

        private static XmlAttributeValueRender instance = null;

        public static synchronized XmlAttributeValueRender getInstance() {
            if (instance == null) {
                instance = new XmlAttributeValueRender();
            }
            return instance;
        }

        @Override
        public void renderElement(LookupElement element, LookupElementPresentation presentation) {
            presentation.setIcon(F9Icons.SPRINGWEB);
            presentation.setItemTextBold(true);
            presentation.setItemText(element.getLookupString());
        }
    }
}
