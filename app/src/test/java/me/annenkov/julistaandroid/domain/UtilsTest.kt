package me.annenkov.julistaandroid.domain

import org.junit.Test

class UtilsTest {
    @Test
    fun extractUrls() {
        val urls = Utils.extractUrls("http://vk.com lol kek https://lol.ru")

        for (url in urls)
            println(url)
    }
}