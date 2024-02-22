package com.example.twojastara

import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import android.app.Activity
import android.graphics.Color

class CurrentDayDecorator(context: Activity?): DayViewDecorator {
    private var date: CalendarDay? = CalendarDay.today()
    private val drawable: Drawable?

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return date != null && day == date
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(Color.BLACK))
        view.setBackgroundDrawable(drawable!!)
    }
    init {
        drawable = ContextCompat.getDrawable(context!!, R.drawable.current_day)
    }
}