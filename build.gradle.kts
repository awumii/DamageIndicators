plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

group = "com.github.awumii"
version = "2.0.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.github.decentsoftware-eu:decentholograms:2.8.4")

    implementation("com.github.xxneox.commons:commons-core:1.0.1")
    implementation("com.github.xxneox.commons:commons-config:1.0.1")
}

tasks {
    processResources {
        filesMatching("paper-plugin.yml") {
            expand("version" to project.version)
        }
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        minimize()
        relocate("me.xneox.commons", "me.xneox.indicators.libs.commons")
    }
}
