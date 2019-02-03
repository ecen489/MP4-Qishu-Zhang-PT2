package com.example.hw4calculator;

import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView dispScreen;
    private String dispString = "";
    private String currentOperation = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dispScreen = (TextView)findViewById(R.id.showResult);
        dispScreen.setText(dispString);
    }

    private void updateScreen(){
        dispScreen.setText(dispString);
    }

    protected void onNumberClick(View view){
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) view;
        dispString += b.getText();
        updateScreen();
    }

    private boolean isOperator(char op){
        switch(op){
            case '+': return true;
            case '-': return true;
            case '*': return true;
            default: return false;
        }
    }

    protected void onOperatorClick(View view){

        if(dispString == "") return;

        Button b = (Button) view;

        if(result != ""){
            String display = result;
            clear();
            dispString = display;
        }

        if(currentOperation != ""){
            Log.d("Calculator","" + dispString.charAt(dispString.length()-1));
            if(isOperator(dispString.charAt(dispString.length()-1))){
                dispString = dispString.replace(dispString.charAt(dispString.length()-1),b.getText().charAt(0));
                updateScreen();
                return;
            } else {
                getResult();
                dispString = result;
                result = "";
            }
            currentOperation = b.getText().toString();
        }

        dispString += b.getText();
        currentOperation = b.getText().toString();
        updateScreen();
    }

    protected void clear(){
        dispString = "";
        currentOperation = "";
        result = "";
    }

    protected void onClearClick(View view){
        clear();
        updateScreen();
    }

    private double operate(String a, String b, String op){
        switch(op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "*": return Double.valueOf(a) * Double.valueOf(b);
            default: return -1;
        }
    }

    private boolean getResult(){
        if(currentOperation == "") return false;
        String[] operation = dispString.split(Pattern.quote(currentOperation));
        if(operation.length<2) return false;
        result = String.valueOf(operate(operation[0],operation[1],currentOperation));
        return true;
    }

    protected void onClickEquals(View view){
        if(dispString == "") return;
        if(!getResult()) return;
        dispScreen.setText(dispString + "\n" +String.valueOf(result));
    }

}
