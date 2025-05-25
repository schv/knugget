plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(
        url = "https://packages.jetbrains.team/maven/p/grazi/grazie-platform-public"
    )
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("ai.koog:koog-agents:0.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.slf4j:slf4j-simple:2.0.12")

}

tasks.test {
    useJUnitPlatform()
}