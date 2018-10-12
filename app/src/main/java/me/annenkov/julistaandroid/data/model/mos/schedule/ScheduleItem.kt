package me.annenkov.julistaandroid.data.model.mos.schedule

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ScheduleItem {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("day_number")
    @Expose
    var dayNumber: Int? = null

    @SerializedName("lesson_number")
    @Expose
    var lessonNumber: Int? = null

    @SerializedName("study_ordinal")
    @Expose
    var studyOrdinal: Int? = null

    @SerializedName("group_id")
    @Expose
    var groupId: Int? = null

    @SerializedName("group_name")
    @Expose
    var groupName: String? = null

    @SerializedName("subject_id")
    @Expose
    var subjectId: Int? = null

    @SerializedName("subject_name")
    @Expose
    var subjectName: String? = null

    @SerializedName("class_unit_id")
    @Expose
    var classUnitId: Int? = null

    @SerializedName("schedule_id")
    @Expose
    var scheduleId: Int? = null

    @SerializedName("teacher_id")
    @Expose
    var teacherId: Int? = null

    @SerializedName("room_id")
    @Expose
    var roomId: Int? = null

    @SerializedName("room_name")
    @Expose
    var roomName: String? = null

    @SerializedName("ordinal")
    @Expose
    var ordinal: Int? = null

    @SerializedName("building_id")
    @Expose
    var buildingId: Int? = null

    @SerializedName("bell_day_timetable_id")
    @Expose
    var bellDayTimetableId: Int? = null

    @SerializedName("bell_timetable_id")
    @Expose
    var bellTimetableId: Int? = null

    @SerializedName("bell_id")
    @Expose
    var bellId: Int? = null

    @SerializedName("date")
    @Expose
    var date: List<Int>? = null

    @SerializedName("time")
    @Expose
    var time: List<Int>? = null

    @SerializedName("calendar_lesson_id")
    @Expose
    var calendarLessonId: Int? = null

    @SerializedName("lesson_id")
    @Expose
    var lessonId: Int? = null

    @SerializedName("lesson_name")
    @Expose
    var lessonName: String? = null

    @SerializedName("topic_id")
    @Expose
    var topicId: Int? = null

    @SerializedName("topic_name")
    @Expose
    var topicName: String? = null

    @SerializedName("module_id")
    @Expose
    var moduleId: Int? = null

    @SerializedName("module_name")
    @Expose
    var moduleName: String? = null

    @SerializedName("lesson_plan_id")
    @Expose
    var lessonPlanId: Int? = null

    @SerializedName("duration")
    @Expose
    var duration: Int? = null

    @SerializedName("calendar_plan_id")
    @Expose
    var calendarPlanId: Int? = null

    @SerializedName("periods_schedule_id")
    @Expose
    var periodsScheduleId: Int? = null

    @SerializedName("homeworks_to_give")
    @Expose
    var homeworksToGive: List<HomeworkToGive>? = null

    @SerializedName("homeworks_to_verify")
    @Expose
    var homeworksToVerify: List<HomeworkToVerify>? = null

    @SerializedName("controllable_items")
    @Expose
    var controllableItems: Any? = null

    @SerializedName("current")
    @Expose
    var current: Boolean? = null

    @SerializedName("replaced")
    @Expose
    var replaced: Boolean? = null

    @SerializedName("is_transferred")
    @Expose
    var isTransferred: Boolean? = null

    @SerializedName("transferring_id")
    @Expose
    var transferringId: Any? = null

    @SerializedName("transferred_to_date")
    @Expose
    var transferredToDate: Any? = null

    @SerializedName("transferred_from_date")
    @Expose
    var transferredFromDate: Any? = null

    @SerializedName("is_home_based")
    @Expose
    var isHomeBased: Boolean? = null

    @SerializedName("home_base_period_id")
    @Expose
    var homeBasePeriodId: Any? = null

    @SerializedName("comment")
    @Expose
    var comment: String? = null

    @SerializedName("lesson_type")
    @Expose
    var lessonType: String? = null

    @SerializedName("course_lesson_type")
    @Expose
    var courseLessonType: Any? = null

    @SerializedName("scripts")
    @Expose
    var scripts: Any? = null
}
