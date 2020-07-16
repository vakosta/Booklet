package com.booklet.bookletandroid.presentation.fragments.newschedule

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.booklet.bookletandroid.domain.model.Result
import com.booklet.bookletandroid.domain.repository.ScheduleRepository
import com.booklet.bookletandroid.presentation.BaseViewModel
import com.booklet.bookletandroid.presentation.model.schedule.Day
import kotlinx.coroutines.launch

class NewScheduleViewModel(application: Application) : BaseViewModel(application) {
    override val repository = ScheduleRepository(application.applicationContext)

    val scheduleLiveData = MutableLiveData<Result<List<Day>>>()

    var pagerPosition = 5000
    var currentWeekday = Weekday.MONDAY
    var currentWeek = 2
    var withSaturdays = true

    /**
     * Метод для получения расписания.
     * Дополнительно берёт на себя ответственность, за определение
     * ошибки при запросе к серверу.
     *
     * После реализации запроса обновляет scheduleLiveData переменную.
     *
     * @param start это начальная дата расписания.
     * @param end это конечная дата расписания (включительно).
     */
    fun getSchedule(start: String, end: String) {
        scope.launch {
            // TODO: Реализовать заполнение LiveData.
            val data = repository.getSchedule(start, end, true)

            val kek = Result.success(data = data)
            scheduleLiveData.postValue(kek)
        }
    }

    /**
     * Класс-перечисление с днями недели.
     *
     * @param number это номер дня недели.
     */
    enum class Weekday(private val number: Int) {
        MONDAY(1),
        TUESDAY(2),
        WEDNESDAY(3),
        THURSDAY(4),
        FRIDAY(5),
        SATURDAY(6),
        SUNDAY(7)
    }
}