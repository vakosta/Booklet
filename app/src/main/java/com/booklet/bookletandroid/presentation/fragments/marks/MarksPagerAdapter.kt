package com.booklet.bookletandroid.presentation.fragments.marks

import com.booklet.bookletandroid.data.model.booklet.marks.Subject
import com.booklet.bookletandroid.presentation.fragments.markscard.MarksCardFragment
import com.booklet.bookletandroid.presentation.model.Result

class MarksPagerAdapter(
        fm: androidx.fragment.app.FragmentManager,
        private val progresses: List<Subject>
) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        if (position == count - 1) {
            val results = arrayListOf<Result>()
            for (progress in progresses) {
                val yearMark = try {
                    progress.yearMark?.toInt() ?: 0
                } catch (e: NumberFormatException) {
                    1
                }
                val result = Result(progress.name!!,
                        arrayListOf(), yearMark)
                for (period in progress.periods!!)
                    try {
                        result.marks.add(period!!.finalMark?.toInt() ?: continue)
                    } catch (e: NumberFormatException) {
                    }
                results.add(result)
            }
            return MarksCardFragment.newInstanceResults(results)
        }

        val resultProgresses = arrayListOf<com.booklet.bookletandroid.presentation.model.Progress>()
        for (progress in progresses) {
            try {
                resultProgresses.add(com.booklet.bookletandroid.presentation.model.Progress(
                        progress.periods!![position]!!.title!!,
                        progress.name!!,
                        //progress.periods!![position].avgFive.toDouble(),
                        5.14,
                        progress.periods[position]!!.finalMark?.toInt(),
                        progress.periods[position]!!.marks!!
                ))
            } catch (e: NullPointerException) {
            } catch (e: IndexOutOfBoundsException) {
            } catch (e: NumberFormatException) {
            }
        }
        return MarksCardFragment.newInstance(resultProgresses)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
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