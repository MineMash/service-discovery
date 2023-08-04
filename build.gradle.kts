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

    dependencies {
        // using 'project' instead of 'this' because scope is 'DependencyHandlerScope' and not the project itself
        if (project.path != ":common") {
            implementation(project(":common"))
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
