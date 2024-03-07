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
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.sample.decorators.EventDecorator
import java.time.LocalDate
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
        mcv.selectedDate = CalendarDay.today()
        mcv.addDecorator(CurrentDayDecorator(activity))
        mcv.setOnDateChangedListener { _, date, _ ->
            val monthString = date.month.toString().padStart(2, '0')
            val dayString = date.day.toString().padStart(2, '0')
            val dateString = "${date.year}-${monthString}-${dayString}"

            refreshEvents(eventList, db, dateString, adapter)

        }

        val cursor = db.query("SELECT date FROM Events")
        if (cursor!!.moveToFirst()) {
            do {
                val date = cursor.getString(0)
                val localDate = LocalDate.parse(date)
                mcv.addDecorator(
                    EventDecorator(
                        Color.RED,
                        CalendarDay.from(localDate.year, localDate.monthValue, localDate.dayOfMonth)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()

        fab.setOnClickListener { view ->
            val builder = AlertDialog.Builder(this.requireContext())
            val inflater = layoutInflater
            builder.setTitle("Add Event")
            val dialogLayout = inflater.inflate(R.layout.event_popup, null)
            val nameInput = dialogLayout.findViewById<EditText>(R.id.nameInput)
            val descriptionInput = dialogLayout.findViewById<EditText>(R.id.descriptionInput)
            val dateInput = dialogLayout.findViewById<EditText>(R.id.dateInput)
            val timeInput = dialogLayout.findViewById<EditText>(R.id.timeInput)

            dateInput.setOnClickListener {
                val c = Calendar.getInstance()
                datepickerDialog(dateInput, c)
            }

            timeInput.setOnClickListener {
                val c = Calendar.getInstance()
                timepickerDialog(timeInput, c)
            }

            builder.setView(dialogLayout)
            builder.setPositiveButton("Add") { _, _ ->
                db.sql("INSERT INTO Events VALUES(null, '${nameInput.text}', '${descriptionInput.text}', '${dateInput.text}', '${timeInput.text}');")
                Snackbar.make(view, "Dodano", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()

                    refreshEvents(eventList, db, dateInput.text.toString(), adapter)

            }
            builder.setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            builder.show()
        }

        return root
    }

    private fun refreshEvents(
        eventList: MutableList<EventItem>,
        db: DBHelper,
        dateString: String,
        adapter: EventAdapter
    ) {
        eventList.clear()
        val cursor =
            db.query("SELECT name, description, time FROM Events WHERE date = '${dateString}';")
        if (cursor!!.moveToFirst()) {
            do {
                val name = cursor.getString(0)
                val desc = cursor.getString(1)
                val time = cursor.getString(2)
                val event = EventItem(name = name, desc = desc, date = dateString, time = time)
                eventList.add(event)
            } while (cursor.moveToNext())
        }
        cursor.close()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
    }

    private fun datepickerDialog(dateInput: EditText, calendar: Calendar) {
        val datePickerDialog = DatePickerDialog(
            this.requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val dat =
                    "${year}-${
                        (monthOfYear + 1).toString().padStart(2, '0')
                    }-${dayOfMonth.toString().padStart(2, '0')}"
                dateInput.setText(dat)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun timepickerDialog(timeInput: EditText, calendar: Calendar) {
        val c = Calendar.getInstance()


        MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_24H)
            .setHour(c.get(Calendar.HOUR))
            .setMinute(c.get(Calendar.MINUTE))
            .setTitleText("Set time")
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    timeInput.setText(
                        hour.toString().padStart(2, '0') + ":" + minute.toString().padStart(2, '0')
                    )
                }
            }
            .show(parentFragmentManager, "Calendar")
    }

    companion object {
    }
}