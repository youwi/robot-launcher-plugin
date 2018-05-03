package com.github.youwi.robotlauncher;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * context_menu_launcher
 * Created by yu on 2018/4/28.
 */
public class PluginConst {

    public static final String RUN_TYPE_NAME = "Robot Framework";
    public static final String NAME = RUN_TYPE_NAME;

    public static final String DESCRIPTION = "Run configuration for robot framework";
    //  pybot -t testcase *.robot
    //  python -m robot.run -t testcase *.robot
    public static final String default_binary = "python";
    // pip install robot-framework
    public static final String default_options = " -m robot.run ";
    // pip install side_md_launcher
    public static final String default_side_options = " -m side_md_launcher ";

    public static final Icon ICON = IconLoader.getIcon("/icons/perl.png");
}
