package com.example.practica1_compi1_ps26

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
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

    private lateinit var webView: WebView

    private var isPageLoaded = false
    private var pendingDot: String? = null


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
        this.setDOT()
    }

    fun initComponents() {
        this.btnExecute = findViewById(R.id.btnExecute)
        this.btnOperatorsReport = findViewById(R.id.btnOperatorsReport)
        this.btnStructuresReport = findViewById(R.id.btnStructuresReport)
        this.btnErrorReport = findViewById(R.id.btnErrorReport)

        this.webView = findViewById(R.id.webViewGraph)

        this.configureWebView()

        this.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                isPageLoaded = true
                pendingDot?.let { showGraph(it) }
            }
        }

        this.webView.loadUrl("file:///android_asset/graph.html")
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

    private fun configureWebView() {
        this.webView.settings.javaScriptEnabled = true
        this.webView.settings.domStorageEnabled = true
        this.webView.settings.allowFileAccess = true
        this.webView.settings.allowContentAccess = true
        this.webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        this.webView.settings.loadsImagesAutomatically = true
        this.webView.settings.setSupportZoom(false)
        this.webView.settings.useWideViewPort = true
        this.webView.settings.loadWithOverviewMode = true
        webView.settings.allowUniversalAccessFromFileURLs = true
        webView.settings.allowFileAccessFromFileURLs = true

        WebView.setWebContentsDebuggingEnabled(true)
    }

    private fun showGraph(dot: String) {
        val safeDot = dot.replace("\n", " ")
            .replace("\"", "\\\"")
            .replace("'", "\\'")
        val js = "renderGraph('$safeDot')"
        this.webView.evaluateJavascript(js, null)
    }

    fun setDOT() {
        val dot = "digraph DiagramaFlujoPseudocodigo1 {\n" +
                "    node [style=filled]\n" +
                "\n" +
                "    Inicio0 [shape=ellipse, label=\"Inicio0\", color=\"#4caf50\", fontcolor=\"#fff\", fontname=\"Times-New-Roman\", fontsize=18.0];\n" +
                "    \n" +
                "    Bloque1 [shape=parallelogram, label=\"Bloque1\", color=\"#ffc107\", fontcolor=\"#000\", fontname=\"Times-New-Roman\", fontsize=14.0];\n" +
                "    Inicio0 -> Bloque1;\n" +
                "    \n" +
                "    Si2 [shape=diamond, label=\"Si2\", color=\"#f80\", fontcolor=\"#fff\", fontname=\"Times-New-Roman\", fontsize=14.0];\n" +
                "    Bloque1 -> Si2;\n" +
                "    \n" +
                "    Bloque3 [shape=parallelogram, label=\"Bloque3\", color=\"#ffc107\", fontcolor=\"#000\", fontname=\"Times-New-Roman\", fontsize=14.0];\n" +
                "    Si2 -> Bloque3 [label=\"Si\"];\n" +
                "    \n" +
                "    Mientras4 [shape=diamond, label=\"Mientras4\", color=\"#f80\", fontcolor=\"#fff\", fontname=\"Times-New-Roman\", fontsize=14.0];\n" +
                "    Bloque3 -> Mientras4;\n" +
                "    Si2 -> Mientras4 [label=\"No\"];\n" +
                "    \n" +
                "    Bloque5 [shape=parallelogram, label=\"Bloque5\", color=\"#ffc107\", fontcolor=\"#000\", fontname=\"Times-New-Roman\", fontsize=14.0];\n" +
                "    Mientras4 -> Bloque5 [label=\"Mientras Si\"];\n" +
                "    \n" +
                "    Bloque6 [shape=parallelogram, label=\"Bloque6\", color=\"#ffc107\", fontcolor=\"#000\", fontname=\"Times-New-Roman\", fontsize=14.0];\n" +
                "    Mientras4 -> Bloque6 [label=\"No\"]\n" +
                "    Bloque5 -> Mientras4;\n" +
                "    \n" +
                "    Fin7 [shape=ellipse, label=\"Fin7\", color=\"#4caf50\", fontcolor=\"#fff\", fontname=\"Times-New-Roman\", fontsize=18.0];\n" +
                "    Bloque6 -> Fin7\n" +
                "}"
        if (this.isPageLoaded) {
            this.showGraph(dot)
        } else {
            this.pendingDot = dot
        }
    }

}