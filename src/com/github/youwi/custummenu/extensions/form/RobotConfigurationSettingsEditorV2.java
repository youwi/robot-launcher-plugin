package com.github.youwi.custummenu.extensions.form;

import com.github.youwi.custummenu.extensions.RobotRunConfiguration;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

class RobotConfigurationSettingsEditorV2 extends SettingsEditor<RobotRunConfiguration> {
    private RobotConfigurationSettingsEditorV2From robotConfigurationSettingsEditorV2From;

    public RobotConfigurationSettingsEditorV2(Module module) {
        this.robotConfigurationSettingsEditorV2From = new RobotConfigurationSettingsEditorV2From();
        this.robotConfigurationSettingsEditorV2From.setModuleContext(module);
    }

    @Override
    protected void resetEditorFrom(RobotRunConfiguration config) {
        robotConfigurationSettingsEditorV2From.reset(config);
    }

    @Override
    protected void applyEditorTo(RobotRunConfiguration config) throws ConfigurationException {
        robotConfigurationSettingsEditorV2From.applyTo(config);
    }

    @Override
    @NotNull
    protected JComponent createEditor() {
        return robotConfigurationSettingsEditorV2From;
    }

    @Override
    protected void disposeEditor() {
        robotConfigurationSettingsEditorV2From = null;
    }
}