package com.example.practica1_compi1_ps26

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica1_compi1_ps26.domain.entities.ErrorReport
import com.example.practica1_compi1_ps26.domain.entities.OperatorReport
import com.example.practica1_compi1_ps26.domain.entities.StructureReport
import com.example.practica1_compi1_ps26.ui.TableActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val REPORT_TYPE = "report_type"
        const val OPERATOR_REPORT = "operator_report"
        const val STRUCTURE_REPORT = "structure_report"
        const val ERROR_REPORT = "error_report"
    }

    private lateinit var btnExecute: Button
    private lateinit var btnOperatorsReport: Button
    private lateinit var btnStructuresReport: Button
    private lateinit var btnErrorReport: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.initComponents()
        this.setListeners()
    }

    fun initComponents() {
        this.btnExecute = findViewById(R.id.btnExecute)
        this.btnOperatorsReport = findViewById(R.id.btnOperatorsReport)
        this.btnStructuresReport = findViewById(R.id.btnStructuresReport)
        this.btnErrorReport = findViewById(R.id.btnErrorReport)
    }

    fun setListeners() {
        this.btnExecute.setOnClickListener {
            // Implementacion con JFlex y CUP
        }
        this.btnOperatorsReport.setOnClickListener {
            this.navigateToOperatorsReport()
        }
        this.btnStructuresReport.setOnClickListener {
            this.navigateToStructuresReport()
        }
        this.btnErrorReport.setOnClickListener {
            this.navigateToErrorsReport()
        }
    }

    fun navigateToOperatorsReport() {
        val intent = Intent(this, TableActivity::class.java)
        intent.putExtra(REPORT_TYPE, 1)
        val operatorReport = arrayListOf(
            OperatorReport("Suma", 1, 15, "12 + 2"),
            OperatorReport("Resta", 1, 28, ") - 25")
        )
        intent.putExtra(OPERATOR_REPORT, operatorReport)
        startActivity(intent)
    }

    fun navigateToStructuresReport() {
        val intent = Intent(this, TableActivity::class.java)
        intent.putExtra(REPORT_TYPE, 2)
        val structureReport = arrayListOf(
            StructureReport("Si", 5, "a > b"),
            StructureReport("Mientras", 24, "b != 5")
        )
        intent.putExtra(STRUCTURE_REPORT, structureReport)
        startActivity(intent)
    }

    fun navigateToErrorsReport() {
        val intent = Intent(this, TableActivity::class.java)
        intent.putExtra(REPORT_TYPE, 3)
        val errorReport = arrayListOf(
            ErrorReport("$", 2, 13, "Lexico", "Simbolo no existente en el lenguaje"),
            ErrorReport("*", 3, 1, "Sintactico", "Se esperaba 'numero'")
        )
        intent.putExtra(ERROR_REPORT, errorReport)
        startActivity(intent)
    }

}