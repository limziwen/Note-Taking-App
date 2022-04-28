val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
var kmongoVersion = "4.5.0"

plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.cs398"
version = "0.0.1"
application {
    mainClass.set("com.cs398.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("org.litote.kmongo:kmongo:$kmongoVersion")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongoVersion")

    implementation("org.litote.kmongo:kmongo-rxjava2:4.5.0")
    implementation("org.litote.kmongo:kmongo-reactor:4.5.0")

    implementation("io.ktor:ktor-gson:$ktor_version")

}

tasks{
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "com.example.ApplicationKt"))
        }
    }
}
