package com.example.chatapp.model.message

data class MessageModel(
    val message: String? ="",
    val fromTo: String? = "",
    val sendTo: String? = "",
    val timeStamp: String? = ""
)
