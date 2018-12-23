package com.example.yeialel.enviarecibirsms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText numeroTelefono, mensajeEnviar;
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numeroTelefono = (EditText) findViewById(R.id.editTextNumeroTelefono);
        mensajeEnviar = (EditText) findViewById(R.id.editTextMensajeTexto);
        enviar = (Button) findViewById(R.id.buttonEnviar);


        /** Esto de aqui sirve para justificar el texto en donde:
         * @param JUSTIFICATION_MODE_INTER_WORD devuelve un valor entero = 1
         *
         * El otro valor y es por defecto es 'JUSTIFICATION_MODE_NONE' = 0   */
        mensajeEnviar.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);




    }
}
