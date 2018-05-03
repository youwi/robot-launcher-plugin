package com.github.youwi.robotlauncher;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;

import java.nio.file.Paths;
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
        runConfig.setInterpreterName(PluginConst.default_binary);

        VirtualFile file = location.getVirtualFile();
        if (file == null) {
            return false;
        }

        String pathFile = file.getPath().replace(context.getProject().getBasePath() + "/", "");
        String testCaseName = getTestCaseName(context);
        // robot file
        if (testCaseName.startsWith("***")) {
            return false;
        }

        runConfig.setProgramName(pathFile);
        runConfig.setInterpreterOptions(PluginConst.default_options + " -t \"" + testCaseName + "\"");
        runConfig.setName(testCaseName);
        runConfig.testCaseName = testCaseName;

        if (file.getName().endsWith(".robot"))
            return true;
        if (sourceElement != null) {
            if (sourceElement.toString().startsWith("|")) {
                return true;
            }
        }

        if (file.getName().endsWith(".robot"))
            return true;
        if (file.getName().endsWith(".txt")) {
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
            return true;
        }
        String parentLineText = location.getPsiElement().getParent().getParent().getText();
        if (parentLineText.startsWith("|") && parentLineText.endsWith("|")) {
            return true;
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

        String pathFile = file.getPath().replace(context.getProject().getBasePath() + "/", "");

        return FileUtil.pathsEqual(pathFile, runConfig.getProgramName()) && runConfig.testCaseName.equals(getTestCaseName((context)));
    }

    public String getTestCaseName(ConfigurationContext context) {
        Location location = context.getLocation();
        VirtualFile file = location.getVirtualFile();
        if (file.getName().endsWith(".robot")) {
            PsiElement e = location.getPsiElement();
            String text = e.getText();
            String pText1 = e.getParent().getText();
            String pText2 = e.getParent().getParent().getText();
            String pText3 = e.getParent().getParent().getParent().getText();
            if (pText1.contains("\n")) {
                return pText1.split("\n")[0];
            }
            if (pText2.contains("\n")) {
                return pText2.split("\n")[0];
            }
            if (pText3.contains("\n")) {
                return pText3.split("\n")[0];
            }
            while (text.startsWith(" ") || text.startsWith("\t") || text.startsWith("\n") || text.equals("")) {
                e = e.getPrevSibling();
                text = e.getText();
            }
            return text;
        }
        if (file.getName().endsWith(".md")) {

        }


        return "";
    }


}


