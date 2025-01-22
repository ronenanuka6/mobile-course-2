package com.example.assignment2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment2.model.Student
import com.example.assignment2.utils.Database

class EditStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val nameEditText = findViewById<EditText>(R.id.edit_student_name)
        val idEditText = findViewById<EditText>(R.id.edit_student_id)
        val saveMsgTextView = findViewById<TextView>(R.id.edit_student_activity_save_message_textview)
        val saveButton = findViewById<Button>(R.id.edit_student_activity_save_button)
        val cancelButton = findViewById<Button>(R.id.edit_student_activity_cancel_button)

        // Get the existing student data from intent
        val currentId = intent.getStringExtra("id")
        val currentStudent = Database.students.find { it.id == currentId }

        // Populate the fields with the current data
        currentStudent?.let {
            nameEditText.setText(it.name)
            idEditText.setText(it.id)
        }

        // Save changes to the database
        saveButton.setOnClickListener {
            val newName = nameEditText.text.toString()
            val newId = idEditText.text.toString()

            if (newName.isNotEmpty() && newId.isNotEmpty()) {
                // Update the student in the database
                currentStudent?.apply {
                    this.name = newName
                    this.id = newId
                }

                // Show success message
                saveMsgTextView.text = "Successfully updated student $newName ID: $newId"

                // Navigate back to the details page
                val intent = Intent(this, ShowStudentActivity::class.java)
                intent.putExtra("name", newName)
                intent.putExtra("id", newId)
                startActivity(intent)

                // Close the current activity
                finish()
            } else {
                saveMsgTextView.text = "Name and ID cannot be empty."
            }
        }

        // Cancel edit and close the activity
        cancelButton.setOnClickListener {
            finish()
        }
    }
}
