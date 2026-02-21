package com.example.practica1_compi1_ps26.domain.entities

import java.io.Serializable

data class StructureReport (
    val structure: String,
    val line: Int,
    val condition: String
) : Serializable