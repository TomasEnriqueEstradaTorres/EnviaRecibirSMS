package com.example.yeialel.enviarecibirsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class RecibirMensaje extends BroadcastReceiver {
    //Esta clase de encargara de recibir el mensaje enviado.
    @Override
    public void onReceive(Context context, Intent intent){
        Bundle bundle = intent.getExtras();
        SmsMessage[] mensajes;
        String mensajeRecibido = "";

        if ( bundle != null){
            //Recibe un objeto con el contenido del mensaje
            Object[] pdus = (Object[])bundle.get("pdus");
            //Esto leera si el objeto es nulo o se encuentra vacio
            mensajes = new SmsMessage[pdus != null ? pdus.length : 0];
            //Este bucle recontruira el mensaje recido del objeto y lo reconstruira caractar a caracter
            for (int i = 0; i < mensajes.length ; i++) {
                mensajes[i] = SmsMessage.createFromPdu((byte[]) (pdus != null ? pdus[i] : null));
                mensajeRecibido += mensajes[i].getOriginatingAddress();  // datos de quien envia el mensaje
                mensajeRecibido += ": ";
                mensajeRecibido += mensajes[i].getMessageBody();  //Es el cuerpo del mensaje
                mensajeRecibido += "\n";
                //muestra el mensaje que se recibio, pero en este caso usaremos el de la clase principal por temas de recursos
                //Toast.makeText(context, mensajeRecibido, Toast.LENGTH_LONG).show();
                Intent broadcastIntent = new Intent();  // servira para enviar el mensaje recibido
                broadcastIntent.setAction("SMS_RECEIVED_ACTION");  //
                broadcastIntent.putExtra("mensaje", mensajeRecibido); //Envia el mensaje a la clase principal
                context.sendBroadcast(broadcastIntent);
            }
        }
    }
}
