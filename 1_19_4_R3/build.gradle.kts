plugins {
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperDevBundle("1.19.4-R0.1-SNAPSHOT")

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