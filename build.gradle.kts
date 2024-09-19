plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ru.nwtls.megapaybot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://repo.dmulloy2.net/repository/public/")
}

val versionJDA = "5.1.0"
val versionYAML = "2.3";

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("net.dv8tion:JDA:$versionJDA")
    implementation("org.yaml:snakeyaml:$versionYAML")

    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:slf4j-simple:2.0.16")

    implementation ("mysql:mysql-connector-java:8.0.33")
}

tasks.test {
    useJUnitPlatform()
}

project.tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "ru.nwtls.megapaybot.Main"
        )
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}