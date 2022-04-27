
plugins {
	id("org.springframework.boot") version "2.5.6"
	id("org.openapi.generator").version("5.3.0")
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
	kotlin("plugin.jpa") version "1.5.31"
}

group = "com.hometask"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation ("org.springframework.boot:spring-boot-starter-validation:2.6.7")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok:1.18.22")
	implementation("org.mapstruct:mapstruct:1.4.2.Final")
	implementation("org.mapstruct:mapstruct-processor:1.4.2.Final")
	implementation("javax.validation:validation-api:2.0.1.Final")
	implementation("org.liquibase:liquibase-core")
	api("io.springfox:springfox-swagger2:2.9.2")
	api("io.springfox:springfox-swagger-ui:2.9.2")
	api("org.openapitools:jackson-databind-nullable:0.2.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
val spec = "$rootDir/src/main/resources/openapi/api.yml"
val generatedSourcesDir = "$buildDir/generated/openapi"

openApiGenerate {
	generatorName.set("spring")

	inputSpec.set(spec)
	outputDir.set(generatedSourcesDir)

	apiPackage.set("org.openapi.moneytransfer.api")
	invokerPackage.set("org.openapi.moneytransfer.invoker")
	modelPackage.set("org.openapi.moneytransfer.model")

	configOptions.set(mapOf(
		"dateLibrary" to "java8", "interfaceOnly" to "true", "useBeanValidation" to "true"
	))
}

sourceSets {
	getByName("main") {
		java {
			srcDir("$generatedSourcesDir/src/main/java")
		}
	}
}

tasks {
	val openApiGenerate by getting

	val compileJava by getting {
		dependsOn(openApiGenerate)
	}

	val compileKotlin by getting {
		dependsOn(openApiGenerate)
	}
}
tasks.withType<Test> {
	useJUnitPlatform()
}
