buildscript {
    repositories {
        gradlePluginPortal()
        maven("https:///repository/maven-public/")
    }
    dependencies {
        classpath("io.papermc.paperweight.userdev:io.papermc.paperweight.userdev.gradle.plugin:2.0.0-beta.17")
    }
}

rootProject.name = "AnvilGUI"

include(":api")
include(":abstraction")
include(":1_19_R1")
include(":1_19_1_R1")
include(":1_19_3_R2")
include(":1_19_4_R3")
include(":1_20_R1")
include(":1_20_6_R4")
include(":1_21_R1")
include(":1_21_R2")
include(":1_21_R7")