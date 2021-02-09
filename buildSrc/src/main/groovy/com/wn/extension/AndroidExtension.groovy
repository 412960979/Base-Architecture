package com.wn.extension

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.util.function.Consumer

class AndroidExtension implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.apply plugin: 'com.android.application'
        target.apply plugin: 'kotlin-android'

        def appExt = target.android as BaseExtension

        DomainObjectCollection<BaseVariant> variants = null

        if (appExt instanceof AppExtension) {
            variants = appExt.applicationVariants
        } else if (appExt instanceof LibraryExtension) {
            variants = appExt.libraryVariants
        }

        List<String> variantsStr = new ArrayList<>()
        if (variants != null) {
            for (BaseVariant variant in variants) {
                variantsStr.add(variant.name)
            }
        }

        appExt.sourceSets.forEach(new Consumer<AndroidSourceSet>() {
            @Override
            void accept(AndroidSourceSet androidSourceSet) {
                if (!variantsStr.contains(androidSourceSet.name)
                        && androidSourceSet.name != "main") {
                    return
                }

                androidSourceSet.java.srcDirs += "src/$androidSourceSet.name/kotlin"
            }
        })


        if (variants != null) {
            variants.all {
                def bv = it as BaseVariant
                final String name = bv.flavorName
                final String flavorName = (name == null || name.isEmpty()) ? "main" : name

                appExt.sourceSets.getByName(flavorName).java.srcDirs += "src/$flavorName/kotlin"
            }
        }
    }
}

























