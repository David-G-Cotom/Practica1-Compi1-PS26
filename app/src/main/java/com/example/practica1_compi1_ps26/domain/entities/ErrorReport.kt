package com.example.practica1_compi1_ps26.domain.entities

import java.io.Serializable

data class ErrorReport(
    val lexeme: String,
    val line: Int,
    val column: Int,
    val type: String,
    val description: String
) : Serializable