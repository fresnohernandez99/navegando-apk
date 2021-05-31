package cu.fresnohernandez99.app.navegando.data.json

import android.content.Context
import com.google.gson.Gson
import cu.fresnohernandez99.app.navegando.data.model.entities.Article
import cu.fresnohernandez99.app.navegando.data.model.entities.ArticleParts
import cu.fresnohernandez99.app.navegando.data.model.entities.QuestionArray
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class JsonManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        const val STRUCTURE_ARTICLE = "article_"
        const val articleCount = 22
    }

    private suspend fun loadTextFromAssetsAsync(fileName: String) = withContext(Dispatchers.IO) {
        val finalFilename = "${fileName}.json"
        var json = ""
        try {
            val inputStream = context.assets.open(finalFilename)

            val isr = InputStreamReader(inputStream)
            val br = BufferedReader(isr)

            val lines = br.readLines()
            lines.forEach { line ->
                json += line
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return@withContext json
    }

    suspend fun getArticle(fileName: String): Article = withContext(Dispatchers.IO) {
        val text = loadTextFromAssetsAsync(fileName)
        return@withContext Gson().fromJson(text, Article::class.java)
    }

    suspend fun getMenuArticles(start: Int): ArrayList<Article> = withContext(Dispatchers.IO) {
        var cont = start
        val articles: ArrayList<Article> = ArrayList()
        while (cont < start + 6 && cont <= articleCount) {
            articles.add(getArticle("${STRUCTURE_ARTICLE}${cont}"))
            cont++
        }
        return@withContext articles
    }

    suspend fun getArticleParts(fileName: String): ArticleParts = withContext(Dispatchers.IO) {
        val text = loadTextFromAssetsAsync(fileName)
        return@withContext Gson().fromJson(text, ArticleParts::class.java)
    }

}