package com.github.youwi.custummenu.extensions;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;

public class RunConfigProducer extends RunConfigurationProducer<RobotRunConfiguration> {
    public RunConfigProducer() {
        super(RobotRunConfigurationPluginType.getInstance());
    }

    @Override
    protected boolean setupConfigurationFromContext(RobotRunConfiguration runConfig, ConfigurationContext context, Ref<PsiElement> sourceElement) {
        Location location = context.getLocation();
        if (location == null) {
            return false;
        }

        VirtualFile file = location.getVirtualFile();
        if(file!=null &&file.getName().endsWith(".robot"))
            return true;
        if(sourceElement!=null){
            if(sourceElement.toString().startsWith("|")){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isConfigurationFromContext(RobotRunConfiguration runConfig, ConfigurationContext context) {
        Location location = context.getLocation();
        if (location == null) {
            return false;
        }

        VirtualFile file = location.getVirtualFile();
        if(file!=null &&file.getName().endsWith(".robot"))
            return true;
        return  false;
      //  return file != null && FileUtil.pathsEqual(file.getPath(), runConfig.getProgramName());
    }
    public boolean isRobotScript(){
        return  true;
    }
}
