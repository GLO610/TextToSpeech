package com.example.texttospeech.service;

import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.Toast;

public class MyTell extends Service implements OnInitListener{
    public MyTell() {
    }

    public static TextToSpeech mTts = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onStart(Intent intent, int startId) {
      
        mTts = new TextToSpeech(this, this);
        super.onStart(intent, startId);
    }

 public void onInit(int status) {
     // TODO Auto-generated method stub
     if (status == TextToSpeech.SUCCESS) {
         if (mTts.isLanguageAvailable(Locale.UK) >= 0)

         Toast.makeText( MyTell.this,
                 "Sucessfull intialization of Text-To-Speech engine Mytell ",
                 Toast.LENGTH_LONG).show();


     } else if (status == TextToSpeech.ERROR) {
         Toast.makeText(MyTell.this,
                 "Unable to initialize Text-To-Speech engine",
                 Toast.LENGTH_LONG).show();
     }
 }}
