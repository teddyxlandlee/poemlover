plugins {
    id("java")
    id("idea")
}

group = "xland.mcmod"
version = ext["project_version"].toString()

repositories {
    maven("https://maven.aliyun.com/repository/public")
    maven("https://lss233.littleservice.cn/repositories/minecraft")
    maven("https://covid-trump.github.io/mvn")
}

dependencies {
    compileOnly("org.jetbrains:annotations:19.0.0")
    implementation("com.google.guava:guava:21.0")
    implementation("org.apache.logging.log4j:log4j-api:2.8.1")
    implementation("net.fabricmc:sponge-mixin:0.11.4+mixin.0.8.5")
}

val targetJavaVersion = 17
tasks.withType(JavaCompile::class).configureEach {
    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    // If Javadoc is generated, this must be specified in that task too.
    options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
        options.release = (targetJavaVersion)
    }
}

java.withSourcesJar()

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching(setOf("fabric.mod.json", "META-INF/mods.toml", "META-INF/neoforge.mods.toml")) {
        expand("version" to project.version)
    }
}

tasks.jar {
	manifest {
		attributes("MixinConfigs" to "poemlover.mixins.json")
	}
}
