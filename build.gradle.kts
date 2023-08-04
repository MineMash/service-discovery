plugins {
    java
}

group = "dev.minemesh"
version = "0.0.1-SNAPSHOT"

subprojects {
    afterEvaluate {
        configureSubProject(this)
    }
}

fun configureSubProject(project: Project) = project.run {
    this.group = rootProject.group

    repositories {
        mavenCentral()
    }

    if (this.plugins.hasPlugin("java")) {
        configureJavaProject(this)
    }
}

fun configureJavaProject(project: Project) = project.run {
    java {
        sourceCompatibility = JavaVersion.VERSION_17;
    }

    if (this.path != ":common") {
        dependencies.implementation(project(":common"))
    }
}
