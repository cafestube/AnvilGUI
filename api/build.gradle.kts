import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.papermc.paperweight.userdev.attribute.Obfuscation

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
}

group = "eu.cafestube"
version = "1.0.8-SNAPSHOT"

val versionSpecific = configurations.create("versionSpecific") {
    description = "Version Adapters to include in the JAR"
    isCanBeConsumed = false
    isCanBeResolved = true
    shouldResolveConsistentlyWith(configurations["runtimeClasspath"])
    attributes {
        attribute(Obfuscation.OBFUSCATION_ATTRIBUTE, objects.named(Obfuscation.NONE))
    }
}

val versionSpecificReobf = configurations.create("versionSpecificReobf") {
    description = "Version Adapters to include in the JAR"
    isCanBeConsumed = false
    isCanBeResolved = true
    shouldResolveConsistentlyWith(configurations["runtimeClasspath"])
    attributes {
        attribute(Obfuscation.OBFUSCATION_ATTRIBUTE, objects.named(Obfuscation.OBFUSCATED))
    }
}

val reobfVersions = listOf("1_19_R1", "1_19_1_R1", "1_19_3_R2", "1_19_4_R3", "1_20_R1")
val versions = listOf("1_20_6_R4", "1_21_R1", "1_21_R2")

repositories {
    maven(url = "https://repo.opencollab.dev/main/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    implementation(project(":abstraction"))
    compileOnly("org.geysermc.geyser:api:2.4.0-SNAPSHOT")
    versions.forEach {
        versionSpecific(project(":$it"))
    }
    reobfVersions.forEach {
        versionSpecificReobf(project(":$it"))
    }
}

tasks.named<ShadowJar>("shadowJar") {
    dependsOn(versions.map { project.project(":$it") }.map { it.tasks.named("build") } +
            reobfVersions.map { project.project(":$it") }.map { it.tasks.named("build") })

    from(Callable {
        (versionSpecific.resolve() + versionSpecificReobf.resolve())
            .map { f ->
                zipTree(f).matching {
                    exclude("META-INF/")
                }
            }
    })
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

            artifact(tasks["shadowJar"])
            artifact(tasks["sourcesJar"])
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