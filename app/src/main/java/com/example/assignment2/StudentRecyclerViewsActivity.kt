package com.example.assignment2
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.model.Model
import com.example.assignment2.model.Student
import android.content.Intent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.assignment2.utils.Database

interface OnItemClickListener {
    fun onItemClick(position: Int)
    fun onItemClick(student: Student?)
}

class StudentRecyclerViewsActivity : AppCompatActivity() {

    private var students: MutableList<Student>? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_recycler_views)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        students = Database.students
        val recyclerView: RecyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = StudentRecyclerAdapter(students)

        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                // do something
            }
            override fun onItemClick(student: Student?) {
                // do something
            }
        }

        adapter.notifyDataSetChanged()

        // Add FAB listener
        val fabAddStudent: FloatingActionButton = findViewById(R.id.fab_add_student)
        fabAddStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }

        recyclerView.adapter = adapter

    }
    class StudentViewHolder(itemView: View, listener:OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        private var nameTextView: TextView? = null
        private var idTextView: TextView? = null
        private var checkBox: CheckBox? = null
        private var student: Student? = null

        init {
            nameTextView = itemView.findViewById(R.id.student_row_name_text_view)
            idTextView = itemView.findViewById(R.id.student_row_id_text_view)
            checkBox = itemView.findViewById(R.id.student_row_checkbox)

            checkBox?.apply {
                setOnClickListener{ view ->
                    (tag as? Int)?.let { tag ->
                        student?.isChecked = (view as? CheckBox)?.isChecked ?: false
                    }
                }
            }

            itemView.setOnClickListener{
                listener?.onItemClick(absoluteAdapterPosition)
                listener?.onItemClick(student)
            }
        }

        fun bind(student: Student?, position: Int) {
            this.student = student
            nameTextView?.text = student?.name
            idTextView?.text = student?.id
            checkBox?.apply {
                isChecked = student?.isChecked ?: false
                tag = position
            }
        }
    }

    class StudentRecyclerAdapter(private val students: List<Student>?) :
        RecyclerView.Adapter<StudentViewHolder>() {

        var listener: OnItemClickListener? = null
        override fun getItemCount(): Int = students?.size ?: 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val layoutInflation = LayoutInflater.from(parent.context)
            val view = layoutInflation.inflate(R.layout.student_list_row, parent, false)

            return StudentViewHolder(view, listener)
        }


        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            holder.bind(students?.get(position), position)
        }

    }
}