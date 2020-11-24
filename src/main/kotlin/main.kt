import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.jar.JarFile

val ijVersion = "201.7223.92"
val category = "com.jetbrains.intellij.platform"
val tempDir = "/Users/maman/Documents/JarTraverser/tempDir"
val fileToFind = "ApplicationManager"

fun String.saveTo(path: String) {
    if(!File(path).exists()) {
        URL(this).openStream().use { input ->
            FileOutputStream(File(path)).use { output ->
                input.copyTo(output)
            }
        }
    }
}

fun main() {
    Files.createDirectories(Paths.get("tempDir"))
    val webpage = Jsoup.connect("https://www.jetbrains.com/intellij-repository/releases").maxBodySize(0).get()
    val header = webpage.body().allElements.first { it.`is`("h2") && it.text().contains(category) }
    val ijRow =
        header.nextElementSibling().getElementsByTag("tr").first { it.children().first().text().contains(ijVersion) }
    ijRow.children().last().getElementsByTag("a").forEach { tagWithJar ->
        val name = tagWithJar.text()
        tagWithJar.attr("href").takeIf { it.endsWith(".jar") }?.saveTo("$tempDir/$name")?.run {
            val file = JarFile("$tempDir/$name")
            file.entries().asIterator().forEach {
                if (it.name.contains(fileToFind)) {
                    println("Found $fileToFind in ${it.name}, jar: $name in ${tagWithJar.attr("href")}")
                }
            }
        }
    }
    File(tempDir).deleteRecursively()
}
