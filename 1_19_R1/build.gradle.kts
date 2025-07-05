plugins {
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("1.19-R0.1-SNAPSHOT")

    implementation(project(":abstraction"))
    implementation(project(":1_19_1_R1"))

}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}