package com.example.yeialel.enviarecibirsms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button enviar;  // Boton de enviar
    private EditText numeroTelefono, mensajeEnviar; // seran el numero de telefono y el mensaje que se enviara
    private IntentFilter intentFilter;  // Servira para poder ver el mensaje recibido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentFilter = new IntentFilter(); // Filtrara el archivo enviado
        intentFilter.addAction("SMS_RECEIVED_ACTION");  // sirve para registar el receptor

        //Se declara el contenido de la interfaz
        enviar = (Button) findViewById(R.id.buttonEnviar);
        numeroTelefono = (EditText) findViewById(R.id.editTextNumeroTelefono);
        mensajeEnviar = (EditText) findViewById(R.id.editTextMensajeTexto);

        //Se pone a la escucha el boton de enviar
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje = mensajeEnviar.getText().toString();  //Recibe el mensaje a enviar
                String numero = numeroTelefono.getText().toString(); // Recibe el numero de telefono a quien se quiere enviar el mensaje
                enviarMensaje(numero, mensaje);  //funcion que enviara el mensaje --> ver FUNCIONES CREADAS abajo
            }
        });
    }

    @Override
    public void onResume() {
        /**Cuando se reanuda una actividad desde el estado de pausa, el sistema llama al método onResume().
         * Este método cada vez que la actividad pasa a primer plano reiniciara la aplicacion, incluso
         * cuando esta se crea por primera vez. Por ende, se debe implementar onResume() para inicializar los componentes
         * que lancen en onPause() y para realizar otras inicializaciones que deban ejecutarse cada vez que la actividad
         * entre en el estado de reanudación (por ejemplo, activar animaciones e inicializar componentes que solo se
         * usen mientras el usuario ponen en foco a la actividad).       */
        registerReceiver(intentReceiver, intentFilter);  // Reinicia el receptor de mensajes
        super.onResume();
    }

    @Override
    public void onPause() {
        /**Sirve para que el sistema no consuma recursos mientras la aplicacion se encuentra en pausa.
         * Liberar recursos del sistema, como receptores de mensajes, manejadores de sensores
         * (como GPS [sistema de posicionamiento global]), u otros recursos que puedan afectar la duración
         * de la batería mientras tu actividad está en pausa y el usuario no los necesita.       */
        unregisterReceiver(intentReceiver);  // Pausa el receptor de mensajes anulandolo temporalmente
        super.onPause();
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //FUNCIONES CREADAS

    //Esta funcion se encargara de enviar el mensaje
    private void enviarMensaje(String numero, String mensaje){
        try {  //Si se tiene permisos de envio se ejecutara este bloque
            String SENT = "Message Sent";
            String DELIVERED = "Message Delivered";

            PendingIntent sentIp = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED),0);

            SmsManager sms = SmsManager.getDefault(); // Sirve para enviar el servicio de mensajes por defecto
            sms.sendTextMessage(numero, null, mensaje, sentIp, deliveredPI);// recibe los parametros que se enviaran

            Toast.makeText(getApplicationContext(), "Su mensaje se ha enviado", Toast.LENGTH_LONG).show(); //Muestra de mensaje de enviado

        }catch (Exception e){ //Si no se tiene permisos para enviar mensaje el programa ejecutara este bloque
            Toast.makeText(getApplicationContext(), "No tiene permisos para enviar mensajes", Toast.LENGTH_LONG).show();
        }
    }

    /**Esta funcion mostrara el mensaje que sera recibido por la clase 'RecibirMensaje.class'
     * y tambien servira para que el sistema pueda ahorar recursos del sistema por medio de los
     * metodos 'onResume()' y 'onPause()' usando el 'intentReceiver'      */
    private BroadcastReceiver intentReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            //Muestra el mensaje recibido por medio de intent con su clave 'mensaje'
            Toast recibir = Toast.makeText(getApplicationContext(),"Mensaje Recibido\n" + intent.getExtras().getString("mensaje"), Toast.LENGTH_LONG);
            recibir.setGravity(Gravity.TOP | Gravity.CENTER, 0, 1000);
            recibir.show();
        }
    };
}
