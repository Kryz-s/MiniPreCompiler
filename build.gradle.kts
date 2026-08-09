plugins {
  id("java")
}

group = "io.github.krys"
version = "1.0.0"

repositories {
  mavenCentral()
}

dependencies {
  compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
}

tasks.test {
  useJUnitPlatform()
}