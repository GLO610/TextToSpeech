package com.example.texttospeech.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

public class TTS extends Service implements TextToSpeech.OnInitListener {

    private TextToSpeech mTts;
    //private String spokenText;

    @Override
    public void onCreate() {
        mTts = new TextToSpeech(this, this);
        // This is a good place to set spokenText
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            StringBuilder msgStr = new StringBuilder();
            
            Format formatter = new SimpleDateFormat("hh:mm:ss a");
            msgStr.append(formatter.format(new Date()));
            
            int result = mTts.setLanguage(Locale.US);
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                mTts.speak(msgStr.toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    
}