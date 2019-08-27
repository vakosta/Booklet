package com.booklet.bookletandroid.data

import com.booklet.bookletandroid.data.model.booklet.auth.Auth
import com.booklet.bookletandroid.data.model.booklet.events.Event
import com.booklet.bookletandroid.data.model.booklet.marks.Data
import com.booklet.bookletandroid.data.model.booklet.netschool_data.NetschoolData
import com.booklet.bookletandroid.data.model.booklet.students.Students
import com.booklet.bookletandroid.data.model.julista.account.Account
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BookletApi {
    @FormUrlEncoded
    @POST("auth/")
    suspend fun auth(
            @Field("diary") diary: String,
            @Field("login") login: String,
            @Field("password") password: String,
            @Field("region") region: Int?,
            @Field("province") province: Int?,
            @Field("city") city: Int?,
            @Field("school") school: Int?
    ): Response<Auth>

    @GET("auth/get_data/")
    suspend fun getNetschoolData(
            @Query("region") region: Int?,
            @Query("province") province: Int?,
            @Query("city") city: Int?
    ): Response<NetschoolData>

    @GET("diary/events/")
    suspend fun getEvents(
            @Query("id") id: Long,
            @Query("secret") secret: String
    ): Response<ArrayList<Event>>

    @GET("profile/students/")
    fun getStudents(
            @Query("id") id: Long,
            @Query("secret") secret: String
    ): Call<Students>

    @GET("diary/journal/dates/")
    fun getSchedule(@Query("id") id: Long,
                    @Query("secret") secret: String,
                    @Query("start") start: String,
                    @Query("end") end: String):
            Call<com.booklet.bookletandroid.data.model.booklet.journal.Response>

    @GET("diary/marks/all/")
    suspend fun getProgress(@Query("id") id: Long,
                            @Query("secret") secret: String): Response<Data>

    @GET("profile_screen/")
    fun getAccount(@Query("token") token: String,
                   @Query("pid") pid: String): Call<Account>
}