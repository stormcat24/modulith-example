plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.stormcat"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.velocity:velocity-engine-core:2.3")
    implementation("info.picocli:picocli:4.7.5")
    implementation("org.liquibase:liquibase-core:4.28.0")
    implementation("com.mysql:mysql-connector-j:8.3.0")
    implementation("edu.stanford.nlp:stanford-corenlp:4.5.6")
    implementation("edu.stanford.nlp:stanford-corenlp:4.5.6:models")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.testcontainers:junit-jupiter:1.19.7")
    testImplementation("org.testcontainers:mysql:1.19.7")
}

tasks {
    test {
        useJUnitPlatform()
    }

    shadowJar {
        archiveBaseName.set("tools")
        archiveClassifier.set("")
        archiveVersion.set("0.0.1-SNAPSHOT")
        manifest {
            attributes["Main-Class"] = "dev.stormcat.tools.generator.jdbc.SpringDataJdbcGenerator"
        }
    }

    build {
        dependsOn(shadowJar)
    }
}


val generateJdbc by tasks.registering(JavaExec::class) {
    group = "application"
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
    classpath = sourceSets["main"].runtimeClasspath
}
