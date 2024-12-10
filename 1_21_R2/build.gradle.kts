
plugins {
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("1.21.3-R0.1-SNAPSHOT")

    implementation(project(":abstraction"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}