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
                val result = Result(progress.subjectName!!,
                        arrayListOf())
                for (period in progress.periods)
                    result.marks.add(period.finalMark ?: continue)
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
                        progress.periods!![position].finalMark,
                        progress.periods!![position].marks
                ))
            } catch (e: NullPointerException) {
            } catch (e: IndexOutOfBoundsException) {
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