package me.annenkov.julistaandroid.data

import me.annenkov.julistaandroid.data.model.booklet.auth.Auth
import me.annenkov.julistaandroid.data.model.booklet.students.Students
import me.annenkov.julistaandroid.data.model.julista.Progress
import me.annenkov.julistaandroid.data.model.julista.ResultCheckNotificationsSubscription
import me.annenkov.julistaandroid.data.model.julista.ResultSetNotificationsSubscription
import me.annenkov.julistaandroid.data.model.julista.account.Account
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface JulistaApi {
    @FormUrlEncoded
    @POST("auth/")
    suspend fun auth(@Field("diary") diary: String,
                     @Field("login") login: String,
                     @Field("password") password: String): Response<Auth>

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
            Call<me.annenkov.julistaandroid.data.model.booklet.journal.Response>

    @GET("progress/")
    fun getProgressJulista(@Query("token") token: String,
                           @Query("pid") pid: String): Call<List<Progress>>

    @GET("profile_screen/")
    fun getAccount(@Query("token") token: String,
                   @Query("pid") pid: String): Call<Account>

    @GET("notifications_subscription/")
    fun checkNotificationsSubscription(@Query("pid") pid: String): Call<ResultCheckNotificationsSubscription>

    @FormUrlEncoded
    @POST("notifications_subscription/")
    fun setNotificationsSubscription(@Field("pid") pid: String,
                                     @Field("token") token: String,
                                     @Field("hash") hash: String): Call<ResultSetNotificationsSubscription>

    @GET("reports/api/progress/json")
    fun getProgress(@Header("Auth-Token") token: String,
                    @Header("Profile-Id") pid: Int,
                    @Query("student_profile_id") studentProfileId: Int?,
                    @Query("academic_year_id") academicYearId: Int?): Call<List<Progress>>
}