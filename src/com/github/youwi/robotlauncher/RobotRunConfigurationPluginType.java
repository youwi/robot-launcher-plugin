package com.github.youwi.robotlauncher;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * context_menu_launcher
 * Created by yu on 2018/4/28.
 */
public class RobotRunConfigurationPluginType implements ConfigurationType {
    @Override
    public String getDisplayName() {
        return PluginConst.NAME;
    }

    @Override
    public String getConfigurationTypeDescription() {
        return PluginConst.DESCRIPTION;
    }

    @Override
    public Icon getIcon() {
        //return IconLoader.getIcon("/icons/robot50.png");
        //   return
        return AllIcons.Toolbar.Unknown;
    }

    @NotNull
    public static RobotRunConfigurationPluginType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(RobotRunConfigurationPluginType.class);
    }

    @NotNull
    @Override
    public String getId() {
        return "DEMO_RUN_CONFIGURATION";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new RunRobotConfigurationFactory(this)};
    }
}