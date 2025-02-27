package com.example.assignment2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.assignment2.model.Student
import com.example.assignment2.utils.Database

class AddStudentActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.student_list_activity_list_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val saveButton: Button = findViewById(R.id.add_student_activity_save_button)
        val cancelButton: Button = findViewById(R.id.add_student_activity_cancel_button)

        val nameEditText: EditText = findViewById(R.id.add_student_activity_name_edit_text)
        val idEditText: EditText = findViewById(R.id.add_student_activity_id_edit_text)

        val saveMsgTextView: TextView = findViewById(R.id.add_student_activity_save_message_textview)

        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                val newStudent = Student(name, id, "url")
                Database.addStudent(newStudent)
                saveMsgTextView.text = "Successfully saved student ${newStudent.name} ID: ${newStudent.id}"
                val intent = Intent(this, StudentRecyclerViewsActivity::class.java)
                startActivity(intent)
            } else {
                saveMsgTextView.text = "Name and ID cannot be empty."
            }
        }
    }
}
