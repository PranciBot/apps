package com.prancibot;

public class CronExpression {
    public final String EXPRESSION_SEPARATOR = " ";
    private final String expression;
    private final String[] expressionSegments;

    public CronExpression(String expression) {
        this.expression = expression;
        expressionSegments = expression.split(EXPRESSION_SEPARATOR);
    }

    @Override
    public String toString() {
        return expression;
    }
}
