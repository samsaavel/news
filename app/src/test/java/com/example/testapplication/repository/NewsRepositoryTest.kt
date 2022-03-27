package com.example.testapplication.repository

import com.example.testapplication.data.ApiResult
import com.example.testapplication.data.Article
import com.example.testapplication.data.NewsResponse
import com.example.testapplication.data.Source
import com.example.testapplication.network.NewsAPI
import com.example.testapplication.utils.castDate
import com.example.testapplication.utils.ifEmptySubstituteTo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NewsRepositoryTest {

    lateinit var newsRepo: NewsRepository

    @MockK
    lateinit var newsAPI: NewsAPI
    val fakeArticle = Article(
        source = Source("1", "NYTimes"),
        author = "Speedy Gonzalez",
        title = "FakeNews",
        description = "Description fake the news",
        url = "https://www.typingclub.com/sportal/",
        urlToImage = "https://onlinetyping.org/typing-lessons/images/touch-typing-keyboard.png",
        publishedAt = "1994-11-05T08:15:30-05:00", content = "Content for a fake new"
    )
    val fakeList: List<Article> = listOf(fakeArticle)
    val fakeNews = NewsResponse(
        status = "200", totalResults = 1, articles = fakeList
    )
    val fakeCountry = ""
    val fakeDefaultCountry = "default"
    val fakePattern = "yyyy-MM-dd HH:mm:ss"
    val expectedDate = "yyyy-MM-dd"

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        newsRepo = NewsRepository(newsAPI)
    }

    @Test
    fun testGetNewsAPIReturnDataWithoutModifies() = runBlocking {
        //Given
        coEvery { newsAPI.getAllHeadlines(fakeCountry) } returns fakeNews

        //When
        val response = newsRepo.getAllNews(fakeCountry)

        //Then
        assert(response == ApiResult.Success(fakeNews))
        coVerify { newsAPI.getAllHeadlines(fakeCountry) }
    }

    @Test
    fun testDefaultCountryIfCountryIsEmpty() = runBlocking {
        //Given
        coEvery { newsAPI.getAllHeadlines("") } throws IllegalStateException("error en el api")

        //When
        val response = newsRepo.getAllNews("")

        //Then
        assert(response == ApiResult.Failure)
    }

    @Test
    fun testUtilIfIsEmpty() {
        //when
        val result = fakeCountry.ifEmptySubstituteTo(fakeDefaultCountry)

        //then
        assert(result == fakeDefaultCountry)
    }

    @Test
    fun testGetDateWithDifferentPatterns() {
        //when
        val result = fakePattern.castDate(" ")

        //then
        assert(result == expectedDate)
    }
}