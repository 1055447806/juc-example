package com.ohh.concurrent1.chapter2;

/**
 * 税务计算
 */
public class TaxCalculator {

    private final double salary;      //工资

    private final double bonus;       //奖金

    private final CalculatorStrategy calculatorStrategy;

    public TaxCalculator(double salary, double bonus, CalculatorStrategy calculatorStrategy) {
        this.salary = salary;
        this.bonus = bonus;
        this.calculatorStrategy = calculatorStrategy;
    }

    protected double calcTax() {
        return calculatorStrategy.calculate(salary, bonus);
    }

    public double calculate() {
        return calcTax();
    }
}
