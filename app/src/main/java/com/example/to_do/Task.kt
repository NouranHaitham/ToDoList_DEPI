package com.example.to_do

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    val taskName:String,
    val priority:String,
    val date:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
)