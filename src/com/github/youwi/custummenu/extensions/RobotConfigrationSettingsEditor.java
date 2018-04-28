package com.github.youwi.custummenu.extensions;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * context_menu_launcher
 * Created by yu on 2018/4/28.
 */


public class RobotConfigrationSettingsEditor extends SettingsEditor<RobotRunConfiguration> {
    private JPanel myPanel;
    private LabeledComponent<ComponentWithBrowseButton> myMainClass;
    private JRadioButton radioButton1;
    private JCheckBox checkBox1;
    private JTextField textField1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;

    @Override
    protected void resetEditorFrom(RobotRunConfiguration robotRunConfiguration) {

    }

    @Override
    protected void applyEditorTo(RobotRunConfiguration robotRunConfiguration) throws ConfigurationException {
        robotRunConfiguration.setInterpreterOptions(textField1.getText());
        robotRunConfiguration.setInterpreterName(textField1.getText());
        robotRunConfiguration.setProgramName(textField1.getText());
        robotRunConfiguration.setProgramParameters(textField1.getText());
        // save
        // super.applyTo(robotRunConfiguration);
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return myPanel;
    }

    private void createUIComponents() {
        myMainClass = new LabeledComponent<ComponentWithBrowseButton>();
        myMainClass.setComponent(new TextFieldWithBrowseButton());
    }
}