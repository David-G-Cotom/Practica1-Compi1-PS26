plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.practica1_compi1_ps26"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.practica1_compi1_ps26"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(files("src/main/java/com/example/practica1_compi1_ps26/libs/java-cup-11b-runtime.jar"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// TASKS TO GENERATE LEXERS AND PARSERS
tasks.register<JavaExec>("generateLexer") {

    group = "code generation"

    val baseDir = project.projectDir

    classpath =
        project.files("$baseDir/src/main/java/com/example/practica1_compi1_ps26/libs/jflex-full-1.9.1.jar")
    mainClass.set("jflex.Main")

    args(
        "$baseDir/src/main/java/com/example/practica1_compi1_ps26/domain/analyzers/Lexer.flex"
    )
}

tasks.register<JavaExec>("generateParser") {

    group = "code generation"

    val baseDir = project.projectDir

    classpath =
        project.files("$baseDir/src/main/java/com/example/practica1_compi1_ps26/libs/java-cup-11b.jar")
    mainClass.set("java_cup.Main")

    args(
        "-destdir", "$baseDir/src/main/java/com/example/practica1_compi1_ps26/domain/analyzers",
        "-parser", "Parser",
        "-symbols", "sym",
        "$baseDir/src/main/java/com/example/practica1_compi1_ps26/domain/analyzers/Parser.cup"
    )
}

tasks.register("generateAnalyzers") {
    dependsOn("generateLexer", "generateParser")
}

tasks.named("preBuild") {
    dependsOn("generateAnalyzers")
}