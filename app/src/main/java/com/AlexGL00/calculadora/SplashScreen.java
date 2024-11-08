package com.AlexGL00.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        TextView pantalla = (TextView) findViewById(R.id.ResultadoSplash);
        Toast msgError = Toast.makeText(getApplicationContext(),"Error" , Toast.LENGTH_LONG);

        Button button0 = (Button) findViewById(R.id.button21);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonSuma = (Button) findViewById(R.id.button20);
        Button buttonResta = (Button) findViewById(R.id.button22);
        Button buttonMultiplicar = (Button) findViewById(R.id.button23);
        Button buttonDividir = (Button) findViewById(R.id.button24);
        Button buttonResultado = (Button) findViewById(R.id.button25);
        Button buttonBorrar = (Button) findViewById(R.id.button26);


        ArrayList<Button> buttonsNum = new ArrayList<>();
        buttonsNum.add(button0);
        buttonsNum.add(button1);
        buttonsNum.add(button2);
        buttonsNum.add(button3);
        buttonsNum.add(button4);
        buttonsNum.add(button5);
        buttonsNum.add(button6);
        buttonsNum.add(button7);
        buttonsNum.add(button8);
        buttonsNum.add(button9);

        ArrayList<Button> buttonsOP = new ArrayList<>();
        buttonsOP.add(buttonSuma);
        buttonsOP.add(buttonResta);
        buttonsOP.add(buttonMultiplicar);
        buttonsOP.add(buttonDividir);


        for (Button button : buttonsOP) {
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (pantalla.getText().toString().equals("0")){
                        msgError.show();
                    } else {
                        pantalla.append(((Button) view).getText().toString());
                    }
                }
            });
        }
        for  (Button button : buttonsNum){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String buttonId = getResources().getResourceEntryName(view.getId());
                    String digito = buttonId.substring(buttonId.length()- 1);

                    if (pantalla.getText().toString().equals("0")) {
                        pantalla.setText(digito);
                    } else {
                        pantalla.append(digito);
                    }
                }
            });
        }

        buttonResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operacion = pantalla.getText().toString();
                try {
                    double resultado = eval(operacion);
                    pantalla.setText(String.valueOf(resultado));
                } catch (Exception e) {
                    msgError.show();
                }
            }
        });
         buttonBorrar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 pantalla.setText("0");
             }
         });
    }
    public double eval(final String str) {
        return new Object() {
            int pos = -1, ch;
            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1 ;
            }
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " +(char) ch);
                return x;
            }
            double parseExpression(){
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+'))x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }
            double parseTerm(){
                double x = parseFactor();
                for (; ; ) {
                    if (eat('x')) x *= parseFactor();
                    else if (eat('/'))x /= parseFactor();
                    else return x;
                }
            }
            double parseFactor(){
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("unexpected: " + (char) ch);
                }
                return x;
            }
        }.parse();
    }
}
