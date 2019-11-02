package com.example.calulator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv1, tv2;
    Calculator calculator;
    Button btnClear = null;
    boolean btnDotPressed = false, btnOperatorPressed = false, btnModPressed = false, btnNumberPressed = false;
    //operators
    String operator;
    Button btnPlus, btnMinus, btnMultiply, btnDivide, btnPlusMinus, btnMod, btnEqual;
    double num1, num2;
    double result;
    //variables used to compute mod operation
    double currentNumber;
    double computedNumber;
    //add on buttons
    Button btnPower, btnMC, btnMPlus, btnMMinus, btnMR;
    boolean firstTime, memoryRead, memoryClear, memoryPlus, memoryMinus;
    boolean operatorClicked, equalClicked, singleNumber, modClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing values on creating app
        tv1 = findViewById(R.id.screenHistory);
        tv2 = findViewById(R.id.screenResult);
        btnClear = findViewById(R.id.btnC);
        calculator = new Calculator();
        operator = "";
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        btnMod = findViewById(R.id.btnModule);
        btnPlusMinus = findViewById(R.id.btnPlusMinus);
        btnEqual = findViewById(R.id.btnEqual);

        //add-on buttons
        btnPower = findViewById(R.id.btnPower);
        btnMPlus = findViewById(R.id.btnMPlus);
        btnMMinus = findViewById(R.id.btnMMinus);
        btnMC = findViewById(R.id.btnMC);
        btnMR = findViewById(R.id.btnMR);

        //to display x raise to power y
        ((TextView) findViewById(R.id.btnPower)).setText(Html.fromHtml("X<sup>Y</sup>"));

        firstTime = true;
        memoryRead = false;
        memoryClear = false;
        memoryPlus = false;
        memoryMinus = false;
        operatorClicked = true;
        equalClicked = true;
        singleNumber = true;
        modClicked = true;
    }

    //this method is used by all the operators
    public void btnOperator(View view) {

        num1 = Double.valueOf(tv2.getText().toString());
        btnOperatorPressed = true;
        resetToDefaultColor();
        operator = ((Button) view).getText().toString();

        if (((Button) view).getText().toString().equals("+/-")) {
            btnOperatorPressed = false;
            //changing the colors to add effect on clicking
            view.setBackgroundColor(Color.parseColor("#a7a6ab"));
            ((Button) view).setTextColor(Color.parseColor("#1F1F1F"));

            calculator.performOperation(operator, num1, 0);

        } else if (((Button) view).getText().toString().equals("MR")) {
//            Toast.makeText(getApplicationContext(), String.valueOf(calculator.memory), Toast.LENGTH_SHORT).show();
            memoryRead = true;
        } else if (((Button) view).getText().toString().equals("MC")) {
            memoryClear = true;
        } else if (((Button) view).getText().toString().equals("M+")) {
            memoryPlus = true;
        } else if (((Button) view).getText().toString().equals("M-")) {
            memoryMinus = true;
        } else {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            ((Button) view).setTextColor(Color.parseColor("#F89013"));
        }

        if (((Button) view).getText().toString().equals("+") || ((Button) view).getText().toString().equals("-") || ((Button) view).getText().toString().equals("\u00d7") || ((Button) view).getText().toString().equals("\u00f7")) {
            if (operatorClicked) {
                calculator.setDetailsString(operator);
                operatorClicked = false;
                singleNumber = false;
            }
        }

        updateDisplay();
    }

    //this method changes the color to default colors of operators
    private void resetToDefaultColor() {
        //revert to original color
        btnPlus.setTextColor(Color.parseColor("#ffffff"));
        btnPlus.setBackgroundColor(Color.parseColor("#F89013"));

        btnMinus.setTextColor(Color.parseColor("#ffffff"));
        btnMinus.setBackgroundColor(Color.parseColor("#F89013"));

        btnMultiply.setTextColor(Color.parseColor("#ffffff"));
        btnMultiply.setBackgroundColor(Color.parseColor("#F89013"));

        btnDivide.setTextColor(Color.parseColor("#ffffff"));
        btnDivide.setBackgroundColor(Color.parseColor("#F89013"));

        btnMod.setTextColor(Color.parseColor("#1F1F1F"));
        btnMod.setBackgroundColor(Color.parseColor("#b7b6ba"));

        btnPlusMinus.setTextColor(Color.parseColor("#1F1F1F"));
        btnPlusMinus.setBackgroundColor(Color.parseColor("#b7b6ba"));

        btnPower.setTextColor(Color.parseColor("#ffffff"));
        btnPower.setBackgroundColor(Color.parseColor("#F89013"));

        btnMPlus.setTextColor(Color.parseColor("#ffffff"));
        btnMPlus.setBackgroundColor(Color.parseColor("#F89013"));

        btnMMinus.setTextColor(Color.parseColor("#ffffff"));
        btnMMinus.setBackgroundColor(Color.parseColor("#F89013"));

        btnMC.setTextColor(Color.parseColor("#ffffff"));
        btnMC.setBackgroundColor(Color.parseColor("#F89013"));

        btnMR.setTextColor(Color.parseColor("#ffffff"));
        btnMR.setBackgroundColor(Color.parseColor("#F89013"));
    }

    //this method is used when any number is clicked
    public void btnClicked(View view) {

        btnNumberPressed = true;
        if (btnOperatorPressed || btnModPressed) {
            calculator.numberString = "0.0";
            calculator.number = 0;
            btnOperatorPressed = false;
            btnModPressed = false;
        }


        //dynamically changes text from C to AC which means all clear when limit is reached
        if (tv2.getText().length() > 6) {
            btnClear.setText("AC");
        }
//        calculator.numberClicked(((Button)view).getText().toString(), tv2.getText().toString());
        calculator.numberClicked(Integer.valueOf(((Button) view).getText().toString()));
        calculator.setDetailsString(((Button) view).getText().toString());


//        calculator.setDetailsString(calculator.getNumberString());
        updateDisplay();

        operatorClicked = true;
        equalClicked = true;

    }


    private void updateDisplay() {

        if (tv2.getText().toString().equals("0") || tv2.getText().toString().equals("0.0")) {
            tv1.setText("");
        } else {
            tv1.setText(calculator.getDetailsString());
        }


        if (!btnOperatorPressed) {
            resetToDefaultColor();
        }
//        tv1.setText(calculator.getDetailsString());
        if (Double.parseDouble(calculator.getNumberString()) == 0) {
            tv2.setText("0");
            btnDotPressed = false;
        } else {
            if (btnModPressed) {
                //validation to remove unnecessary zeros after decimal
                tv2.setText(calculator.removeUnnecessaryDot(calculator.getNumberString()));
            } else if (calculator.getNumberString().contains(".") && btnDotPressed == false) {
                tv2.setText(calculator.getNumberString().substring(0, calculator.getNumberString().length() - 2));
            } else {
                tv2.setText(calculator.getNumberString());
            }
        }
        if (memoryRead) {
            //display number on tv2
            tv2.setText(calculator.removeUnnecessaryDot(String.valueOf(calculator.memory)));
            tv1.setText("");
            calculator.detailsString = "0";
            memoryRead = false;
        }
        if (memoryClear) {
            calculator.memoryClear();
            tv2.setText("0");
            tv1.setText("");
            calculator.detailsString = "0";
            memoryClear = false;
        }
        if (memoryPlus) {
            calculator.memoryPlus(tv2.getText().toString());
            tv2.setText(calculator.removeUnnecessaryDot(String.valueOf(calculator.memory)));
            tv1.setText("");
            calculator.detailsString = "0";
            memoryPlus = false;
        }
        if (memoryMinus) {
            calculator.memoryMinus(tv2.getText().toString());
            tv2.setText(calculator.removeUnnecessaryDot(String.valueOf(calculator.memory)));
            tv1.setText("");
            calculator.detailsString = "0";
            memoryMinus = false;
        }
    }

    //this method is invoked when user clicks on clear button
    //also, it dynamically changes text from C to AC which means all clear when limit is reached
    //code for AC is written in btnClicked method
    public void btnClear(View view) {

        if (calculator.detailsString.length() > 1) {
            if (calculator.detailsString.substring(calculator.detailsString.length() - 2, calculator.detailsString.length() - 1) == "1" ||
                    calculator.detailsString.substring(calculator.detailsString.length() - 2, calculator.detailsString.length() - 1) == "2" ||
                    calculator.detailsString.substring(calculator.detailsString.length() - 2, calculator.detailsString.length() - 1) == "3" ||
                    calculator.detailsString.substring(calculator.detailsString.length() - 2, calculator.detailsString.length() - 1) == "4" ||
                    calculator.detailsString.substring(calculator.detailsString.length() - 2, calculator.detailsString.length() - 1) == "5" ||
                    calculator.detailsString.substring(calculator.detailsString.length() - 2, calculator.detailsString.length() - 1) == "6" ||
                    calculator.detailsString.substring(calculator.detailsString.length() - 2, calculator.detailsString.length() - 1) == "7" ||
                    calculator.detailsString.substring(calculator.detailsString.length() - 2, calculator.detailsString.length() - 1) == "8" ||
                    calculator.detailsString.substring(calculator.detailsString.length() - 2, calculator.detailsString.length() - 1) == "9" ||
                    calculator.detailsString.substring(calculator.detailsString.length() - 2, calculator.detailsString.length() - 1) == "0") {
                calculator.detailsString = calculator.removeUnnecessaryDot(calculator.detailsString.substring(0, calculator.detailsString.length() - 1));
            }
        } else if (calculator.detailsString.length() == 1) {
            if (calculator.detailsString == "1" ||
                    calculator.detailsString == "2" ||
                    calculator.detailsString == "3" ||
                    calculator.detailsString == "4" ||
                    calculator.detailsString == "5" ||
                    calculator.detailsString == "6" ||
                    calculator.detailsString == "7" ||
                    calculator.detailsString == "8" ||
                    calculator.detailsString == "9" ||
                    calculator.detailsString == "0") {
                calculator.detailsString = calculator.removeUnnecessaryDot(calculator.detailsString.substring(0, calculator.detailsString.length() - 1));
            }
        } else {
            calculator.detailsString = calculator.removeUnnecessaryDot("0");
        }


        if (firstTime) {
            Toast.makeText(getApplicationContext(), "Hold C to remove all digits", Toast.LENGTH_SHORT).show();
            firstTime = false;
        }

        if (calculator.getNumberString().equals("0.0") || calculator.number == 0) {
            resetToDefaultColor();
        }
        if (tv2.getText().length() > 6) {
            btnClear.setText("C");
        }
        calculator.clearText(btnClear);
        updateDisplay();
    }

    //this method is invoked when user clicks on dot button
    public void btnDot(View view) {
        calculator.buttonDot(tv2.getText().toString());
        btnDotPressed = true;
        updateDisplay();
    }

    //method for computing the values upon clicking the equals button
    public void btnEqual(View view) {

        if (btnModPressed) {
            num2 = computedNumber;
        } else {
            num2 = Double.valueOf(tv2.getText().toString());
        }

        resetToDefaultColor();
        calculator.performOperation(operator, num1, num2);
        num1 = 0;
        btnOperatorPressed = false;
        if (equalClicked) {
            calculator.setDetailsString("=");
            calculator.setDetailsString(calculator.removeUnnecessaryDot(String.valueOf(calculator.memory)));
            equalClicked = false;
        }
        updateDisplay();
        calculator.numberString = "0.0";
        calculator.number = 0;
        btnModPressed = false;
        modClicked = true;
        calculator.detailsString = "0.0";
    }

    //this % operator works similar to Google's operation
    public void btnMod(View view) {
        if (modClicked) {
            calculator.setDetailsString("%");
            modClicked = false;
            updateDisplay();
        }

        //changing the colors to add effect on clicking
        view.setBackgroundColor(Color.parseColor("#a7a6ab"));
        ((Button) view).setTextColor(Color.parseColor("#1F1F1F"));

        //logic similar to Google's operation
        currentNumber = Double.valueOf(tv2.getText().toString());
        if (singleNumber) {
            num1 = 0;
            singleNumber = false;
        }
        System.out.println("num1: " + num1 + "cN: " + currentNumber);
        //added addition functionality just like Google in which you can enter only one number and do mod operation on it
        computedNumber = calculator.btnMod(num1, currentNumber, operator);
        num2 = computedNumber;
        btnModPressed = true;
    }


    public void btnPower(View view) {
        //logic to be added

    }
}
