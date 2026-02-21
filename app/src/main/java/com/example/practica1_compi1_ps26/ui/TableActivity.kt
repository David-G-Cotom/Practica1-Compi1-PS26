package com.example.practica1_compi1_ps26.ui

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica1_compi1_ps26.MainActivity.Companion.ERROR_REPORT
import com.example.practica1_compi1_ps26.MainActivity.Companion.OPERATOR_REPORT
import com.example.practica1_compi1_ps26.MainActivity.Companion.REPORT_TYPE
import com.example.practica1_compi1_ps26.MainActivity.Companion.STRUCTURE_REPORT
import com.example.practica1_compi1_ps26.R
import com.example.practica1_compi1_ps26.domain.entities.ErrorReport
import com.example.practica1_compi1_ps26.domain.entities.OperatorReport
import com.example.practica1_compi1_ps26.domain.entities.StructureReport

class TableActivity : AppCompatActivity() {

    private lateinit var tbTemplate: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_table)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.tbTemplate = findViewById(R.id.tbTemplate)
        this.tbTemplate.removeAllViews()
        val reportType: Int = intent.extras?.getInt(REPORT_TYPE) ?: 0
        this.reportType(reportType)
    }

    fun reportType(reportType: Int) {
        when (reportType) {
            1 -> this.generateOperatorReport()
            2 -> this.generateStructureReport()
            3 -> this.generateErrorReport()
        }
    }

    fun generateOperatorReport() {
        val operatorReport =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(
                    OPERATOR_REPORT,
                    ArrayList::class.java
                ) as? ArrayList<OperatorReport>
            } else {
                @Suppress("UNCHECKED_CAST")
                intent.getSerializableExtra(OPERATOR_REPORT) as? ArrayList<OperatorReport>
            }

        val row = layoutInflater.inflate(R.layout.table_row_operator, null)

        val colOperator = row.findViewById<TextView>(R.id.colOperator)
        val colLine = row.findViewById<TextView>(R.id.colLine)
        val colColumn = row.findViewById<TextView>(R.id.colColumn)
        val colOccurrence = row.findViewById<TextView>(R.id.colOccurrence)

        colOperator.text = "Operador"
        colOperator.setTypeface(null, Typeface.BOLD)
        colLine.text = "Linea"
        colLine.setTypeface(null, Typeface.BOLD)
        colColumn.text = "Columna"
        colColumn.setTypeface(null, Typeface.BOLD)
        colOccurrence.text = "Ocurrencia"
        colOccurrence.setTypeface(null, Typeface.BOLD)

        this.tbTemplate.addView(row)

        for (report in operatorReport!!) {
            val row = layoutInflater.inflate(R.layout.table_row_operator, null)

            val colOperator = row.findViewById<TextView>(R.id.colOperator)
            val colLine = row.findViewById<TextView>(R.id.colLine)
            val colColumn = row.findViewById<TextView>(R.id.colColumn)
            val colOccurrence = row.findViewById<TextView>(R.id.colOccurrence)

            colOperator.text = report.operator
            colLine.text = report.line.toString()
            colColumn.text = report.column.toString()
            colOccurrence.text = report.occurrence

            this.tbTemplate.addView(row)
        }
    }

    fun generateStructureReport() {
        val structureReport =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(
                    STRUCTURE_REPORT,
                    ArrayList::class.java
                ) as? ArrayList<StructureReport>
            } else {
                @Suppress("UNCHECKED_CAST")
                intent.getSerializableExtra(STRUCTURE_REPORT) as? ArrayList<StructureReport>
            }

        val row = layoutInflater.inflate(R.layout.table_row_structure, null)

        val colObject = row.findViewById<TextView>(R.id.colObject)
        val colLine = row.findViewById<TextView>(R.id.colLine)
        val colCondition = row.findViewById<TextView>(R.id.colCondition)

        colObject.text = "Objeto"
        colObject.setTypeface(null, Typeface.BOLD)
        colLine.text = "Linea"
        colLine.setTypeface(null, Typeface.BOLD)
        colCondition.text = "Condicion"
        colCondition.setTypeface(null, Typeface.BOLD)

        this.tbTemplate.addView(row)

        for (report in structureReport!!) {
            val row = layoutInflater.inflate(R.layout.table_row_structure, null)

            val colObject = row.findViewById<TextView>(R.id.colObject)
            val colLine = row.findViewById<TextView>(R.id.colLine)
            val colCondition = row.findViewById<TextView>(R.id.colCondition)

            colObject.text = report.structure
            colLine.text = report.line.toString()
            colCondition.text = report.condition

            this.tbTemplate.addView(row)
        }
    }

    fun generateErrorReport() {
        val errorReport =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(
                    ERROR_REPORT,
                    ArrayList::class.java
                ) as? ArrayList<ErrorReport>
            } else {
                @Suppress("UNCHECKED_CAST")
                intent.getSerializableExtra(ERROR_REPORT) as? ArrayList<ErrorReport>
            }

        val row = layoutInflater.inflate(R.layout.table_row_error, null)

        val colLexeme = row.findViewById<TextView>(R.id.colLexeme)
        val colLine = row.findViewById<TextView>(R.id.colLine)
        val colColumn = row.findViewById<TextView>(R.id.colColumn)
        val colType = row.findViewById<TextView>(R.id.colType)
        val colDescription = row.findViewById<TextView>(R.id.colDescription)

        colLexeme.text = "Lexema"
        colLexeme.setTypeface(null, Typeface.BOLD)
        colLine.text = "Linea"
        colLine.setTypeface(null, Typeface.BOLD)
        colColumn.text = "Columna"
        colColumn.setTypeface(null, Typeface.BOLD)
        colType.text = "Tipo"
        colType.setTypeface(null, Typeface.BOLD)
        colDescription.text = "Descripcion"
        colDescription.setTypeface(null, Typeface.BOLD)

        this.tbTemplate.addView(row)

        for (report in errorReport!!) {
            val row = layoutInflater.inflate(R.layout.table_row_error, null)

            val colLexeme = row.findViewById<TextView>(R.id.colLexeme)
            val colLine = row.findViewById<TextView>(R.id.colLine)
            val colColumn = row.findViewById<TextView>(R.id.colColumn)
            val colType = row.findViewById<TextView>(R.id.colType)
            val colDescription = row.findViewById<TextView>(R.id.colDescription)

            colLexeme.text = report.lexeme
            colLine.text = report.line.toString()
            colColumn.text = report.column.toString()
            colType.text = report.type
            colDescription.text = report.description

            this.tbTemplate.addView(row)
        }
    }

}