package com.retter.rettersdk.network

data class AuthRequest (
    var userId: String = "userId",
    var identity: String? = "enduser"
)

data class AuthResponse(val data: Token? = null)
data class Token(val customToken: String?)