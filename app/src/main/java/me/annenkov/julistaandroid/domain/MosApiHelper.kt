package me.annenkov.julistaandroid.domain

import me.annenkov.julistaandroid.data.JulistaApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MosApiHelper {
    private var retrofit: Retrofit? = null

    fun getAPI(): JulistaApi {
        val BASE_URL = "https://dnevnik.mos.ru"

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        return retrofit!!.create<JulistaApi>(JulistaApi::class.java)
    }


}