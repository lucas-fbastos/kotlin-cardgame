plugins {
    kotlin("jvm") version "1.9.20"
    id("org.jetbrains.compose") version "1.6.0"
}

group = "com.luck"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.compose.ui:ui")
    implementation("com.mohamedrejeb.dnd:compose-dnd:0.2.0")

    implementation("org.jetbrains.compose.foundation:foundation")
    implementation("org.jetbrains.compose.material:material")
    implementation(compose.desktop.currentOs)

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.test {
    useJUnitPlatform()
}

compose.desktop {
    application {
        mainClass = "BootstraperKt"

        nativeDistributions {
            packageName = "com-luck-mind-bug"
            packageVersion = "1.0.0"
        }
    }
}
