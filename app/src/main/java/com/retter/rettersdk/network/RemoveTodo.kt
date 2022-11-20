package com.retter.rettersdk.network

data class RemoveTodoRequest (
    var id: Number
)

data class RemoveTodoResponse(val success: Boolean, val message: String)
