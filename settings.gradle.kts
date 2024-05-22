buildscript {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
    dependencies {
        classpath("io.papermc.paperweight.userdev:io.papermc.paperweight.userdev.gradle.plugin:1.7.1")
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
include(":1_20_2_R2")
include(":1_20_3_R3")
include(":1_20_6_R4")