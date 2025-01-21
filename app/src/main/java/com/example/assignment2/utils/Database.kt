package com.example.assignment2.utils

import com.example.assignment2.model.Student

object Database {
    val students = mutableListOf<Student>()

    fun addStudent(student: Student) {
        students.add(student)
    }
}
