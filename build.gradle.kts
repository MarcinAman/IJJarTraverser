import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
    kotlin("jvm") version "1.4.10"
}
group = "me.prodcons"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    implementation("org.jsoup:jsoup:1.13.1")
    testImplementation(kotlin("test-junit"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
//    val getDependenciesUrls by creating {
//        project.repositories.forEach { repository ->
//            val dependencyGroup = "org.jetbrains.intellij.deps"
//            val dependencyName = "log4j"
//            val dependencyVersion = "1.2.17" //1.2.17.1
//            val url = (repository as MavenArtifactRepository).url.let {
//                if (!it.toString().endsWith("/")) {
//                    "$it/"
//                } else {
//                    it
//                }
//            }
//            val jarUrl = String.format(
//                "%s%s/%s/%s/%s-%s.jar", url,
//                dependencyGroup.replace('.', '/'), dependencyName, dependencyVersion,
//                dependencyName, dependencyVersion
//            )
//
//            kotlin.runCatching {
//                val jarFile = URL(jarUrl)
//                val inStream = jarFile.openStream();
//                if (inStream != null) {
//                    println(
//                        String.format("%s:%s:%s", dependencyGroup, dependencyName, dependencyVersion)
//                                + " -> " + jarUrl
//                    )
//                }
//            }
//        }
//    }
}