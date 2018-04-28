package com.github.youwi.custummenu.extensions;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configuration.EnvironmentVariablesComponent;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.PathMacroManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.execution.CommonProgramRunConfigurationParameters;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configuration.AbstractRunConfiguration;
import com.intellij.execution.configuration.EnvironmentVariablesComponent;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationModule;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.components.PathMacroManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * context_menu_launcher
 * Created by yu on 2018/4/28.
 */
public class RobotRunConfiguration extends AbstractRunConfiguration implements CommonProgramRunConfigurationParameters {
//public class RobotRunConfiguration extends RunConfigurationBase implements AbstractRunConfiguration {

    private String interpreterOptions = "";
    private String interpreterName = "";
    private String programName = "";
    private String programParameters = "";

//    protected RobotRunConfiguration(Project project, ConfigurationFactory factory, String name) {
//        super(project, factory, name);
//    }
    public RobotRunConfiguration(RunConfigurationModule configurationModule, ConfigurationFactory factory) {
        super("", configurationModule, factory);
    }

//    @NotNull
//    public static RobotRunConfiguration getInstance() {
//        return ConfigurationTypeUtil.findConfigurationType(RobotRunConfigurationPluginType.class);
//    }

    @Override
    public Collection<Module> getValidModules() {
        return Arrays.asList(ModuleManager.getInstance(getProject()).getModules());

    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {
        PathMacroManager.getInstance(getProject()).expandPaths(element);
        super.readExternal(element);
        DefaultJDOMExternalizer.readExternal(this, element);
        readModule(element);
        EnvironmentVariablesComponent.readExternal(element, getEnvs());

        interpreterOptions = JDOMExternalizerUtil.readField(element, "INTERPRETER_OPTIONS");
        interpreterName = JDOMExternalizerUtil.readField(element, "INTERPRETER_NAME");
        programName = JDOMExternalizerUtil.readField(element, "PROGRAM_NAME");
        setProgramParameters(JDOMExternalizerUtil.readField(element, "PROGRAM_PARAMETERS"));
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);

        JDOMExternalizerUtil.writeField(element, "INTERPRETER_OPTIONS", interpreterOptions);
        JDOMExternalizerUtil.writeField(element, "INTERPRETER_NAME", interpreterName);
        JDOMExternalizerUtil.writeField(element, "PROGRAM_NAME", programName);
        JDOMExternalizerUtil.writeField(element, "PROGRAM_PARAMETERS", getProgramParameters());

        DefaultJDOMExternalizer.writeExternal(this, element);
        writeModule(element);

        PathMacroManager.getInstance(getProject()).collapsePathsRecursively(element);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new RobotConfigrationSettingsEditor();
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {

    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        executor.getContextActionId();
        return new RunState(this, executionEnvironment);
        // GeneralCommandLine s= new GeneralCommandLine();
        //return null;
    }


    public String getInterpreterOptions() {
        return interpreterOptions;
    }

    public void setInterpreterOptions(String interpreterOptions) {
        this.interpreterOptions = interpreterOptions;
    }

    public String getInterpreterName() {
        return interpreterName;
    }

    public void setInterpreterName(String interpreterName) {
        this.interpreterName = interpreterName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramParameters() {
        return programParameters;
    }

    @Override
    public void setWorkingDirectory(@Nullable String s) {

    }

    @Nullable
    @Override
    public String getWorkingDirectory() {
        return null;
    }

    public void setProgramParameters(String programParameters) {
        this.programParameters = programParameters;
    }

}