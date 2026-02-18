// IMPORTS
package com.example.practica1_compi1_ps26.data.analyzers;

import java_cup.runtime.*;

import java.util.ArrayList;

%% // ---------------------------------------- SECTION SEPARATOR ----------------------------------------

// USER CODE
%{
    // Utils
    private StringBuilder string;

    // Error Handling
    private ArrayList<String> errorList;
    private ArrayList<String> symbols;

    private void error(String message) {
        String errorMessage = "Error en la linea " + yyline + ", columna " + yycolumn + ": " + message;
        this.errorList.add(errorMessage);
        System.out.println(errorMessage);
    }

    public ArrayList<String> getErrorList(){
        return this.errorList;
    }

    public ArrayList<String> getSymbols(){
        return this.symbols;
    }

    //Parser Code
    private Symbol symbol(int type) {
        this.symbols.add(yytext());
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        this.symbols.add(value.toString());
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

// OPTIONS AND DECLARATIONS
%public
%class Lexer
%line
%column
%char
%unicode
%cup
%init{
    this.string = new StringBuilder();
    this.errorList = new ArrayList<>();
    this.symbols = new ArrayList<>();
    yyline = 1;
    yycolumn = 1;
%init}

// REGULAR EXPRESSIONS
LineTerminator = \r|\n|\n\r|\r\n
WhiteSpace = [" "\t\f]
InputCharacter = [^{LineTerminator}]
Dot = \.
WholeNumber = 0|[1-9][0-9]*
DecimalNumber = {WholeNumber}{Dot}[0-9]+
Letter = [a-zA-Z]
ID = _?{Letter}({Letter}|_|{WholeNumber})*

// STATES
%state TEXT, COMMENT//, HEXADECIMAL

%% // ---------------------------------------- SECTION SEPARATOR ----------------------------------------

// LEXICAL RULES
// Default State
<YYINITIAL> {
    "%%%%"                    { return symbol(sym.SEPARADOR); }
    "INICIO"                  { return symbol(sym.INICIO); }
    "FIN"                     { return symbol(sym.FIN); }

    "SI"                      { return symbol(sym.SI); }
    "ENTONCES"                { return symbol(sym.ENTONCES); }
    "FINSI"                   { return symbol(sym.FINSI); }

    "MIENTRAS"                { return symbol(sym.MIENTRAS); }
    "HACER"                   { return symbol(sym.HACER); }
    "FINMIENTRAS"             { return symbol(sym.FINMIENTRAS); }

    "VAR"                     { return symbol(sym.VAR); }
    "MOSTRAR"                 { return symbol(sym.MOSTRAR); }
    "LEER"                    { return symbol(sym.LEER); }

    "ELIPSE"                  { return symbol(sym.ELIPSE); }
    "CIRCULO"                 { return symbol(sym.CIRCULO); }
    "PARALELOGRAMO"           { return symbol(sym.PARALELOGRAMO); }
    "RECTANGULO"              { return symbol(sym.RECTANGULO); }
    "ROMBO"                   { return symbol(sym.ROMBO); }
    "RECTANGULO_REDONDEADO"   { return symbol(sym.RECTANGULO_REDONDEADO); }

    "ARIAL"                   { return symbol(sym.ARIAL); }
    "TIMES_NEW_ROMAN"          { return symbol(sym.TIMES_NEW_ROMAN); }
    "COMIC_SANS"              { return symbol(sym.COMIC_SANS); }
    "VERDANA"                 { return symbol(sym.VERDANA); }

    "==" { return symbol(sym.IGUALDAD); }
    "!=" { return symbol(sym.DIFERENTE); }
    ">"  { return symbol(sym.MAYOR); }
    "<"  { return symbol(sym.MENOR); }
    ">=" { return symbol(sym.MAYOR_IGUAL); }
    "<=" { return symbol(sym.MENOR_IGUAL); }

    "&&" { return symbol(sym.AND); }
    "||" { return symbol(sym.OR); }
    "!"  { return symbol(sym.NOT); }

    "+" { return symbol(sym.SUMA); }
    "-" { return symbol(sym.RESTA); }
    "*" { return symbol(sym.MULTIPLICACION); }
    "/" { return symbol(sym.DIVISION); }
    "(" { return symbol(sym.PARENTESIS_ABIERTO); }
    ")" { return symbol(sym.PARENTESIS_CERRADO); }

    "=" { return symbol(sym.IGUAL); }
    "," { return symbol(sym.COMA); }
    "|" { return symbol(sym.PIPE); }

    "%DEFAULT" { return symbol(sym.DEFAULT); }

    "%COLOR_TEXTO_SI" { return symbol(sym.COLOR_TEXTO_SI); }
    "%COLOR_SI"       { return symbol(sym.COLOR_SI); }
    "%FIGURA_SI"      { return symbol(sym.FIGURA_SI); }
    "%LETRA_SI"       { return symbol(sym.LETRA_SI); }
    "%LETRA_SIZE_SI"  { return symbol(sym.LETRA_SIZE_SI); }

    "%COLOR_TEXTO_MIENTRAS" { return symbol(sym.COLOR_TEXTO_MIENTRAS); }
    "%COLOR_MIENTRAS"       { return symbol(sym.COLOR_MIENTRAS); }
    "%FIGURA_MIENTRAS"      { return symbol(sym.FIGURA_MIENTRAS); }
    "%LETRA_MIENTRAS"       { return symbol(sym.LETRA_MIENTRAS); }
    "%LETRA_SIZE_MIENTRAS"  { return symbol(sym.LETRA_SIZE_MIENTRAS); }

    "%COLOR_TEXTO_BLOQUE" { return symbol(sym.COLOR_TEXTO_BLOQUE); }
    "%COLOR_BLOQUE"       { return symbol(sym.COLOR_BLOQUE); }
    "%FIGURA_BLOQUE"      { return symbol(sym.FIGURA_BLOQUE); }
    "%LETRA_BLOQUE"       { return symbol(sym.LETRA_BLOQUE); }
    "%LETRA_SIZE_BLOQUE"  { return symbol(sym.LETRA_SIZE_BLOQUE); }

    "#" { this.string.setLength(0); yybegin(COMMENT); }

    \" { this.string.setLength(0); yybegin(TEXT); }

    //"H" { this.string.setLength(0); yybegin(HEXADECIMAL); }
    "H"[a-fA-F0-9]{6} {
        this.string.setLength(0);
        this.string.append(yytext());
        this.string.deleteCharAt(0);
        return symbol(sym.HEXADECIMAL, this.string.toString().trim());
    }
    "H"[a-fA-F0-9]{3} {
        this.string.setLength(0);
        this.string.append(yytext());
        this.string.deleteCharAt(0);
        return symbol(sym.HEXADECIMAL, this.string.toString().trim());
    }

    {WholeNumber}   { return symbol(sym.NUMERO_ENTERO); }
    {DecimalNumber} { return symbol(sym.NUMERO_DECIMAL); }
    {ID}            { return symbol(sym.ID); }
}

// Comment state
<COMMENT> {
    {InputCharacter}+ { this.string.append(yytext()); }
    {LineTerminator} {
        yybegin(YYINITIAL);
        //return symbol(sym.COMENTARIO, this.string.toString().trim());
    }
    <<EOF>> {
        yybegin(YYINITIAL);
        //return symbol(sym.COMENTARIO, this.string.toString().trim());
    }
}

// Text state
<TEXT> {
    \" {
        yybeing(YYINITIAL);
        return symbol(sym.TEXTO, this.string.toString().trim());
    }
    {InputCharacter}+ { this.string.append(yytext()); }
    {LineTerminator} { this.string.append(yytext()); }
}

// Hexadecimal state
/*<HEXADECIMAL> {
    [a-fA-F0-9]{6} {
        this.string.append(yytext());
        yybegin(YYINITIAL);
        return symbol(sym.HEXADECIMAL, this.string.toString().trim());
    }
    [a-fA-F0-9]{3} {
        this.string.append(yytext());
        yybegin(YYINITIAL);
        return symbol(sym.HEXADECIMAL, this.string.toString().trim());
    }
}*/

/* Ignored whitespace */
{WhiteSpace} | {LineTerminator} { /* Ignore */ }

/* Error handling */
. { error("Símbolo inválido <" + yytext() + ">"); }
<<EOF>> { return symbol(sym.EOF); }