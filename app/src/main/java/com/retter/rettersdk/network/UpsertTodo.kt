package com.retter.rettersdk.network

class EditTodoRequest (
    var id: Number,
    var todo: String
)

data class UpsertTodoRequest (
    var todo: String
)

data class UpsertTodoResponse(val success: Boolean, val newTodo: ArrayList<Todos>, val error: String)
