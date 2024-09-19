package com.example.to_do

sealed interface TaskEvent {

    data object SaveTask:TaskEvent
    data class SetTaskName(val name:String):TaskEvent
    data class SetDate(val date:String):TaskEvent
    data class SetPriority(val priority:String):TaskEvent
    data object  ShowDialog:TaskEvent
    data object  HideDialog:TaskEvent
    data class SortContacts(val sortType:SortType):TaskEvent
    data class DeleteTask(val task:Task):TaskEvent


}