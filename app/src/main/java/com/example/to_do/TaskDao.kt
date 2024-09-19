package com.example.to_do

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(task:Task)

    @Delete
    suspend fun deleteTask(task:Task)

    @Query("SELECT * FROM Task ORDER BY priority ASC")
    fun getTasksOrderedByPriority(): Flow<List<Task>>

    @Query("SELECT * FROM Task ORDER BY taskName ASC")
    fun getTasksOrderedByName(): Flow<List<Task>>


}