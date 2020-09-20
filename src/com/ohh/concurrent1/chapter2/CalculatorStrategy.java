package com.ohh.concurrent1.chapter2;

@FunctionalInterface
public interface CalculatorStrategy {
    double calculate(double salary, double bonus);
}
