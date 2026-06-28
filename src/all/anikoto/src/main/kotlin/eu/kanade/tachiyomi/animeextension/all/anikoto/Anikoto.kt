package eu.kanade.tachiyomi.animeextension.all.anikoto

import eu.kanade.tachiyomi.animesource.model.AnimesPage
import eu.kanade.tachiyomi.animesource.model.SAnime
import eu.kanade.tachiyomi.animesource.model.SEpisode
import eu.kanade.tachiyomi.animesource.model.Video
import eu.kanade.tachiyomi.animesource.online.AnimeHttpSource
import eu.kanade.tachiyomi.network.GET
import kotlinx.serialization.Serializable
import okhttp3.Request

class Anikoto : AnimeHttpSource() {
    override val name = "Anikoto"
    override val lang = "en"
    override val supportsLatest = true
    override val baseUrl = "https://anikototv.to"

    // Browse - Popular
    override fun popularAnimeRequest(page: Int) = GET("$baseUrl/most-viewed?page=$page")
    
    override fun popularAnimeParse(response: okhttp3.Response): AnimesPage {
        val doc = response.use { it.asJsoup() }
        val animeList = doc.select("div.anime-wrapper a").mapNotNull { 
            val url = it.attr("href").removePrefix("$baseUrl/")
            val title = it.select("h3").text()
            val thumbnail = it.select("img").attr("src")
            SAnime.create().apply {
                this.url = url
                this.title = title
                thumbnail_url = thumbnail
            }
        }
        return AnimesPage(animeList, animeList.size == 20)
    }

    // Browse - Latest
    override fun latestUpdatesRequest(page: Int) = GET("$baseUrl/latest-updated?page=$page")
    override fun latestUpdatesParse(response: okhttp3.Response) = popularAnimeParse(response)

    // Search
    override fun searchAnimeRequest(page: Int, query: String) = GET("$baseUrl/search?q=$query&page=$page")
    override fun searchAnimeParse(response: okhttp3.Response): AnimesPage {
        val doc = response.use { it.asJsoup() }
        val animeList = doc.select("div.anime-wrapper a").mapNotNull { 
            val url = it.attr("href").removePrefix("$baseUrl/")
            val title = it.select("h3").text()
            SAnime.create().apply {
                this.url = url
                this.title = title
            }
        }
        return AnimesPage(animeList, false)
    }

    // Details
    override fun animeDetailsRequest(anime: SAnime) = GET("$baseUrl/watch/${anime.url}/ep-1")
    
    override fun animeDetailsParse(response: okhttp3.Response): SAnime {
        val doc = response.use { it.asJsoup() }
        return SAnime.create().apply {
            title = doc.select("h1.title").text()
            thumbnail_url = doc.select("img.cover").attr("src")
            description = doc.select("div.description").text()
        }
    }

    // Episodes
    override fun episodeListRequest(anime: SAnime) = GET("$baseUrl/watch/${anime.url}/ep-1")
    
    override fun episodeListParse(response: okhttp3.Response): List<SEpisode> {
        val doc = response.use { it.asJsoup() }
        return doc.select("ul.episodes a").mapNotNull { 
            val num = it.text().filter { c -> c.isDigit() }
            SEpisode.create().apply {
                url = it.attr("href").removePrefix("$baseUrl/")
                name = "Episode $num"
                episode_number = num.toFloatOrNull() ?: 0f
            }
        }
    }

    // Video
    override fun videoListRequest(episode: SEpisode) = GET("$baseUrl/${episode.url}")
    
    override fun videoListParse(response: okhttp3.Response): List<Video> {
        // Parse video url from response
        return emptyList()
    }
}
