package me.annenkov.julistaandroid.domain.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.ApiHelper
import me.annenkov.julistaandroid.domain.DateHelper
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.domain.model.Mark
import me.annenkov.julistaandroid.domain.model.Time
import me.annenkov.julistaandroid.domain.model.mos.ScheduleResponse
import me.annenkov.julistaandroid.presentation.activities.main.MainActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.net.ConnectException
import java.net.UnknownHostException

class NotificationsService : IntentService("NotificationsService") {
    private lateinit var mSchedule: List<ScheduleResponse>
    private val mNewMarks = arrayListOf<Mark>()

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe
    fun onNewMark(mark: Mark) {
        mNewMarks.add(mark)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return Service.START_STICKY
    }

    override fun onHandleIntent(intent: Intent?) {
        sendNotification("Дневник", "Загрузка...", "Загрузка...")

        val prefs = Preferences.getInstance(this)

        var currentDate = DateHelper.getDate().format("DD.MM.YYYY")
        while (true) {
            if (!prefs.notificationsSubscription && !prefs.notificationMain)
                stopForeground(true)
            mNewMarks.clear()
            mSchedule = try {
                ApiHelper.getInstance(this).getSchedule(
                        prefs.userPid!!,
                        prefs.userSecret!!,
                        currentDate,
                        currentDate
                )
            } catch (e: UnknownHostException) {
                sendNotification("Дневник", "Проверьте подключение к интернету.",
                        "Проверьте подключение к интернету.")
                Thread.sleep(60 * 1000)
                continue
            } catch (e: ConnectException) {
                sendNotification("Дневник", "Не удалось получить данные",
                        "Не удалось получить данные")
                Thread.sleep(60 * 1000)
                continue
            } catch (e: Exception) {
                sendNotification("Дневник", "Произошла ошибка",
                        "Не удалось получить данные")
                Thread.sleep(60 * 1000)
                continue
            }

            while (true) {
                if (!prefs.notificationsSubscription && !prefs.notificationMain)
                    stopForeground(true)

                if (DateHelper.getDate().format("DD.MM.YYYY") != currentDate) {
                    currentDate = DateHelper.getDate().format("DD.MM.YYYY")
                    break
                }

                updateNotification(mSchedule)

                Thread.sleep(10 * 1000)
            }
            Thread.sleep(60 * 1000)
        }
    }

    private fun updateNotification(schedule: List<ScheduleResponse>) {
        val currentTime = DateHelper.getCurrentTime()
        var endPreviousTime = Time(0, 0)

        val subjectMap = hashMapOf<String, ArrayList<Int>>()
        for (scheduleItem in schedule)
            for (mark in scheduleItem.marks) {
                if (subjectMap[scheduleItem.subject] == null)
                    subjectMap[scheduleItem.subject] = arrayListOf()
                subjectMap[scheduleItem.subject]!!.add(mark!!.score!!)
            }
        for (mark in mNewMarks) {
            if (subjectMap[mark.subject] == null)
                subjectMap[mark.subject] = arrayListOf()
            subjectMap[mark.subject]!!.add(mark.grade)
        }

        var marksText = "Оценки за день:\n"
        for ((subject, marks) in subjectMap) {
            marksText += "$subject — "

            var counter = 0
            for (mark in marks) {
                counter++
                marksText += mark
                marksText += if (counter != marks.size)
                    "/"
                else
                    "\n"
            }
        }
        if (subjectMap.isEmpty())
            marksText = "Оценок за день ещё нет."

        var body = "Уроков нет. Разверни для подробной информации."
        var expandBody = marksText
        for (scheduleItem in schedule) {
            val beginTime = Time(scheduleItem.beginTime[0], scheduleItem.beginTime[1])
            val endTime = Time(scheduleItem.endTime[0], scheduleItem.endTime[1])
            if (DateHelper.isTimeInInterval(DateHelper.getCurrentTime(),
                            beginTime,
                            endTime)) {
                val minRemaining = DateHelper.numTimeFrom(currentTime, endTime)
                body = "Сейчас ${scheduleItem.subject.toLowerCase()}. " +
                        "Осталось $minRemaining мин."
                expandBody = "Предмет: ${scheduleItem.subject.toLowerCase()}." +
                        "\nОсталось времени: $minRemaining мин." +
                        "\n\n$marksText"
            } else if (DateHelper.isTimeInInterval(DateHelper.getCurrentTime(),
                            endPreviousTime,
                            beginTime)) {
                val minRemaining = DateHelper.numTimeFrom(currentTime, beginTime)
                body = "Следующий урок ${scheduleItem.subject.toLowerCase()}. " +
                        "Осталось $minRemaining мин."
                expandBody = "Следующий урок: ${scheduleItem.subject.toLowerCase()}." +
                        "\nОсталось времени: $minRemaining мин." +
                        "\n\n$marksText"
            }

            endPreviousTime = Time(scheduleItem.endTime[0], scheduleItem.endTime[1])
        }

        sendNotification("Дневник",
                body,
                expandBody)
    }

    private fun sendNotification(title: String, body: String, expandBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(ID_CHANNEL,
                    "Main",
                    NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null, null)
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                    .createNotificationChannel(channel)
        }
        val notificationBuilder = NotificationCompat.Builder(this, ID_CHANNEL)
                .setSmallIcon(R.drawable.school)
                .setShowWhen(false)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(null)
                .setContentIntent(pendingIntent)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(expandBody))

        startForeground(ID_FOREGROUND, notificationBuilder.build())
    }

    companion object {
        private const val ID_FOREGROUND = 123
        private const val ID_CHANNEL = "channel_main"
    }
}