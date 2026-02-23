// IMPORTS
package com.example.practica1_compi1_ps26.domain.analyzers;

import java_cup.runtime.*;
import com.example.practica1_compi1_ps26.domain.analyzers.sym;

import java.util.ArrayList;

import com.example.practica1_compi1_ps26.domain.entities.ErrorReport;

%% // ---------------------------------------- SECTION SEPARATOR ----------------------------------------

// USER CODE
%{
    // Utils
    private StringBuilder string;

    // Error Handling
    private ArrayList<String> symbols;
    private ArrayList<ErrorReport> lexicalErrors;

    private void error(String token) {
        this.lexicalErrors.add(new ErrorReport(token, yyline, yycolumn, "Lexico", "Cadena no existente en el lenguaje"));
    }

    public ArrayList<ErrorReport> getLexicalErrors(){
        return this.lexicalErrors;
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
    this.symbols = new ArrayList<>();
    this.lexicalErrors = new ArrayList<>();
    yyline = 1;
    yycolumn = 1;
%init}

// REGULAR EXPRESSIONS
LineTerminator = \r|\n|\n\r|\r\n
WhiteSpace = [ \t\f]
InputCharacter = [^\r\n]
Dot = \.
WholeNumber = 0|[1-9][0-9]*
DecimalNumber = {WholeNumber}{Dot}[0-9]+
Letter = [a-zA-Z]
ID = _?{Letter}({Letter}|_|{WholeNumber})*

// STATES
%state TEXT, COMMENT

%% // ---------------------------------------- SECTION SEPARATOR ----------------------------------------

// LEXICAL RULES
// Default State
<YYINITIAL> {
    "%%%%"                    { return symbol(sym.SEPARADOR); }
    "INICIO"                  { return symbol(sym.INICIO, yytext()); }
    "FIN"                     { return symbol(sym.FIN, yytext()); }

    "SI"                      { return symbol(sym.SI); }
    "ENTONCES"                { return symbol(sym.ENTONCES); }
    "FINSI"                   { return symbol(sym.FINSI); }

    "MIENTRAS"                { return symbol(sym.MIENTRAS); }
    "HACER"                   { return symbol(sym.HACER); }
    "FINMIENTRAS"             { return symbol(sym.FINMIENTRAS); }

    "VAR"                     { return symbol(sym.VAR, yytext()); }
    "MOSTRAR"                 { return symbol(sym.MOSTRAR, yytext()); }
    "LEER"                    { return symbol(sym.LEER, yytext()); }

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

    "==" { return symbol(sym.IGUALDAD, yytext()); }
    "!=" { return symbol(sym.DIFERENTE, yytext()); }
    ">"  { return symbol(sym.MAYOR, yytext()); }
    "<"  { return symbol(sym.MENOR, yytext()); }
    ">=" { return symbol(sym.MAYOR_IGUAL, yytext()); }
    "<=" { return symbol(sym.MENOR_IGUAL, yytext()); }

    "&&" { return symbol(sym.AND, yytext()); }
    "||" { return symbol(sym.OR, yytext()); }
    "!"  { return symbol(sym.NOT, yytext()); }

    "+" { return symbol(sym.SUMA); }
    "-" { return symbol(sym.RESTA); }
    "*" { return symbol(sym.MULTIPLICACION); }
    "/" { return symbol(sym.DIVISION); }
    "(" { return symbol(sym.PARENTESIS_ABIERTO, yytext()); }
    ")" { return symbol(sym.PARENTESIS_CERRADO, yytext()); }

    "=" { return symbol(sym.IGUAL, yytext()); }
    "," { return symbol(sym.COMA, yytext()); }
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

    {WholeNumber}   { return symbol(sym.NUMERO_ENTERO, yytext()); }
    {DecimalNumber} { return symbol(sym.NUMERO_DECIMAL, yytext()); }
    {ID}            { return symbol(sym.ID, yytext()); }
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
        yybegin(YYINITIAL);
        return symbol(sym.TEXTO, this.string.toString().trim());
    }
    {InputCharacter}+ { this.string.append(yytext()); }
    {LineTerminator} { this.string.append("\n"); }
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
. { error(yytext()); }
<<EOF>> { return symbol(sym.EOF); }