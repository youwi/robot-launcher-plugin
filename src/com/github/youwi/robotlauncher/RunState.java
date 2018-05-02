package com.github.youwi.robotlauncher;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.KillableColoredProcessHandler;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;

public class RunState extends CommandLineState {
    private final RobotRunConfiguration config;

    public RunState(RobotRunConfiguration config, ExecutionEnvironment environment) {
        super(environment);
        this.config = config;
    }

    @NotNull
    @Override
    public ProcessHandler startProcess() throws ExecutionException {
        GeneralCommandLine cmd = new GeneralCommandLine();
        cmd.setExePath(config.getInterpreterName());
        cmd.getParametersList().addParametersString(config.getInterpreterOptions());

        cmd.addParameter(config.getProgramName());
        cmd.getParametersList().addParametersString(config.getProgramParameters());

        OSProcessHandler processHandler = new KillableColoredProcessHandler(cmd);
        ProcessTerminatedListener.attach(processHandler, getEnvironment().getProject());

        return processHandler;
    }
}