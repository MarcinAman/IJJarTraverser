import org.jsoup.Jsoup
import java.nio.file.Files
import java.nio.file.Paths
import java.util.jar.JarFile

private val version = "202-1.4.30-release-IJ8194.7"
private val fileToFind = "KlibLoadingMetadataCache"
private val tempDir = "/Users/maman/Documents/JarTraverser/tempDir"

fun main() {
    Files.createDirectories(Paths.get("tempDir"))
    val webpage = Jsoup.connect("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-ide/org/jetbrains/kotlin/")
        .maxBodySize(0).get()
    webpage.body().allElements.filter { it.`is`("a") && !it.text().contains("../") }.forEach { a ->
        val name = a.attr("title").removeSuffix("/")
        (a.attr("href") + "202-1.4.30-release-IJ8194.7/$name-$version.jar").saveTo("$tempDir/$name")?.run {
            val file = JarFile("$tempDir/$name")
            file.entries().asIterator().forEach {
                if (it.name.contains(fileToFind)) {
                    println("Found $fileToFind in ${it.name}, jar: $name in ${a.attr("href")}")
                }
            }
        } ?: println("failed to search for: $name")
//        val packageUrl = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-ide/org/jetbrains/kotlin/common/202-1.4.30-release-IJ8194.7/common-202-1.4.30-release-IJ8194.7.jar"
    }

}