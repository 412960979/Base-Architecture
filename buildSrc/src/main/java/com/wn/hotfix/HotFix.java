package com.wn.hotfix;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class HotFix implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        System.out.println("HotFix Development");

        // 自定义扩展（build.gradle里面的闭包，方法调用）
        People people = target.getExtensions().create("people", People.class);

//        System.out.println("name:" + people.getName());
//        System.out.println("age:" + people.getAge());
        target.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {

                // 等build.gradle解析完，再来执行这个，不然有的闭包没执行到，肯定拿不到数据
                System.out.println("name:" + people.getName());
                System.out.println("age:" + people.getAge());
            }
        });
    }
}
