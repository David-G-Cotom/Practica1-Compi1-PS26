package com.example.practica1_compi1_ps26.domain

import com.example.practica1_compi1_ps26.domain.entities.Bloque

class ParserUtilities(
    var generalId: Int = 1,
    var idSi: Int = 1,
    var idMientras: Int = 1,
    var idBloque: Int = 1
) {

    fun updateGeneralId(): Int {
        return this.generalId++
    }

    fun updateIdSi(): Int {
        return this.idSi++
    }

    fun updateIdMientras(): Int {
        return this.idMientras++
    }

    fun updateIdBloque(): Int {
        return this.idBloque++
    }

    fun generateBloque(text: ArrayList<String>): Bloque {
        val string: StringBuilder = StringBuilder()
        for (line in text) {
            string.append(line)
            string.append("\n")
        }
        return Bloque(this.generalId++, this.idBloque++, string.toString())
    }

}