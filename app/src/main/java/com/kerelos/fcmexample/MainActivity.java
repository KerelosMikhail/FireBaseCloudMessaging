package com.kerelos.fcmexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvdata = findViewById(R.id.tvdata);

// 1 deprecated    private String m_Token ;       m_Token = FirebaseInstanceId.getInstance().getToken();

        findViewById(R.id.btnToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

// 2 deprecated               Log.d("token", "the token: " + m_Token );

                ////////////////////////////////////////////////////////////////////////////////////////////////
                // Retrieve the current registration token from:
                // https://firebase.google.com/docs/cloud-messaging/android/client
                /////////////////////////////////////////////////////////////////////////
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("token", "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                String token = task.getResult().getToken();

                                // Log and toast
                                //      String msg = getString(R.string.msg_token_fmt, token);

                                Log.d("token", " the needed token is : " + token);
                                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                            }
                        });

                ////////////////////////////////////////////////////////////////////////////////////////////


            }
        });




        // when starts up
        if ( getIntent().getExtras() != null ){

            String lunchMSG = "";

            tvdata.setText("");

            for ( String Keysms:getIntent().getExtras().keySet() ){

         //      Object val = getIntent().getExtras().get(Keysms );

//                lunchMSG = "key: " + key1 +" Val: " + val ;



                tvdata.append(  getIntent().getExtras().get(Keysms )   + "\n" )  ;
            }

          //   tvdata.setText( lunchMSG );




        }else{
            tvdata.setText("No lunch data");
        }

     // Topic messaging on Android https://firebase.google.com/docs/cloud-messaging/android/topic-messaging

        ////////////////--------------------------////////////////////////////

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel( "MyNotifications","MyNotifications",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class );
            manager.createNotificationChannel( channel );

        }


    }
}
