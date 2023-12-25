plugins {
    kotlin("jvm") version "1.9.21"
}

sourceSets {
    main {
        kotlin {
            srcDir("src")
            dependencies {
                implementation("org.apache.commons:commons-math3:3.6.1")
            }
        }
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
