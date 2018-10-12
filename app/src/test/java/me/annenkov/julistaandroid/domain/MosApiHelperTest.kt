package me.annenkov.julistaandroid.domain

import org.junit.Test

class MosApiHelperTest {
    @Test
    fun getProfile() {
        MosApiHelper.getProfile("5df89173a7683539973fd451b225d82a", 2995882)
    }

    @Test
    fun getMarks() {
        MosApiHelper.getMarks("5df89173a7683539973fd451b225d82a", 2995882, "", "")
    }

    @Test
    fun getHomework() {
        MosApiHelper.getHomework("5df89173a7683539973fd451b225d82a", 2995882, "10.09.2018", "13.09.2018")
    }

    @Test
    fun getSchedule() {
        val result = MosApiHelper.getSchedule("5df89173a7683539973fd451b225d82a", 2995882, "10.09.2018", "13.09.2018")
        print(result)
    }

    @Test
    fun getProgress() {
        val result = MosApiHelper.getProgress("840c4a4d6dcbf5d5dfb24ca012360c3b", 2995882)
        print(result)
    }
}