plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.graalvm.buildtools.native") version "0.10.1"
    id("org.asciidoctor.jvm.convert") version "4.0.2"
}

group = "dev.stormcat"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

sourceSets {
    main {
        java {
            srcDir("src/generated/java")
        }
        resources {
            srcDir("src/generated/resources")
        }
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }

    create("myTaskDependencies") {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
}

extra["springModulithVersion"] = "1.1.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
//    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.3.0")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("org.springframework.modulith:spring-modulith-starter-jdbc")
    implementation("com.mysql:mysql-connector-j:8.3.0")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.testcontainers:junit-jupiter")

    "myTaskDependencies"(files("../tools/build/libs/tools-0.0.1-SNAPSHOT.jar"))
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

tasks.test {
    useJUnitPlatform()
}

val generateJdbc by tasks.registering(JavaExec::class) {
    group = "generate"
    description = "Generate JDBC code"
    classpath = configurations["myTaskDependencies"]
    mainClass = "dev.stormcat.tools.generator.jdbc.SpringDataJdbcGenerator"
    args(
            "--url=jdbc:mysql://localhost:3306/modulith?useSSL=true&serverTimezone=UTC",
            "--username=spring",
            "--password=summer",
            "--ignore-table=DATABASECHANGELOG",
            "--ignore-table=DATABASECHANGELOGLOCK",
            "--repository-package-name=dev.stormcat.apps.common.repository",
            "--entity-package-name=dev.stormcat.apps.common.entity",
    )
    outputs.upToDateWhen { false }
}