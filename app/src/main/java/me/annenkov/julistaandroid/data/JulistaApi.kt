package me.annenkov.julistaandroid.data

import me.annenkov.julistaandroid.data.model.julista.Progress
import me.annenkov.julistaandroid.data.model.julista.ResultCheckNotificationsSubscription
import me.annenkov.julistaandroid.data.model.julista.ResultSetNotificationsSubscription
import me.annenkov.julistaandroid.data.model.julista.Schedule
import me.annenkov.julistaandroid.data.model.julista.account.Account
import me.annenkov.julistaandroid.data.model.julista.auth.Auth
import me.annenkov.julistaandroid.data.model.mos.homework.HomeworkBase
import me.annenkov.julistaandroid.data.model.mos.mark.Mark
import me.annenkov.julistaandroid.data.model.mos.profile.Profile
import me.annenkov.julistaandroid.data.model.mos.schedule.ScheduleItem
import retrofit2.Call
import retrofit2.http.*

interface JulistaApi {
    @FormUrlEncoded
    @POST("auth_user/")
    fun auth(@Field("login") login: String,
             @Field("password") password: String,
             @Field("fcm_token") fcmToken: String?,
             @Field("invite_code") inviteCode: String?): Call<Auth>

    @GET("schedule/")
    fun getScheduleJulista(@Query("token") token: String,
                           @Query("pid") pid: String,
                           @Query("from") from: String,
                           @Query("to") to: String): Call<List<Schedule>>

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

    @GET("core/api/student_profiles/{student_profile_id}")
    fun getProfile(@Header("Auth-Token") token: String,
                   @Header("Profile-Id") pid: Int,
                   @Path("student_profile_id") studentProfileId: Int,
                   @Query("academic_year_id") academicYearId: Int?,
                   @Query("with_groups") withGroups: Boolean?,
                   @Query("with_archived_groups") withArchivedGroups: Boolean?): Call<Profile>

    @GET("jersey/api/schedule_items")
    fun getSchedule(@Header("Auth-Token") token: String,
                    @Header("Profile-Id") pid: Int,
                    @Query("student_profile_id") studentProfileId: Int,
                    @Query("from") from: String,
                    @Query("to") to: String,
                    @Query("academic_year_id") academicYearId: Int?,
                    @Query("group_id") groups: String,
                    @Query("with_group_class_subject_info") withGroupClassSubjectInfo: Boolean,
                    @Query("with_lesson_info") withLessonInfo: Boolean): Call<List<ScheduleItem>>

    @GET("core/api/marks")
    fun getMarks(@Header("Auth-Token") token: String,
                 @Header("Profile-Id") pid: Int,
                 @Query("student_profile_id") studentProfileId: Int,
                 @Query("created_at_from") createdAtFrom: String,
                 @Query("created_at_to") createdAtTo: String,
                 @Query("academic_year_id") academicYearId: Int?,
                 @Query("page") page: Int?,
                 @Query("per_page") perPage: Int?): Call<List<Mark>>

    @GET("core/api/student_homeworks")
    fun getHomework(@Header("Auth-Token") token: String,
                    @Header("Profile-Id") pid: Int,
                    @Query("student_profile_id") studentProfileId: Int,
                    @Query("begin_date") beginDate: String,
                    @Query("end_date") endDate: String,
                    @Query("academic_year_id") academicYearId: Int?,
                    @Query("page") page: Int?,
                    @Query("per_page") perPage: Int?): Call<List<HomeworkBase>>

    @GET("reports/api/progress/json")
    fun getProgress(@Header("Auth-Token") token: String,
                    @Header("Profile-Id") pid: Int,
                    @Query("student_profile_id") studentProfileId: Int?,
                    @Query("academic_year_id") academicYearId: Int?): Call<List<Progress>>
}