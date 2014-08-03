package com.example.texttospeech;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TextToSpeechActivity extends Activity {

    public static TextToSpeech ttobj;
    private EditText write;
    private AlarmManagerBroadcastReceiver alarm;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_text_to_speech);

	write = (EditText) findViewById(R.id.editText1);

	ttobj = new TextToSpeech(getApplicationContext(),
		new TextToSpeech.OnInitListener() {
		    @Override
		    public void onInit(int status) {
			if (status != TextToSpeech.ERROR) {
			    ttobj.setLanguage(Locale.UK);
			}
		    }
		});
	alarm = new AlarmManagerBroadcastReceiver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.text_to_speech, menu);
	return true;
    }

    @Override
    public void onPause() {
	if (ttobj != null) {
	    ttobj.stop();
	    ttobj.shutdown();
	}
	super.onPause();
    }

    public void speakText(View view) {
	String toSpeak = write.getText().toString();
	Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT)
		.show();
	ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    // Timer methods
    public void startRepeatingTimer(View view) {
	Context context = this.getApplicationContext();
	if (alarm != null) {
	    alarm.SetAlarm(context);
	} else {
	    Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
	}
    }

    public void cancelRepeatingTimer(View view) {
	Context context = this.getApplicationContext();
	if (alarm != null) {
	    alarm.CancelAlarm(context);
	} else {
	    Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
	}
    }

    public void onetimeTimer(View view) {
	Context context = this.getApplicationContext();
	if (alarm != null) {
	    alarm.setOnetimeTimer(context);
	} else {
	    Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
	}
    }

}
