package com.example.to_do

data class TaskState(
    val tasks:List<Task> = emptyList(),
    val taskName:String="",
    val priority:String="",
    val date:String="",
    val isAddingContact:Boolean=false,
    val sortType: SortType= SortType.TASK_NAME
)
