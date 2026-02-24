package com.example.practica1_compi1_ps26.domain.analyzers

import com.example.practica1_compi1_ps26.domain.Interpreter
import com.example.practica1_compi1_ps26.domain.entities.ErrorReport
import com.example.practica1_compi1_ps26.domain.entities.OperatorReport
import com.example.practica1_compi1_ps26.domain.entities.StructureReport
import java.io.StringReader

class Analyzer {
    var operators: ArrayList<OperatorReport> = ArrayList()
    var structures: ArrayList<StructureReport> = ArrayList()

    var interpreter: Interpreter = Interpreter(ArrayList())

    var errors: ArrayList<ErrorReport> = ArrayList()

    fun analyze(text: String): String {
        var result: String
        val lexer = Lexer(StringReader(text))
        val parser = Parser(lexer)
        this.interpreter = parser.getInterpreter()
        try {
            parser.parse()
            if (lexer.getLexicalErrors().isEmpty() && parser.getSyntaxErrors().isEmpty()) {
                this.operators = parser.getOperatorsReport()
                this.structures = parser.getStructuresReport()
            } else {
                for (error in lexer.getLexicalErrors()) {
                    this.errors.add(error)
                }
                for (error in parser.getSyntaxErrors()) {
                    this.errors.add(error)
                }
            }
            result = "Analisis Finalizado"
        } catch (e: Exception) {
            e.printStackTrace()
            e.message
            e.cause
            e.stackTrace
            result = "Error Inesperado -> " + e.message
        }
        return result
    }

}