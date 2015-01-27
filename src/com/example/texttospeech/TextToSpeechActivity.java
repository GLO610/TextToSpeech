package com.example.texttospeech;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TextToSpeechActivity extends Activity {

    public static TextToSpeech ttobj;
    private EditText write;
    private Spinner spinner=null;
    private AlarmManagerBroadcastReceiver alarm;
    String[] languages;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_text_to_speech);

	write = (EditText) findViewById(R.id.editText1);
	spinner = (Spinner) findViewById(R.id.spinner1);
	spinner.setSelection(0);
	
	languages = getResources().getStringArray(R.array.language_array);

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
    protected void onStart() {
        super.onStart();
        
        if(ttobj == null){
            ttobj = new TextToSpeech(getApplicationContext(),
    		new TextToSpeech.OnInitListener() {
    		    @Override
    		    public void onInit(int status) {
    			if (status != TextToSpeech.ERROR) {
    			    ttobj.setLanguage(Locale.UK);
    			}
    		    }
    		});
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.text_to_speech, menu);
	return true;
    }

    @Override
    public void onPause() {
//	if (ttobj != null) {
//	    ttobj.stop();
//	    ttobj.shutdown();
//	}
	super.onPause();
    }

    /**
     * Speaks the text from the textEdit field immediately and one time only
     * @param view
     */
    public void speakText(View view) {
	String toSpeak = write.getText().toString();
	
	String lang = spinner.getSelectedItem().toString();
	
	setLanguage(lang);
	
	ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
	
	Toast.makeText(getApplicationContext(), toSpeak+" : in "+ lang, Toast.LENGTH_SHORT)
	.show();
    }

    private void setLanguage(String language) {
	
	if (language.equalsIgnoreCase("english")) {
	    ttobj.setLanguage(Locale.UK);
	} else if (language.equalsIgnoreCase("american")) {
	    ttobj.setLanguage(Locale.US);
	} else if (language.equalsIgnoreCase("canada")) {
	    ttobj.setLanguage(Locale.CANADA);
	} else if (language.equalsIgnoreCase("CANADA_FRENCH")) {
	    ttobj.setLanguage(Locale.CANADA_FRENCH);
	} else if (language.equalsIgnoreCase("china")) {
	    ttobj.setLanguage(Locale.CHINA);
	} else if (language.equalsIgnoreCase("france")) {
	    ttobj.setLanguage(Locale.FRANCE);
	} else if (language.equalsIgnoreCase("GERMAN")) {
	    ttobj.setLanguage(Locale.GERMAN);
	} else if (language.equalsIgnoreCase("ITALY")) {
	    ttobj.setLanguage(Locale.ITALY);
	} else if (language.equalsIgnoreCase("japan")) {
	    ttobj.setLanguage(Locale.JAPANESE);
	} else if (language.equalsIgnoreCase("korea")) {
	    ttobj.setLanguage(Locale.KOREAN);
	} else if (language.equalsIgnoreCase("SimpleChina")) {
	    ttobj.setLanguage(Locale.SIMPLIFIED_CHINESE);
	}
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
