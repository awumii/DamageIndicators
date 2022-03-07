plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "me.xneox"
version = "1.3.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}


repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    implementation("com.github.xxneox.commons:commons-core:1.0.1")
    implementation("com.github.xxneox.commons:commons-config:1.0.1")
}

tasks {
    processResources {
        filesMatching("plugin.yml") {
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
