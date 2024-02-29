package com.example.twojastara

import EventAdapter
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar.FabAlignmentMode
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView


lateinit var recyclerView: RecyclerView
lateinit var mcv: MaterialCalendarView
lateinit var fab: FloatingActionButton

class calendar : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calendar, container, false)
        val db = DBHelper(this.requireContext(), null)
        mcv = root.findViewById(R.id.calendarView)
        recyclerView = root.findViewById(R.id.recyclerView)
        fab = root.findViewById(R.id.fab)

        mcv.selectionColor = Color.parseColor("#20FFFFFF")
        mcv.addDecorator(CurrentDayDecorator(activity))
        mcv.selectedDate = CalendarDay.today()
        mcv.setOnDateChangedListener { _, date, _ ->
            Toast.makeText(context, date.toString(), Toast.LENGTH_SHORT).show()}

        val eventList = mutableListOf<EventItem>()
        val adapter = EventAdapter(this.requireContext(), eventList)

        val cursor = db.query("SELECT * FROM Events")
        if (cursor!!.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val age = cursor.getString(cursor.getColumnIndex("age"))

                val foodItem = EventItem(name = name, desc = age)
                eventList.add(foodItem)
            }
            while(cursor.moveToNext())
        }
        cursor.close()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        fab.setOnClickListener { view ->
            db.sql("INSERT INTO Events VALUES(null, 'Event', 'data')")
            Snackbar.make(view, "Dodano", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
            eventList.clear()
            val db = DBHelper(this.requireContext(), null)
            val cursor = db.query("SELECT * FROM Events")
            if (cursor!!.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val age = cursor.getString(cursor.getColumnIndex("age"))

                    val foodItem = EventItem(name = name, desc = age)
                    eventList.add(foodItem)
                }
                while(cursor.moveToNext())
            }
            cursor.close()

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        }

        return root
    }

    fun fetchEvents() {

    }

    companion object {
    }
}