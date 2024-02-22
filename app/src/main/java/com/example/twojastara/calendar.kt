package com.example.twojastara

import EventAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.MaterialCalendarView


lateinit var recyclerView: RecyclerView
lateinit var mcv: MaterialCalendarView

class calendar : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calendar, container, false)
        mcv = root.findViewById(R.id.calendarView)
        mcv.selectionColor = Color.parseColor("#51FFFFFF")
        mcv.addDecorator(CurrentDayDecorator(activity))
        recyclerView = root.findViewById(R.id.recyclerView)

        mcv.setOnDateChangedListener { _, date, _ -> Toast.makeText(context, date.toString(), Toast.LENGTH_SHORT).show() }

        val eventList = mutableListOf<EventItem>()
        val adapter = EventAdapter(this.requireContext(), eventList)

        for (i in 1..15){
            val name = "Item $i"
            val desc = "desc $i"

            val foodItem = EventItem(name = name, desc = desc)


            eventList.add(foodItem)
        }

//        val dates = ArrayList<CalendarDay>()
//        val mydate= CalendarDay.from(2024,  2, 4)
//        dates.add(mydate)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        return root
    }

    companion object {
    }
}