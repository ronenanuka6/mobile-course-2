package com.example.assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment2.utils.Database

class ShowStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_student)

        // Get data from intent
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        val isCheckedDB = intent.getBooleanExtra("isChecked", false)

        // Set data in UI
        findViewById<TextView>(R.id.student_details_name).text = name
        findViewById<TextView>(R.id.student_details_id).text = id
        findViewById<CheckBox>(R.id.show_student_checkbox).apply {
            isChecked = isCheckedDB
            isEnabled = false // Make the checkbox read-only
        }

        // Cancel button to close the page
        findViewById<Button>(R.id.add_student_activity_cancel_button).setOnClickListener {
            val intent = Intent(this, StudentRecyclerViewsActivity::class.java)
            startActivity(intent)
            finish() // Optionally close the current activity to prevent back navigation
        }

        // Edit button to navigate to edit page
        findViewById<Button>(R.id.add_student_activity_edit_button).setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("id", id)
            intent.putExtra("isCheckedDB", isCheckedDB)
            startActivity(intent)
        }
    }

    // Get latest data from DB
    override fun onResume() {
        super.onResume()

        val updatedStudent = Database.students.find { it.id == intent.getStringExtra("id") }

        // Update the UI with the latest data
        findViewById<TextView>(R.id.student_details_name).text = updatedStudent?.name
        findViewById<TextView>(R.id.student_details_id).text = updatedStudent?.id
    }
}
