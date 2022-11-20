package com.retter.rettersdk.network

class GetTodosRequest {
    var userId: String = "ali"
    var identity: String? = "enduser"
}

data class GetTodosResponse(val success: Boolean, val todos: ArrayList<Todos>)
data class Todos(val todo: String, val id: Number)