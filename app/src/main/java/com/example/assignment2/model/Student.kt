package com.example.assignment2.model

data class Student(
    var name: String,
    var id : String,
    val avatarUrl: String,
    var isChecked: Boolean = false
)
