package com.retter.rettersdk.network

data class GetTodosResponse(val success: Boolean, val todos: ArrayList<Todos>)
data class Todos(val todo: String, val id: Number)