package com.wn.version;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

public class UpdateVersion implements Plugin<Project> {

    @Override
    public void apply(Project target) {

        target.task("updateVersion", new Action<Task>() {
            @Override
            public void execute(Task task) {
                System.out.println("this is project version update task");

                task.doLast(new Action<Task>() {
                    @Override
                    public void execute(Task task) {
                        // ../version.properties 是项目根目录-->../BaseArchitecture/version.properties
                        File file = target.file("version.properties");
                        System.out.println("file_path:" + file.getAbsolutePath());

                        Writer fileWriter = null;
                        Properties versionProp = new Properties();
                        try {
                            versionProp.load(new FileInputStream((file)));

                            int codeBumped = Integer.parseInt((String) versionProp.get("VERSION_CODE")) + 1;
                            versionProp.setProperty("VERSION_CODE", String.valueOf(codeBumped));

                            fileWriter = new FileWriter(file);
                            versionProp.store(fileWriter, null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
//    {
//            doFirst {
//                def versionPropsFile = file('version.properties')
//                def versionProp = new Properties()
//                versionProp.load(new FileInputStream(versionPropsFile))
//                def codeBumped = versionProp['VERSION_CODE'].toInteger + 1
//                versionProp['VERSION_CODE'] = codeBumped.toString()
//                versionProp.store(versionPropsFile.newWriter(), null)
//            }
//        }
    }
}
