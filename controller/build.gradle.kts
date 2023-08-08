import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
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

// Tasks

task("bootRunLocal") {
    this.group = "Service Discovery Controller"
    this.description = "Runs the spring application in your local environment"

    if (project.hasProperty("clean") && project.property("clean") == "true") {
        this.dependsOn("cleanupContainers")
    }

    if (project.hasProperty("debug") && project.property("debug") == "true") {
        tasks.withType<BootRun> {
            this.args("--debug=true")
        }
    }

    this.doLast("Activate 'local' Profile") {
        tasks.withType<BootRun> {
            this.args("--spring.profiles.active=local")
        }
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

    this.doLast("Information") {
        println("RedisInsight is now available on http://localhost:8001")
        println("GraphiQl will start on http://localhost:8080/graphiql")
    }

    // TODO runs always but should only if task successes
	this.finalizedBy("bootRun")
}

task("cleanupContainers") {
	this.group = "Service Discovery Controller"
	this.description = "Stops all running containers from 'bootRunLocal'"

    this.doLast("Stopping redis") {
        exec {
            this.setIgnoreExitValue(true)
            stopContainer("service-discovery-bootRunLocal-redis-stack")
        }
    }
}

// container stuff with docker

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