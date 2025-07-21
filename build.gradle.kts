import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    // 1) de Kotlin/JVM-plugin
    kotlin("jvm") version "2.1.21"
    // 2) de Kotlin Compose-compiler plugin (verplicht sinds Kotlin 2.0.0-RC2)
    kotlin("plugin.compose") version "2.1.21"
    // 3) de Compose Multiplatform Gradle-plugin voor desktop-artefacten
    id("org.jetbrains.compose") version "1.6.10"
}

group = "nl.joozd"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

compose.desktop {
    application {
        javaHome = "C:\\Users\\joozd\\.gradle\\jdks\\eclipse_adoptium-17-amd64-windows\\jdk-17.0.8+7"
        // or hard-code: "C:/Program Files/Java/jdk-17"
        mainClass = "nl.joozd.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Msi, TargetFormat.Exe)
            packageName = "TripStripper"
            packageVersion = "1.0.0"

            // include everything so you don’t miss modules at runtime:
            includeAllModules = true
        }
    }
}


dependencies {
    implementation("com.itextpdf:itextg:5.5.10")
    // Compose for Desktop
    implementation(compose.desktop.currentOs)
    // Material 3 (Multiplatform) – plugin regelt de juiste versie
    implementation(compose.material3)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}