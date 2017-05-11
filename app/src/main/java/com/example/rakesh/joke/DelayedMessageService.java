package com.example.rakesh.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DelayedMessageService extends IntentService {
    public static final String EXTRA_MESSAGE="message";   //use a constant to pass a message from activity to service
    //private Handler handler;
    public static final int NOTIFICATION_ID=5453;  //id can be any number, id is used to identify notification
    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    /*            now we are using notification, so handler and onStartCommand won't be used
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler=new Handler();                                                //creating the handler on the main thread
        return super.onStartCommand(intent, flags, startId);
    }
    */
    @Override
    protected void onHandleIntent(Intent intent) {           //actual task code will be here , (getIntent method is already included)

        synchronized (this) {
            try {
                wait(10000);   //wait 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra("me");  //get the text from the intent
        //Log.v("DelayedMessageService","The message is: "+text);  //to display message in Logcat
        showText(text);
    }
    private void showText(final String text){
        /*    it was used to show message through toast using handler
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();  //getApplicationContext() is used to display toast message in whatever app is in foreground
            }
        });
        */
        Intent intent=new Intent(this,MainActivity.class);           //creating an intent
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);  //taskStackBuilder used to make back button play nicely
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT); //create pending intent
        Notification notification=new Notification.Builder(this)         //building notification object
                .setSmallIcon(R.mipmap.ic_launcher)                      //setting notification properties
                .setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .build();                                              //to finally build notification
        NotificationManager nm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE); //NotificationManager to access the notification service
        nm.notify(NOTIFICATION_ID,notification);  //display notification using android notification service
                     //7When the user clicks on the Notification, the Notification uses its pending intent to start MainActivity.
    }
}
