package com.github.youwi.robotlauncher;

import com.intellij.execution.*;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.ConfigurationFromContext;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.ExecutionUtil;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.util.List;


/**
 * context_menu_launcher
 * Created by yu on 2018/4/27.
 */
public class RunAsRobotFromMenuAction extends AnAction {

    RunConfigProducer CONFIG_PRODUCER=new RunConfigProducer();


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
       //  new RunState(this, executionEnvironment);
        List<RunnerAndConfigurationSettings> configurationSettings = RunManager.getInstance(project).getConfigurationSettingsList(RobotRunConfigurationPluginType.getInstance());

        //DefaultDebugExecutor.getDebugExecutorInstance();
        //ProgramRunnerUtil.getRunner().execute();
        //DefaultRunExecutor.
        // ProgramRunnerUtil.executeConfiguration();
        //ProgramRunnerUtil.executeConfiguration(configurationSettings.get(1));
       // ProgramRunnerUtil.getRunner()
       // ProgramRunnerUtil.getRunner("",RunnerAndConfigurationSettings.class);
        VirtualFile file = anActionEvent.getData(CommonDataKeys.VIRTUAL_FILE);
        boolean isFound=false;

        for(RunnerAndConfigurationSettings rac :configurationSettings){

            //RunRobotConfigurationFactory.createTemplateConfiguration();
            RunConfiguration rcf=rac.getConfiguration();
            if(rcf instanceof RobotRunConfiguration){
               String name= ((RobotRunConfiguration) rcf).getProgramName();
                if(file!=null && file.getName().equals(name)){
                    // not work on idea 2016
                    // ProgramRunnerUtil.executeConfiguration(rac, DefaultRunExecutor.getRunExecutorInstance());
                    isFound=true;
                }
                //((RobotRunConfiguration) rcf).getInterpreterName();
            }
        }
        if(!isFound){
           // RunRobotConfigurationFactory.();
            // ProgramRunnerUtil.crea;
           // RunManager.getInstance(project).createConfiguration(PluginConst.NAME,);
        }
        // RunnerAndConfigurationSettings rac=configurationSettings.get
        final DataContext dataContext = anActionEvent.getDataContext();
        final ConfigurationContext context = ConfigurationContext.getFromContext(dataContext);
        if (context.getLocation() == null) return;
        final RunManagerEx runManager = (RunManagerEx) context.getRunManager();
        RunnerAndConfigurationSettings setting = context.getConfiguration();
        if (setting == null || setting.getConfiguration() == null || !(setting.getConfiguration() instanceof RobotRunConfiguration)) {
            ConfigurationFromContext config = CONFIG_PRODUCER.createConfigurationFromContext(context);
            if (config == null) return;
            setting = config.getConfigurationSettings();
            runManager.setTemporaryConfiguration(setting);
        } else {
            boolean hasExistingSetting = false;
            for (RunnerAndConfigurationSettings existingSetting : runManager.getConfigurationSettingsList(new RobotRunConfigurationPluginType())) {
                if (existingSetting.equals(setting)) {
                    hasExistingSetting = true;
                    break;
                }
            }
            if (!hasExistingSetting) {
                runManager.setTemporaryConfiguration(setting);
            }
        }
        runManager.setSelectedConfiguration(setting);

        ExecutorRegistry.getInstance().getRegisteredExecutors();
        for (Executor executor : ExecutorRegistry.getInstance().getRegisteredExecutors()) {
            if(executor instanceof DefaultRunExecutor){
                ExecutionUtil.runConfiguration(setting, executor); //!!run all
            }
        }

        //project.get
//        Navigatable navigatable = anActionEvent.getData(CommonDataKeys.NAVIGATABLE);
//        if (project != null && navigatable != null) {
//            Messages.showMessageDialog(project, navigatable.toString(), "Selected Element", Messages.getInformationIcon());
//        }
    }

    @Override
    public void update(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        //Navigatable navigatable = anActionEvent.getData(CommonDataKeys.NAVIGATABLE);
        VirtualFile file = anActionEvent.getData(CommonDataKeys.VIRTUAL_FILE);
        // only support *.robot
        boolean visible = file != null && file.getName().endsWith(".robot");
        anActionEvent.getPresentation().setEnabledAndVisible(visible);
       // anActionEvent.getPresentation().setEnabledAndVisible(project != null && navigatable != null);
       // anActionEvent.getPresentation().setEnabledAndVisible(true);
    }

    public Icon getIcon() {
        return AllIcons.General.Information;
    }
}


