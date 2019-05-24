package me.annenkov.julistaandroid.presentation.fragments.marks

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import me.annenkov.julistaandroid.domain.model.mos.ProgressResponse
import me.annenkov.julistaandroid.presentation.fragments.markscard.MarksCardFragment
import me.annenkov.julistaandroid.presentation.model.Result

class MarksPagerAdapter(
        fm: FragmentManager,
        private val progresses: List<ProgressResponse>
) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == count - 1) {
            val results = arrayListOf<Result>()
            for (progress in progresses) {
                val result = Result(progress.subjectName,
                        arrayListOf(), progress.final)
                for (period in progress.periods)
                    try {
                        result.marks.add(period.finalMark?.toInt() ?: continue)
                    } catch (e: NumberFormatException) {
                    }
                results.add(result)
            }
            return MarksCardFragment.newInstanceResults(results)
        }

        val resultProgresses = arrayListOf<me.annenkov.julistaandroid.presentation.model.Progress>()
        for (progress in progresses) {
            try {
                resultProgresses.add(me.annenkov.julistaandroid.presentation.model.Progress(
                        progress.periods!![position].name,
                        progress.subjectName!!,
                        progress.periods!![position].avgFive.toDouble(),
                        progress.periods!![position].finalMark?.toInt(),
                        progress.periods!![position].marks
                ))
            } catch (e: NullPointerException) {
            } catch (e: IndexOutOfBoundsException) {
            } catch (e: NumberFormatException) {
            }
        }
        return MarksCardFragment.newInstance(resultProgresses)
    }

    override fun getItemPosition(`object`: Any): Int {
        return FragmentStatePagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        var maxSize = 0
        for (progress in progresses) {
            val periods = progress.periods?.size ?: 0
            val curSize = periods + 1
            if (curSize > maxSize)
                maxSize = curSize
        }
        return maxSize
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            count - 1 -> "ИТОГОВЫЕ"
            else -> (position + 1).toString()
        }
    }
}