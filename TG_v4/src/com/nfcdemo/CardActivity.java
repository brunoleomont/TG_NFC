package com.nfcdemo;

import java.util.Locale;

import com.nfcdemo.R;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class CardActivity extends Activity implements
TextToSpeech.OnInitListener {
	private ImageView mCardView;
	
	//minha text view
	private TextView mTextView1;
	private TextToSpeech tts;
	private String mensagem = "coca cola 3 reais";
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_activity);
		
        mCardView = (ImageView)findViewById(R.id.card_view);
        // minha text view
        mTextView1 = (TextView)findViewById(R.id.textView1);
        
        tts = new TextToSpeech(this, this);
        
        // see if app was started from a tag and show game console
        Intent intent = getIntent();
        if(intent.getType() != null && intent.getType().equals(MimeType.NFC_DEMO)){
        	Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) rawMsgs[0];
            NdefRecord cardRecord = msg.getRecords()[0];
            
            String consoleName = new String(cardRecord.getPayload());
            displayCard(consoleName);
            mensagem = consoleName;
        }
	}
	
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
		new Locale("pt", "br");
		
		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(new Locale("pt"));

			//tts.setPitch(5); // set pitch level

			tts.setSpeechRate(0.8f); // set speech speed rate

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "Language is not supported");
			} else {
				//btnSpeak.setEnabled(true);
				speakOut(mensagem);
			}

		} else {
			Log.e("TTS", "Initilization Failed");
		}

	}
	
	private void displayCard(String consoleName){
		int cardResId = 0;
		/*if(consoleName.equals("nes")) cardResId = R.drawable.nes;
		else if(consoleName.equals("snes")) cardResId = R.drawable.snes;
		else if(consoleName.equals("megadrive")) cardResId = R.drawable.megadrive;
		else if(consoleName.equals("mastersystem")) cardResId = R.drawable.mastersystem;*/
		//mCardView.setImageResource(cardResId);
		mTextView1.setText(consoleName);
		speakOut(mensagem);
	}
	
	private void speakOut(String msg) {
		// método que faz a leitura em voz eletrônica
		tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}
	
}
