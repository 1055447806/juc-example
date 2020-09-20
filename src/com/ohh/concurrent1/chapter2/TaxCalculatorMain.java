package com.ohh.concurrent1.chapter2;

public class TaxCalculatorMain {

    public static void main(String[] args) {
        TaxCalculator calculator = new TaxCalculator(10000d, 2000d, (s, b) -> s * 0.1 + b * 0.15);
        System.out.println(calculator.calculate());
    }
}
