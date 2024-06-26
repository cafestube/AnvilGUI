
plugins {
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperDevBundle("1.20.2-R0.1-SNAPSHOT")

    implementation(project(":abstraction"))
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