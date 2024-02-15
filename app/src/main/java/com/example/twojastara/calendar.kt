package com.example.twojastara

import EventAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView


lateinit var recyclerView: RecyclerView
lateinit var materialCalendarView: MaterialCalendarView

/**
 * A simple [Fragment] subclass.
 * Use the [calendar.newInstance] factory method to
 * create an instance of this fragment.
 */
class calendar : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(com.example.twojastara.R.layout.fragment_calendar, container, false)
        recyclerView = root.findViewById(com.example.twojastara.R.id.recyclerView)
        materialCalendarView = root.findViewById(com.example.twojastara.R.id.calendarView)
        val eventList = mutableListOf<EventItem>()

        materialCalendarView.addDecorator(CurrentDayDecorator())

        for (i in 1..15){
            val name = "Item $i"
            val desc = "desc $i"

            val foodItem = EventItem(name = name, desc = desc)


            eventList.add(foodItem)
        }

//        val dates = ArrayList<CalendarDay>()
//        val mydate= CalendarDay.from(2024,  2, 4)
//        dates.add(mydate)

        val adapter = EventAdapter(this.requireContext(), eventList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        return root;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            calendar().apply {
            }
    }
}