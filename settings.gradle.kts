buildscript {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
    dependencies {
        classpath("io.papermc.paperweight.userdev:io.papermc.paperweight.userdev.gradle.plugin:1.3.8")
    }
}

rootProject.name = "AnvilGUI"

include(":api")
include(":abstraction")
include(":1_19_R1")
include(":1_19_1_R1")
include(":1_19_3_R2")
include(":1_19_4_R3")