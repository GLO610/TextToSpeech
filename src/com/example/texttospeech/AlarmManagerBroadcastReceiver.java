package com.example.texttospeech;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.example.texttospeech.service.TTS;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
    
    final public static String ONE_TIME = "onetime";
    
    TextToSpeech ttobj = null;
    
    @Override
    public void onReceive(Context context, Intent intent) {
	if(ttobj != null){
	ttobj = new TextToSpeech(context,
		new TextToSpeech.OnInitListener() {
		    @Override
		    public void onInit(int status) {
			if (status != TextToSpeech.ERROR) {
			    ttobj.setLanguage(Locale.UK);
			}
		    }
		});
	}
	
      PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
            //Acquire the lock
            wl.acquire();
    
            //You can do the processing here.
            Bundle extras = intent.getExtras();
            StringBuilder msgStr = new StringBuilder();
             
            if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
             //Make sure this intent has been sent by the one-time timer button.
             msgStr.append("One time Timer : ");
            }
            Format formatter = new SimpleDateFormat("hh:mm:ss a");
            msgStr.append(formatter.format(new Date()));
    
            Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();
            speakText(context, msgStr.toString());
             
            //Release the lock
            wl.release();
    }
    
    public void SetAlarm(Context context)
       {
           AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
           Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
           intent.putExtra(ONE_TIME, Boolean.FALSE);
           PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
           //After after 5 seconds
           am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 10 , pi); 
       }
    
       public void CancelAlarm(Context context)
       {
           Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
           PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
           AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
           alarmManager.cancel(sender);
       }
    
       public void setOnetimeTimer(Context context){
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
           Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
           intent.putExtra(ONE_TIME, Boolean.TRUE);
           PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
           am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
       }
       

       public void speakText(Context context, String time) {
   	//String toSpeak = write.getText().toString();
//   	Toast.makeText(context, time, Toast.LENGTH_SHORT)
//   		.show();
//   	ttobj.speak(time, TextToSpeech.QUEUE_FLUSH, null);
   	
	   //TextToSpeechActivity.ttobj.speak(time, TextToSpeech.QUEUE_FLUSH, null);
	   context.startService(new Intent(context, TTS.class));
       }
   }
