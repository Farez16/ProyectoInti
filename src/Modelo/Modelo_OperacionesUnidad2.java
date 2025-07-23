/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Modelo_OperacionesUnidad2.java
package Modelo;

import java.util.Random;

public class Modelo_OperacionesUnidad2 {
    private static final char[] OPERADORES = {'+', '-', '*', '/'};
    private static final Random random = new Random();

    public static int getNumeroAleatorio() {
        return random.nextInt(50) + 1;
    }

    public static char getOperadorAleatorio() {
        return OPERADORES[random.nextInt(OPERADORES.length)];
    }

    public static double calcularResultado(int a, int b, char op) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> (b != 0) ? (double) a / b : 0;
            default -> 0;
        };
    }
}
