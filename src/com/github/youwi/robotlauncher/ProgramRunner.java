package com.github.youwi.robotlauncher;


import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.runners.DefaultProgramRunner;
import org.jetbrains.annotations.NotNull;

public class ProgramRunner extends DefaultProgramRunner {
    @NotNull
    public String getRunnerId() {
        return PluginConst.NAME;
    }

    @Override
    public boolean canRun(@NotNull String s, @NotNull RunProfile runProfile) {
        return runProfile instanceof RobotRunConfiguration;
    }
}