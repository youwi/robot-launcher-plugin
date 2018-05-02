package com.github.youwi.robotlauncher;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;

import java.util.HashMap;
import java.util.Map;

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
        if (file != null && file.getName().endsWith(".robot"))
            return true;
        if (sourceElement != null) {
            if (sourceElement.toString().startsWith("|")) {
                return true;
            }
        }

        return false;
    }

    /**
     * caching .....
     */
    static Map CACHE_MAP = new HashMap();

    @Override
    public boolean isConfigurationFromContext(RobotRunConfiguration runConfig, ConfigurationContext context) {
        Location location = context.getLocation();
        if (location == null) {
            return false;
        }

        VirtualFile file = location.getVirtualFile();

        if (file != null && file.getName().endsWith(".robot"))
            return true;
        if (file != null && file.getName().endsWith(".txt")) {
            if (CACHE_MAP.get(file.getName()) != null) {
                return true;
            }
            String allText = location.getPsiElement().getText().trim();
            String[] alllines = allText.split("\n");
            int tableCount = 0;
            for (String line : alllines) {
                if (line.trim().startsWith("|") && line.trim().endsWith("|")) {
                    tableCount++;
                    if (tableCount > 3) break;
                } else {
                    tableCount = 0;
                }
            }
            CACHE_MAP.put(file.getName(), true);
        }
        String lineText = location.getPsiElement().getText().trim();
        if (lineText.startsWith("|") && lineText.endsWith("|")) {
            //runConfig;
            runConfig.setProgramName("robot");
            runConfig.setName("sf");
            //runConfig.is
            return true;
        }
        String parentLineText = location.getPsiElement().getParent().getParent().getText();
        if (parentLineText.startsWith("|") && parentLineText.endsWith("|")) {
            //runConfig;
            runConfig.setProgramName("robot");
            return true;
        }


        return false;
    }

    public boolean isRobotScript() {
        return true;
    }
}
