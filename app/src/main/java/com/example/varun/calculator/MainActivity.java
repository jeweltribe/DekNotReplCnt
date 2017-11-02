package com.example.varun.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // input buttons
    private Button numOne;
    private Button numTwo;
    private Button numThree;
    private Button numFour;
    private Button numFive;
    private Button numSix;
    private Button numSeven;
    private Button numEight;
    private Button numNine;
    private Button numZero;
    private Button dot;

    // operators
    private Button addition;
    private Button subtraction;
    private Button division;
    private Button multiplication;

    // actions
    private Button delete;
    private Button enter;
    private Button drop;

    // stack textview
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    Deque stack;
    ArrayList numberStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numOne = (Button) findViewById(R.id.one);
        numTwo = (Button) findViewById(R.id.two);
        numThree = (Button) findViewById(R.id.three);
        numFour = (Button) findViewById(R.id.four);
        numFive = (Button) findViewById(R.id.five);
        numSix = (Button) findViewById(R.id.six);
        numSeven = (Button) findViewById(R.id.seven);
        numEight = (Button) findViewById(R.id.eight);
        numNine = (Button) findViewById(R.id.nine);
        numZero = (Button) findViewById(R.id.zero);
        dot = (Button) findViewById(R.id.dot);

        addition = (Button) findViewById(R.id.plus);
        subtraction = (Button) findViewById(R.id.minus);
        division = (Button) findViewById(R.id.divide);
        multiplication = (Button) findViewById(R.id.times);

        delete = (Button) findViewById(R.id.delete);
        enter = (Button) findViewById(R.id.enter);
        drop = (Button) findViewById(R.id.pop);

        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);
        textView4 = (TextView) findViewById(R.id.text4);

        stack = new LinkedList<>();
        numberStack = new ArrayList();

        numOne.setOnClickListener(this);
        numTwo.setOnClickListener(this);
        numThree.setOnClickListener(this);
        numFour.setOnClickListener(this);
        numFive.setOnClickListener(this);
        numSix.setOnClickListener(this);
        numSeven.setOnClickListener(this);
        numEight.setOnClickListener(this);
        numNine.setOnClickListener(this);
        numZero.setOnClickListener(this);
        dot.setOnClickListener(this);
        addition.setOnClickListener(this);
        subtraction.setOnClickListener(this);
        division.setOnClickListener(this);
        multiplication.setOnClickListener(this);
        delete.setOnClickListener(this);
        enter.setOnClickListener(this);
        drop.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.one:
                pressNumber("1");
                break;
            case R.id.two:
                pressNumber("2");
                break;
            case R.id.three:
                pressNumber("3");
                break;
            case R.id.four:
                pressNumber("4");
                break;
            case R.id.five:
                pressNumber("5");
                break;
            case R.id.six:
                pressNumber("6");
                break;
            case R.id.seven:
                pressNumber("7");
                break;
            case R.id.eight:
                pressNumber("8");
                break;
            case R.id.nine:
               pressNumber("9");
                break;
            case R.id.zero:
                pressNumber("0");
                break;
            case R.id.dot:
                addDecimal();
                break;
            case R.id.enter:
                pressEnter();
                break;
            case R.id.delete:
                pressDelete();
                break;
            case R.id.pop:
                pressDrop();
                break;
            case R.id.plus:
                performOperation("+");
                break;
            case R.id.minus:
                performOperation("-");
                break;
            case R.id.divide:
                performOperation("/");
                break;
            case R.id.times:
                performOperation("*");
                break;
        }
    }

    public void pressNumber(String number) {
        // shift up one if top of stack is equal to the current value, otherwise just append
        // there is still a bug: if you enter the same number again then it gets pushed to the stack automatically
        // Example: input 123 and press enter, then input 1234. This will create a duplicate and have the 4 as the current value
        if (!stack.isEmpty() && textView1.getText().toString().equals(stack.peek().toString())) {
            shiftValuesUp();
            textView1.setText(number);
        } else {
            textView1.append(number);
        }
    }


    public void pressEnter() {
        // bug: occurs for a current value that is at the top of the stack. Press delete on current value and then press enter, it throws off pressDrop().
        if (textView1.length() >= 1) {
            CharSequence textForOne = textView1.getText();
            if (!stack.isEmpty() && textView1.getText().toString().equals(stack.peek().toString())) {
                shiftValuesUp();
            } else {
                stack.push(textForOne);
                numberStack.add(0, textForOne);
                shiftValuesUp();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Nothing was entered", Toast.LENGTH_SHORT).show();
        }
    }

    // Note: if the current value equals the top of the stack and the user presses the delete button,
    // then the top of the stack is popped
    public void pressDelete() {
        if (textView1.length() >= 1) {
            CharSequence text1 = textView1.getText();
            int length = text1.length();
            text1 = text1.subSequence(0, length - 1);
            textView1.setText(text1);
            // stack operations
            if ((textView1.length() == 0 || textView1.length() >= 1) && !stack.isEmpty()) {
                    numberStack.remove(stack.peek());
                    stack.pop();
            }
        }
    }

    public void pressDrop() {
        // even if the user doesn't "enter" the current value from the first TextView onto the stack, it still gets treated as if it were pushed onto the stack
        // based on the behavior of the RPN calculator
        // if the top of the stack doesn't equal the current value then don't pop anything from the stack, just shift the values down one
        if (!stack.isEmpty()) {
            if (!textView1.getText().toString().equals(stack.peek().toString())) {
                Log.d("Drop not equal", stack.peek().toString());
                shiftValuesDown();
            } else {
                numberStack.remove(stack.peek());
                stack.pop();
               shiftValuesDown();
            }
        } else {
            textView1.setText("");
            Toast.makeText(getApplicationContext(), "Stack is Empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void shiftValuesUp() {
        CharSequence textForOne = textView1.getText();
        CharSequence textForTwo = textView2.getText();
        CharSequence textForThree = textView3.getText();
        textView1.setText("");
        textView2.setText(textForOne);
        textView3.setText(textForTwo);
        textView4.setText(textForThree);
    }

    public void shiftValuesDown() {
        textView1.setText(textView2.getText());
        textView2.setText(textView3.getText());
        textView3.setText(textView4.getText());
        if (numberStack.size() >= 4) {
            CharSequence temp = (CharSequence) numberStack.get(3);
            textView4.setText(temp);
        } else {
            textView4.setText("");
        }
    }

    public void addDecimal() {
        String s = (String) textView1.getText();
        if (s.indexOf(".") == -1) {
            textView1.append(".");
        }
    }

    public void performOperation(String operator) {
        double number1 = 0, number2 = 0, result = 0;
        if (stack.size() >= 1 && !textView1.getText().toString().equals("")) {
            // pop two times
            if (textView1.getText().toString().equals(stack.peek())) {
                numberStack.remove(stack.peek());
                number2 = Double.parseDouble(stack.pop().toString());
                numberStack.remove(stack.peek());
                number1 = Double.parseDouble(stack.pop().toString());
            } else {
                // pop one time
                number2 = Double.parseDouble(textView1.getText().toString());
                numberStack.remove(stack.peek());
                number1 = Double.parseDouble(stack.pop().toString());
            }
            switch (operator) {
                case "+":
                    result = number1 + number2;
                    break;
                case "-":
                    result = number1 - number2;
                    break;
                case "/":
                    result = number1 / number2;
                    break;
                case "*":
                    result = number1 * number2;
                    break;
            }
            CharSequence temp = Double.toString(result);
            stack.push(temp);
            numberStack.add(0, temp);
            textView2.setText(temp);
            shiftValuesDown();
        } else {
            Toast.makeText(getApplicationContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
        }

    }


}
