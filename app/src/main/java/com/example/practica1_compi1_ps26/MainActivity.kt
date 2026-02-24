package com.example.practica1_compi1_ps26

import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica1_compi1_ps26.domain.analyzers.Analyzer
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

    private lateinit var etInputCode: AppCompatEditText

    private lateinit var webView: WebView
    private var isPageLoaded = false
    private var pendingDot: String? = null

    var analyzer: Analyzer = Analyzer()
    var operatorReport: ArrayList<OperatorReport> = ArrayList()
    var structureReport: ArrayList<StructureReport> = ArrayList()
    var errorReport: ArrayList<ErrorReport> = ArrayList()


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
        this.btnOperatorsReport.isEnabled = false
        this.btnStructuresReport = findViewById(R.id.btnStructuresReport)
        this.btnStructuresReport.isEnabled = false
        this.btnErrorReport = findViewById(R.id.btnErrorReport)
        this.btnErrorReport.isEnabled = false

        this.etInputCode = findViewById(R.id.etInputCode)

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
            this.analyzer = Analyzer()
            val result: String = this.analyzer.analyze(this.etInputCode.text.toString())
            if (this.analyzer.errors.isEmpty()) {
                this.setDOT(this.analyzer.interpreter.generateDOT())
                this.operatorReport = this.analyzer.operators
                this.structureReport = this.analyzer.structures
                this.btnOperatorsReport.isEnabled = true
                this.btnStructuresReport.isEnabled = true
                this.btnErrorReport.isEnabled = false
            } else {
                Toast.makeText(
                    this,
                    "Se encontraron errores en el codigo. Revise el Reporte de Errores",
                    Toast.LENGTH_SHORT)
                    .show()
                this.setDOT("digraph Error {\n" +
                        "  error\n" +
                        "}")
                this.errorReport = this.analyzer.errors
                this.btnOperatorsReport.isEnabled = false
                this.btnStructuresReport.isEnabled = false
                this.btnErrorReport.isEnabled = true
            }
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
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
        intent.putExtra(OPERATOR_REPORT, this.operatorReport)
        startActivity(intent)
    }

    fun navigateToStructuresReport() {
        val intent = Intent(this, TableActivity::class.java)
        intent.putExtra(REPORT_TYPE, 2)
        intent.putExtra(STRUCTURE_REPORT, this.structureReport)
        startActivity(intent)
    }

    fun navigateToErrorsReport() {
        val intent = Intent(this, TableActivity::class.java)
        intent.putExtra(REPORT_TYPE, 3)
        intent.putExtra(ERROR_REPORT, this.errorReport)
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
        println(dot)
        val safeDot = dot.replace("\n", " ")
            .replace("\"", "\\\"")
            .replace("'", "\\'")
        val js = "renderGraph('$safeDot')"
        this.webView.evaluateJavascript(js, null)
    }

    fun setDOT(dot: String) {
        if (this.isPageLoaded) {
            this.showGraph(dot)
        } else {
            this.pendingDot = dot
        }
    }

}