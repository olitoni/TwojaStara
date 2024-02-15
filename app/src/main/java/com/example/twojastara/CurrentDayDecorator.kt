package com.prolificinteractive.materialcalendarview.sample.decorators

import android.graphics.Typeface
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.time.LocalDate

/**
 * Decorate a day by making the text big and bold
 */
class OneDayDecorator : DayViewDecorator {
    private var date: CalendarDay?

    init {
        date = CalendarDay.today()
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return date != null && day == date
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(UnderlineSpan())
    }

    /**
     * We're changing the internals, so make sure to call [MaterialCalendarView.invalidateDecorators]
     */
    fun setDate(date: LocalDate?) {
        this.date = CalendarDay.today()
    }
}