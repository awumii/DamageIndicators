plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "me.xneox"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("com.github.Archy-X:AureliumSkills:Beta1.2.0")

    implementation("org.spongepowered:configurate-hocon:4.1.1")
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
        relocate("org.spongepowered.configurate", "me.xneox.indicators.libs.configurate")
        relocate("com.typesafe.config", "me.xneox.indicators.libs.config")
        relocate("io.leangen.geantyref", "me.xneox.indicators.libs.geantyref")
    }
}
