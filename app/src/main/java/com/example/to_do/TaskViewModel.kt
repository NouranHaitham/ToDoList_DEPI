package com.example.to_do

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModel(
    private val dao:TaskDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.TASK_NAME)
    private val _tasks = _sortType.flatMapLatest { sortType ->
        when(sortType)
        {
            SortType.PRIORITY -> dao.getTasksOrderedByPriority()
            SortType.TASK_NAME -> dao.getTasksOrderedByName()
        }
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(), emptyList())

    private val _state= MutableStateFlow(TaskState())

    val state = combine(_state,_sortType,_tasks){ state,sortType,tasks ->
        state.copy(
            tasks = tasks,
            sortType=sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskState())
    fun onEvent(event:TaskEvent){

        when(event){
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    dao.deleteTask(event.task)
                }
            }
            TaskEvent.HideDialog -> {
                _state.update { it.copy(
                        isAddingContact = false
                    ) }
            }
            TaskEvent.SaveTask -> {

                val taskName = state.value.taskName
                val priority = state.value.priority
                val date = state.value.date

                if(taskName.isBlank() || priority.isBlank() || date.isBlank())
                {
                    return
                }

                val task = Task(
                    taskName = taskName,
                    priority = priority,
                    date = date
                )
                viewModelScope.launch {
                    dao.upsertTask(task)
                }
                _state.update {
                    it.copy(
                        isAddingContact = false,
                        taskName = "",
                        priority = "",
                        date = ""
                    )
                }
            }
            is TaskEvent.SetDate ->{
                _state.update { it.copy(
                    date = event.date
                ) }
            }
            is TaskEvent.SetPriority ->{
                _state.update { it.copy(
                    priority = event.priority
                ) }
            }
            is TaskEvent.SetTaskName ->{
                _state.update { it.copy(
                    taskName = event.name
                ) }
            }
            TaskEvent.ShowDialog ->  {
                _state.update { it.copy(
                    isAddingContact = true
                ) }
            }
            is TaskEvent.SortContacts ->  {
                _sortType.value = event.sortType
            }
        }

    }

}