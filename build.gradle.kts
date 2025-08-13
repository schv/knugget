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
    implementation("ai.koog:koog-agents:0.3.0")
    implementation("ai.jetbrains.code.prompt:code-prompt-executor-grazie-koog-jvm:1.0.0-beta.105-feat-829-1")
    implementation("ai.jetbrains.code.prompt:code-prompt-llm-jvm:1.0.0-beta.105-feat-829-1")
    implementation("org.slf4j:slf4j-simple:2.0.17")

}

tasks.test {
    useJUnitPlatform()
}