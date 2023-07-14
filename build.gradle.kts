import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.15.0"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

// Configure project's dependencies
repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases")
    maven("https://cache-redirector.jetbrains.com/intellij-dependencies")
}

dependencies {
    testImplementation("com.jetbrains.intellij.go:go-test-framework:231.9225.16") {
        exclude("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
        exclude("org.jetbrains.kotlin", "kotlin-reflect")
        exclude("com.jetbrains.rd", "rd-core")
        exclude("com.jetbrains.rd", "rd-swing")
        exclude("com.jetbrains.rd", "rd-framework")
        exclude("org.jetbrains.teamcity", "serviceMessages")
        exclude("io.ktor", "ktor-network-jvm")
        exclude("com.jetbrains.infra", "download-pgp-verifier")
        exclude("ai.grazie.utils", "utils-common-jvm")
        exclude("ai.grazie.model", "model-common-jvm")
        exclude("ai.grazie.model", "model-gec-jvm")
        exclude("ai.grazie.model", "model-text-jvm")
        exclude("ai.grazie.nlp", "nlp-common-jvm")
        exclude("ai.grazie.nlp", "nlp-detect-jvm")
        exclude("ai.grazie.nlp", "nlp-langs-jvm")
        exclude("ai.grazie.nlp", "nlp-patterns-jvm")
        exclude("ai.grazie.nlp", "nlp-phonetics-jvm")
        exclude("ai.grazie.nlp", "nlp-similarity-jvm")
        exclude("ai.grazie.nlp", "nlp-stemmer-jvm")
        exclude("ai.grazie.nlp", "nlp-tokenizer-jvm")
        exclude("ai.grazie.spell", "hunspell-en-jvm")
        exclude("ai.grazie.spell", "gec-spell-engine-local-jvm")
        exclude("ai.grazie.utils", "utils-lucene-lt-compatibility-jvm")
    }
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))
}

tasks {
    // Set the JVM compatibility versions
    properties("javaVersion").let {
        withType<JavaCompile> {
            sourceCompatibility = it
            targetCompatibility = it
        }
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = it
        }
    }

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))
        untilBuild.set(properties("pluginUntilBuild"))
    }
}
