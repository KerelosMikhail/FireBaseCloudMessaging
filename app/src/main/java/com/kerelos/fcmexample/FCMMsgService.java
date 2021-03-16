package com.kerelos.fcmexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Objects;

import static java.security.AccessController.getContext;

public class FCMMsgService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        //you can get your text message here.

        Log.d("token", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("token", "Message data payload: " + remoteMessage.getData());


            for ( String key: remoteMessage.getData().keySet() ){
                Log.d("token in for " , "key: " + key  + "; data:  " +
                        remoteMessage.getData().get(key));
            }
/*

            if (*/
/* Check if data needs to be processed by long running job *//*
 true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
*/

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            showNotification(remoteMessage.getNotification().getTitle() , remoteMessage.getNotification().getBody() );


            //        showmessageShare("Notification" , remoteMessage.getNotification().getBody() );
            Log.d("token", "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        // to get certain key from the i.e. String value = remoteMessage.getData().get("key");
        String message = remoteMessage.getData().get("Keysms");

        Log.d("token" , "message inside service: " + message);
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        // The registration token may change when:
        //The app deletes Instance ID
        //The app is restored on a new device
        //The user uninstalls/reinstall the app
        //The user clears app data.
       //  https://firebase.google.com/docs/cloud-messaging/android/client

        Log.d("token", "Refresed token: " + token );


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

 ////       sendRegistrationToServer(token);

    }
// When on foreground
    public void showNotification( String title, String message){

       // aDDED under test
        Intent intent = new Intent( this, MainActivity.class) ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );

        NotificationCompat.Builder builder = new NotificationCompat.Builder( this , "MyNotifications" )
                .setContentTitle(title)
                .setColor(getResources().getColor(R.color.colorNotification) )  // blue color
                .setSmallIcon(R.drawable.cross)
                .setAutoCancel(true)
                .setContentText(message)

                .setSound(soundUri)  // sound for the notification
                .setContentIntent( pendingIntent );  // Added intent

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify( 999, builder.build() );
    }

    // https://firebase.google.com/docs/cloud-messaging/android/receive
    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

}


