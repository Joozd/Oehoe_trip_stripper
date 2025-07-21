plugins {
    // 1) de Kotlin/JVM-plugin
    kotlin("jvm") version "2.1.21"
    // 2) de Kotlin Compose-compiler plugin (verplicht sinds Kotlin 2.0.0-RC2)
    kotlin("plugin.compose") version "2.1.21"
    // 3) de Compose Multiplatform Gradle-plugin voor desktop-artefacten
    id("org.jetbrains.compose") version "1.6.10"
    application
}

group = "nl.joozd"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.itextpdf:itextg:5.5.10")
    // Compose for Desktop
    implementation(compose.desktop.currentOs)
    // Material 3 (Multiplatform) – plugin regelt de juiste versie
    implementation(compose.material3)
}

application {
    mainClass.set("MainKt")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}