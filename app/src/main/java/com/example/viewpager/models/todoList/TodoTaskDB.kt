package com.example.viewpager.models.todoList

object TodoTaskDB {
    private var _tasks = listOf<TodoTask>(
        TodoTask(1, "Task1", TodoTaskState.PROGRESS),
        TodoTask(2, "Task2", TodoTaskState.DELETED),
        TodoTask(3, "Task3", TodoTaskState.DONE),
        TodoTask(4, "Task4", TodoTaskState.DONE),
        TodoTask(5, "Task5", TodoTaskState.DONE),
        TodoTask(6, "Task6", TodoTaskState.DELETED),
        TodoTask(7, "Task7", TodoTaskState.PROGRESS),
        TodoTask(8, "Task8", TodoTaskState.PROGRESS),
        TodoTask(9, "Task9", TodoTaskState.DELETED),
        TodoTask(10, "Task10", TodoTaskState.DELETED),
        TodoTask(11, "Task11", TodoTaskState.DONE),
        TodoTask(12, "Task12", TodoTaskState.PROGRESS),
    )

    fun tasks(): List<TodoTask> = _tasks
}