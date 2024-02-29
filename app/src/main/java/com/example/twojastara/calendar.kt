package com.example.twojastara

import EventAdapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Calendar


lateinit var recyclerView: RecyclerView
lateinit var mcv: MaterialCalendarView
lateinit var fab: FloatingActionButton

class CalendarFragment : Fragment() {
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
        val eventList = mutableListOf<EventItem>()
        val adapter = EventAdapter(this.requireContext(), eventList)

        mcv = root.findViewById(R.id.calendarView)
        recyclerView = root.findViewById(R.id.recyclerView)
        fab = root.findViewById(R.id.fab)

        mcv.selectionColor = Color.parseColor("#20FFFFFF")
        mcv.addDecorator(CurrentDayDecorator(activity))
        mcv.selectedDate = CalendarDay.today()
        mcv.setOnDateChangedListener { _, date, _ ->
            Toast.makeText(context, date.toString(), Toast.LENGTH_SHORT).show()
        }

        fetchEvents(db, eventList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        fab.setOnClickListener { view ->
//            db.sql("INSERT INTO Events VALUES(null, 'Event', 'data')")
//            eventList.clear()
//            fetchEvents(db, eventList)
//            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

            val builder = AlertDialog.Builder(this.requireContext())
            val inflater = layoutInflater
            builder.setTitle("Add Event")
            val dialogLayout = inflater.inflate(R.layout.event_popup, null)
            val nameInput = dialogLayout.findViewById<EditText>(R.id.nameInput)
            val descriptionInput = dialogLayout.findViewById<EditText>(R.id.descriptionInput)
            val dateInput = dialogLayout.findViewById<EditText>(R.id.dateInput)

            dateInput.setOnClickListener {
                datepickerDialog(dateInput)
            }

            builder.setView(dialogLayout)
            builder.setPositiveButton("Add") { _, _ ->
                Snackbar.make(view, "Dodano", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            }
            builder.setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            builder.show()
        }

        return root
    }

    private fun datepickerDialog(dateInput: EditText) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this.requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                val dat =
                    (dayOfMonth.toString().padStart(2, '0') + "." + (monthOfYear + 1).toString()
                        .padStart(2, '0') + "." + year)
                dateInput.setText(dat)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun fetchEvents(
        db: DBHelper,
        eventList: MutableList<EventItem>
    ) {
        val cursor = db.query("SELECT * FROM Events")
        if (cursor!!.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val age = cursor.getString(cursor.getColumnIndexOrThrow("age"))
                val event = EventItem(name = name, desc = age)
                eventList.add(event)
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    companion object {
    }
}