package me.annenkov.julistaandroid.domain

import android.content.Context
import android.util.Log
import com.google.gson.JsonParseException
import me.annenkov.julistaandroid.data.BookletApi
import me.annenkov.julistaandroid.data.model.booklet.journal.SubjectsItem
import me.annenkov.julistaandroid.data.model.booklet.marks.Subject
import me.annenkov.julistaandroid.data.model.booklet.students.Students
import me.annenkov.julistaandroid.data.model.julista.account.Account
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException

class ApiHelper private constructor(val context: Context) {
    private var retrofit: Retrofit? = null

    private fun deleteCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            return dir.delete()
        } else return if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }

    private val rewriteResponseInterceptor = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")
        if (cacheControl == null
                || cacheControl.contains("no-store")
                || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate")
                || cacheControl.contains("max-age=0")) {
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=0")
                    .build()
        } else {
            originalResponse
        }
    }

    private val rewriteResponseInterceptorOffline = Interceptor { chain ->
        var request = chain.request()
        if (!hasNetwork()) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale="
                            + 60 * 60 * 24 * 7)
                    .build()
        }
        chain.proceed(request)
    }

    init {
        val cache = Cache(context.cacheDir, CACHE_SIZE)
        val client = OkHttpClient().newBuilder()
                .cache(cache)
                .addNetworkInterceptor(rewriteResponseInterceptor)
                .addInterceptor(rewriteResponseInterceptorOffline)
                .build()
        val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)

        retrofit = retrofitBuilder
                .baseUrl(BOOKLET_URL)
                .build()
    }

    fun getAPI(): BookletApi {
        return retrofit!!.create(BookletApi::class.java)
    }

    private fun hasNetwork(): Boolean {
        try {
            val e = context.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
            val activeNetwork = e.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        } catch (e: Exception) {
            Log.w("API", e.toString())
        }
        return false
    }

    fun getStudents(id: Long, secret: String): Call<Students> {
        return getAPI().getStudents(id, secret)
    }

    fun getAccount(token: String, pid: String): Call<Account> {
        return getAPI().getAccount(token, pid)
    }

    @Throws(IOException::class, JsonParseException::class)
    fun getSchedule(pid: Long,
                    secret: String?,
                    from: String,
                    to: String): List<SubjectsItem?> {
        val response = getAPI()
                .getSchedule(pid,
                        secret!!,
                        from,
                        to)
                .execute()
        val days = response.body()?.data?.days
        return if (days != null && days.isNotEmpty())
            days[0]?.subjects ?: arrayListOf()
        else
            arrayListOf()
    }

    @Throws(IOException::class, JsonParseException::class)
    fun getProgress(id: Long, secret: String): List<Subject?>? {
        return getAPI()
                .getProgress(id, secret)
                .execute().body()!!.data
    }

    companion object : SingletonHolder<ApiHelper, Context>(::ApiHelper) {
        private const val BOOKLET_URL = "https://bklet.ml/api/"

        private const val CACHE_SIZE = (1 * 1024 * 1024).toLong()
    }
}