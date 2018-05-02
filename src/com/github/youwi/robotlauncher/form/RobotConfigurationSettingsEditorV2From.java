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

    private LabeledComponent<RawCommandLineEditor> envPATHComponent;


    @Override
    protected void addComponents() {
        envPATHComponent = LabeledComponent.create(new RawCommandLineEditor(), "PATH");
        envPATHComponent.setLabelLocation(BorderLayout.WEST);
        add(envPATHComponent);

        interpreterNameField = new TextFieldWithBrowseButton();
        interpreterNameField.addBrowseFolderListener("Choose interpreter...", "", getProject(), FileChooserDescriptorFactory.createSingleLocalFileDescriptor());
        LabeledComponent<JComponent> interpreterNameComponent = LabeledComponent.create(createComponentWithMacroBrowse(interpreterNameField), "Binary:");
        interpreterNameComponent.setLabelLocation(BorderLayout.WEST);
        add(interpreterNameComponent);

        interpreterOptionsComponent = LabeledComponent.create(new RawCommandLineEditor(), "Options");
        interpreterOptionsComponent.setLabelLocation(BorderLayout.WEST);
        add(interpreterOptionsComponent);

        programNameField = new TextFieldWithBrowseButton();
        programNameField.addBrowseFolderListener("Choose script...", "", getProject(), FileChooserDescriptorFactory.createSingleLocalFileDescriptor());
        LabeledComponent<JComponent> programNameComponent = LabeledComponent.create(createComponentWithMacroBrowse(programNameField), "Script:");
        programNameComponent.setLabelLocation(BorderLayout.WEST);
        add(programNameComponent);

        programParametersComponent = LabeledComponent.create(new RawCommandLineEditor(), "Script Options:");
        programParametersComponent.setLabelLocation(BorderLayout.WEST);
        add(programParametersComponent);
    }

    public void reset(RobotRunConfiguration config) {
        interpreterOptionsComponent.getComponent().setText(config.getInterpreterOptions());
        interpreterNameField.setText(config.getInterpreterName());
        programNameField.setText(config.getProgramName());
        programParametersComponent.getComponent().setText(config.getProgramParameters());
        envPATHComponent.getComponent().setText(config.envPATH);

        super.reset(config);
    }

    public void applyTo(RobotRunConfiguration config) {
        config.setInterpreterOptions(interpreterOptionsComponent.getComponent().getText());
        config.setInterpreterName(interpreterNameField.getText());
        config.setProgramName(programNameField.getText());
        config.setProgramParameters(programParametersComponent.getComponent().getText());
        config.envPATH=envPATHComponent.getComponent().getText();

        super.applyTo(config);
    }
}
