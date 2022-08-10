plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
}

group = "eu.cafestube"
version = "1.0.0-SNAPSHOT"


dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    implementation(project(":abstraction"))
    implementation(project(":1_19_R1"))
    implementation(project(":1_19_1_R1"))

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withSourcesJar()
}

base {
    archivesName.set("AnvilGUI")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "$group"
            artifactId = "anvilgui"
            version = "${project.version}"

            from(components["java"])
        }
        repositories {
            maven {
                name = "cafestubeRepository"
                credentials(PasswordCredentials::class)
                url = uri("https://repo.cafestu.be/repository/maven-public-snapshots/")
            }
        }
    }
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }
    jar {
        archiveClassifier.set("api-only")
    }
    build {
        dependsOn(shadowJar)
    }
}