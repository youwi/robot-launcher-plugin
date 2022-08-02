package com.github.youwi.robotlauncher;

import com.intellij.execution.configuration.ConfigurationFactoryEx;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationModule;
import com.intellij.openapi.project.Project;

/**
 * context_menu_launcher
 * Created by yu on 2018/4/28.
 */

public class RunRobotConfigurationFactory extends ConfigurationFactoryEx {
    private static final String FACTORY_NAME = PluginConst.NAME;

    protected RunRobotConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @Override
    public RunConfiguration createTemplateConfiguration(Project project) {
        return new RobotRunConfiguration(new RunConfigurationModule(project), this);
    }

    @Override
    public String getId() {
        return FACTORY_NAME;
    }
}
