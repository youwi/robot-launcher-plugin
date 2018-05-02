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

import java.io.File;
import java.util.Map;

public class RunState extends CommandLineState {
    private final RobotRunConfiguration config;

    public RunState(RobotRunConfiguration config, ExecutionEnvironment environment) {
        super(environment);
        this.config = config;
    }

    @NotNull
    @Override
    public ProcessHandler startProcess() throws ExecutionException {
        GeneralCommandLine cmd = new GeneralCommandLine(); // normal cmd
        //if use Python  virtual environment
        // GeneralCommandLine cmd = new PtyCommandLine();
        // import com.intellij.execution.configurations.PtyCommandLine;
        // import com.jetbrains.python.run.PythonCommandLineState;

        cmd.setExePath(config.getInterpreterName());
        cmd.getParametersList().addParametersString(config.getInterpreterOptions());

        cmd.addParameter(config.getProgramName());

        cmd.getParametersList().addParametersString(config.getProgramParameters());
        cmd.setWorkDirectory(getEnvironment().getProject().getBasePath());

        // set PATH if use python virtual env
        Map<String,String> envMap=cmd.getEffectiveEnvironment();
        String newPath=config.envPATH+System.getProperties().getProperty("path.separator")+envMap.get("PATH");
        envMap.put("PATH",newPath);
        envMap.remove("PYTHONHOME");// sep handle
        cmd.setPassParentEnvironment(false);
        cmd.withEnvironment(envMap);

        File pythonBinary=new File(config.envPATH+"/"+config.getInterpreterName());
        if(pythonBinary.exists()){
            cmd.setExePath(pythonBinary.getAbsolutePath());
        }


        OSProcessHandler processHandler = new KillableColoredProcessHandler(cmd);
        // processHandler.setHasPty(true);
        ProcessTerminatedListener.attach(processHandler, getEnvironment().getProject());

        return processHandler;
    }
}