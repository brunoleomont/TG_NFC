package com.androidhive.loginandregister;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

import android.speech.tts.TextToSpeech;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private NfcAdapter mAdapter;
	private boolean mInWriteMode;
	private Button mWriteTagButton;
	private EditText editText;
	private TextView mTextView;
	//private String mensagem;

	// minha text view
	private TextView mTextView1;
	//private TextToSpeech tts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		DatabaseHandler db = new DatabaseHandler(this);
		//DatabaseMarket dm = new DatabaseMarket(this);
		/**
         * CRUD Operations
         * */
        // Inserting Products
        Log.d("Insert: ", "Inserting .."); 
        //db.addProduct(new Product("COCA2L", "Coca Cola 2 litros", 4.20, "20 de Dezembro de 2015"));
        //db.addProduct(new Product("NESC400G", "Nescau 400 gramas", 4.0, "22 de Dezembro de 2016"));
        //dm.addMarket(new Market("Extra S/A", "123456789", "extra@extra", "123"));
        
        // Reading all contacts
        Log.d("Reading: ", "Reading all products.."); 
        List<Product> product = db.getAllProducts();
        //List<Market> market = dm.getAllMarkets();
        
        for (Product pr : product) {
            String log = "Id: "+ pr.getID_PRO()+" ,TAG: " + pr.getTAG_PRO() + " ,Name: " + pr.getNOME_PRO() + " ,Preco: " + pr.getPRECO_PRO() + " , Data: " + pr.getDATA_PRO() + " , Frase: " + pr.getFRASE_PRO();
        // Writing products to log
        Log.d("Product: ", log);
        }
        
        /*for (Market mk : market) {
            String log = "Id: "+ mk.getID_MER()+" ,Name: " + mk.getNOME_MER() + " ,CNPJ: " + mk.getCNPJ_MER() + " , Login: " + mk.getLOGIN_MER() + " ,Senha: " + mk.getSENHA_MER();
        // Writing markets to log
        Log.d("Market: ", log);
        }*/
		//tts = new TextToSpeech(this, this);
		
		// grab our NFC Adapter
		mAdapter = NfcAdapter.getDefaultAdapter(this);

		// button that starts the tag-write procedure
		mWriteTagButton = (Button) findViewById(R.id.write_tag_button);
		editText = (EditText) findViewById(R.id.editText);
		mWriteTagButton.setOnClickListener(this);
		
		// meu botão para falar
		/*mWriteTagButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				speakOut();
			}

		});*/

		// TextView that we'll use to output messages to screen
		mTextView = (TextView) findViewById(R.id.text_view);

		// minha text view
		mTextView1 = (TextView) findViewById(R.id.text_view);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.write_tag_button) {
			Log.v("EditText", editText.getText().toString());
			displayMessage("Toque e segure tag contra o telefone para escrever.");
			enableWriteMode();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		disableWriteMode();
	}

	@Override
	public void onNewIntent(Intent intent) {
		if (mInWriteMode) {
			mInWriteMode = false;

			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			writeTag(tag);
		}
	}

	/**
	 * Force this Activity to get NFC events first
	 */
	private void enableWriteMode() {
		mInWriteMode = true;

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, getClass())
						.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter tagDetected = new IntentFilter(
				NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter[] filters = new IntentFilter[] { tagDetected };

		mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
	}

	private void disableWriteMode() {
		mAdapter.disableForegroundDispatch(this);
	}

	/**
	 * Format a tag and write our NDEF message
	 */
	private boolean writeTag(Tag tag) {
		// record to launch Play Store if app is not installed
		NdefRecord appRecord = NdefRecord.createApplicationRecord("om.androidhive.loginandregister");
		
		byte[] payload = getRandomConsole().getBytes();
		byte[] mimeBytes = MimeType.NFC_DEMO.getBytes(Charset
				.forName("US-ASCII"));
		NdefRecord cardRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeBytes, new byte[0], payload);
		NdefMessage message = new NdefMessage(new NdefRecord[] { cardRecord,
				appRecord });

		try {
			// see if tag is already NDEF formatted
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();

				if (!ndef.isWritable()) {
					displayMessage("Tag de somente leitura.");
					return false;
				}

				// work out how much space we need for the data
				int size = message.toByteArray().length;
				if (ndef.getMaxSize() < size) {
					displayMessage("Tag não tem espaço livre suficiente.");
					return false;
				}

				ndef.writeNdefMessage(message);
				displayMessage("Tag escrita com sucesso.");
				return true;
			} else {
				// attempt to format tag
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						format.format(message);
						displayMessage("Tag escrito com sucesso!\n Feche este aplicativo e tag de digitalização.");
						return true;
					} catch (IOException e) {
						displayMessage("Não é possível formatar tag para NDEF.");
						return false;
					}
				} else {
					displayMessage("Tag não parece compatível com o formato NDEF.");
					return false;
				}
			}
		} catch (Exception e) {
			displayMessage("Falha para escrever tag");
		}

		return false;
	}

	private String getRandomConsole() {
		String msg = editText.getEditableText().toString();
		return msg;
		//return "Coca Cola três e noventa";
		/*
		 * double rnd = Math.random(); if(rnd<0.25) return "nes"; else
		 * if(rnd<0.5) return "snes"; else if(rnd<0.75) return "megadrive"; else
		 * return "mastersystem";
		 */
	}

	private void displayMessage(String message) {
		mTextView.setText(message);
	}

	private void dMessage(String message) {
		mTextView1.setText(message);
	}
}