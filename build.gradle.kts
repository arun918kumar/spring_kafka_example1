
plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
}

repositories {
	mavenCentral()
}

subprojects {

	apply(plugin = "java")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	group = "com.example"
	version = "0.0.1-SNAPSHOT"

	java {
		sourceCompatibility = JavaVersion.VERSION_17
	}

	repositories {
		mavenCentral()
	}

	dependencies {
		implementation ("org.springframework.boot:spring-boot-starter-web")
		implementation ("org.springframework.kafka:spring-kafka")
		testImplementation ("org.springframework.boot:spring-boot-starter-test")
		testImplementation ("org.springframework.kafka:spring-kafka-test")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	springBoot {
		mainClass="com.example.Main"
	}

	tasks.bootBuildImage {
		builder.set("paketobuildpacks/builder-jammy-base:latest")

	}

}

project(":common") {
	tasks.bootJar {
		enabled=false
	}

	tasks.jar {
		enabled=true
	}
}

tasks.bootJar {
	enabled=false
}

tasks.jar {
	enabled=false
}
