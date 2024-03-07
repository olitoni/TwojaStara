package com.prolificinteractive.materialcalendarview.sample.decorators

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
class EventDecorator(private val color: Int, date: CalendarDay) : DayViewDecorator {
    private var date: CalendarDay

    init {
        this.date = date
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return date == day
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(8f, color))
    }
}