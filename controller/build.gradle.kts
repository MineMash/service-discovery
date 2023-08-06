import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("com.palantir.docker-run") version "0.35.0"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.kafka:spring-kafka")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka")
}

dockerRun {
    name = "test-container"
    image = "busybox"
}

tasks.withType<BootRun> {
	this.args("--spring.profiles.active=local")
}

task("bootRunLocal") {
    this.group = "Service Discovery Controller"
    this.description = "Runs the spring application in your local environment"

    if (project.hasProperty("clean")) {
        this.dependsOn("cleanupContainers")
    }

    // start redis container
    this.doLast("Setup redis") {
        // run redis
        exec {
            runContainer(
                name = "service-discovery-bootRunLocal-redis-stack",
                image = "redis/redis-stack:6.2.6-v9",
                additionArguments = arrayOf(
					"-p", "6379:6379", // redis port
					"-p", "8001:8001", // redis insight port
				)
            )
        }
    }

	this.doLast("Setup kafka") {

	}

	this.finalizedBy("bootRun")
}

task("cleanupContainers") {
	this.group = "Service Discovery Controller"
	this.description = "Stops all running containers from 'bootRunLocal'"

	this.doLast("Stopping redis") {
		exec {
			stopContainer("service-discovery-bootRunLocal-redis-stack")
		}
	}
}

fun ExecSpec.runContainer(name: String, image: String, vararg additionArguments: String) {
    val args = mutableListOf("run", "-d", "--rm", "--name", name)
    args.addAll(additionArguments)
	args.add(image)

    this.executable = "docker"
    this.args = args
}

fun ExecSpec.stopContainer(name: String) {
	this.executable = "docker"

	this.args("stop", name)
}