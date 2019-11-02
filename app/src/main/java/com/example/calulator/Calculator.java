package com.example.calulator;

import android.view.View;
import android.widget.Button;

public class Calculator {
    String numberString;
    String detailsString;
    double number = 0;
    Button btnClear = null;
    int btnDotClickedCount = 0;
    String operator;
    double prevNum;
    boolean btnModClicked = false;
    double memory;

    Calculator() {
        numberString = "0";
        detailsString = "";
        memory = 0;
    }

    void numberClicked(int num) {

        //on clicking the dot button this happens
        if (btnDotClickedCount >= 1) {
            if (Double.parseDouble(numberString) == 0) {
                numberString = String.valueOf(num);
            } else {
                numberString = numberString + num;
            }
            number = Double.valueOf(numberString);
        } else {
            number = number * 10 + num;
            numberString = String.valueOf(number);
        }

    }

    public String getDetailsString() {
        return detailsString;
    }

    public String getNumberString() {
        return numberString;
    }

    public void setDetailsString(String detailsString) {
        if (this.detailsString.equals("0") || this.detailsString.equals("0.0")) {
            this.detailsString = detailsString;
        } else {
            this.detailsString += detailsString;
        }
    }

    public void clearText(Button btnClear) {

        if (numberString.length() > 1 && numberString != "0.0" && numberString.length() < 10) {
            numberString = numberString.substring(0, numberString.length() - 1);
            if (numberString.endsWith(".")) {
                numberString = numberString.substring(0, numberString.length() - 2);
            }
        } else {
            numberString = "0.0";
        }
        if (numberString.length() == 0) {
            numberString = "0.0";
        }
        number = Double.valueOf(numberString);
        btnClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                detailsString = "0.0";
                number = 0;
                numberString = "0.0";
                btnDotClickedCount = 0;
                return false;
            }
        });


    }

    public void buttonDot(String str) {
        btnDotClickedCount++;

        //for first dot, print dot
        if (btnDotClickedCount == 1) {
            str = str + ".";
            this.setDetailsString(".");
            numberString = str;
        }
    }

    public void performOperation(String operator, double num1, double num2) {
        switch (operator) {
            case "+":
                num1 = num1 + num2;
                break;
            case "-":
                num1 = num1 - num2;
                break;
            case "\u00d7":
                num1 = num1 * num2;
                break;
            case "\u00f7":
                num1 = num1 / num2;
                break;
            case "+/-":
                num1 = -num1;
                break;
            default:
                num1 = num2 / 100;
                break;
        }
        number = num1;
        numberString = String.valueOf(num1);
        memory = number;
    }

    public String removeUnnecessaryDot(String numberString) {
        //validation to remove unnecessary zeros after decimal
        double afterDot = Double.valueOf(numberString.substring(numberString.indexOf("."), numberString.length()));
        if (afterDot > 0) {
            return numberString;
        } else {
            return String.valueOf(numberString.substring(0, numberString.indexOf(".")));
        }
    }

    public void memoryClear() {
        number = 0;
        numberString = "0.0";
        memory = 0;
    }

    public void memoryPlus(String number) {
        memory += Double.valueOf(number);

    }

    public void memoryMinus(String number) {
        memory -= Double.valueOf(number);
    }

    public double btnMod(double num1, double currentNumber, String operator) {
        if (num1 == 0) {
            return currentNumber;
        } else if (operator.equals("\u00d7") || operator.equals(("\u00f7"))) {
            return (currentNumber / 100);
        } else {
            return ((currentNumber / 100) * num1);
        }
    }
}
