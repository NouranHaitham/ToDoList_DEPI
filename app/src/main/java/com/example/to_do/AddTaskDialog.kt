package com.example.to_do

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun AddTaskDialog(
    state: TaskState,
    onEvent: (TaskEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(TaskEvent.HideDialog)
        },
        title = { Text(text = "Add Task") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.taskName,
                    onValueChange = {
                        onEvent(TaskEvent.SetTaskName(it))
                    },
                    placeholder = {
                        Text(text = "Task Name:")
                    }
                )
                TextField(
                    value = state.priority,
                    onValueChange = {
                        onEvent(TaskEvent.SetPriority(it))  // Missing argument fixed
                    },
                    placeholder = {
                        Text(text = "Priority:")
                    }
                )
                TextField(
                    value = state.date,
                    onValueChange = {
                        onEvent(TaskEvent.SetDate(it))
                    },
                    placeholder = {
                        Text(text = "Date:")
                    }
                )
            }
        },
        confirmButton = {  // Updated from 'buttons' to 'confirmButton'
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(TaskEvent.SaveTask)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}
