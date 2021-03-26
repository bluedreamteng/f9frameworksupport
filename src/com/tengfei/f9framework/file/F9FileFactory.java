package com.tengfei.f9framework.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author ztf
 */
public class F9FileFactory {

    public static F9FileFactory getInstance() {
        return new F9FileFactory();
    }

    public F9File createFile(VirtualFile file, Project project){
        
    }
}
