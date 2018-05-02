package com.github.youwi.robotlauncher.form;

import com.github.youwi.robotlauncher.RobotRunConfiguration;
import com.intellij.execution.ui.CommonProgramParametersPanel;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.RawCommandLineEditor;

import javax.swing.*;
import java.awt.*;

class RobotConfigurationSettingsEditorV2From extends CommonProgramParametersPanel {
    private LabeledComponent<RawCommandLineEditor> interpreterOptionsComponent;
    private TextFieldWithBrowseButton interpreterNameField;

    private TextFieldWithBrowseButton programNameField;
    private LabeledComponent<RawCommandLineEditor> programParametersComponent;

    @Override
    protected void addComponents() {
        interpreterNameField = new TextFieldWithBrowseButton();
        interpreterNameField.addBrowseFolderListener("Choose interpreter...", "", getProject(), FileChooserDescriptorFactory.createSingleLocalFileDescriptor());
        LabeledComponent<JComponent> interpreterNameComponent = LabeledComponent.create(createComponentWithMacroBrowse(interpreterNameField), "Interpreter:");
        interpreterNameComponent.setLabelLocation(BorderLayout.WEST);
        add(interpreterNameComponent);

        interpreterOptionsComponent = LabeledComponent.create(new RawCommandLineEditor(), "Interpreter options");
        interpreterOptionsComponent.setLabelLocation(BorderLayout.WEST);
        add(interpreterOptionsComponent);

        programNameField = new TextFieldWithBrowseButton();
        programNameField.addBrowseFolderListener("Choose script...", "", getProject(), FileChooserDescriptorFactory.createSingleLocalFileDescriptor());
        LabeledComponent<JComponent> programNameComponent = LabeledComponent.create(createComponentWithMacroBrowse(programNameField), "Script:");
        programNameComponent.setLabelLocation(BorderLayout.WEST);
        add(programNameComponent);

        programParametersComponent = LabeledComponent.create(new RawCommandLineEditor(), "Script parameters:");
        programParametersComponent.setLabelLocation(BorderLayout.WEST);
        add(programParametersComponent);
    }

    public void reset(RobotRunConfiguration config) {
        interpreterOptionsComponent.getComponent().setText(config.getInterpreterOptions());
        interpreterNameField.setText(config.getInterpreterName());
        programNameField.setText(config.getProgramName());
        programParametersComponent.getComponent().setText(config.getProgramParameters());

        super.reset(config);
    }

    public void applyTo(RobotRunConfiguration config) {
        config.setInterpreterOptions(interpreterOptionsComponent.getComponent().getText());
        config.setInterpreterName(interpreterNameField.getText());
        config.setProgramName(programNameField.getText());
        config.setProgramParameters(programParametersComponent.getComponent().getText());

        super.applyTo(config);
    }
}
