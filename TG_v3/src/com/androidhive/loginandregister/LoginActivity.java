package com.androidhive.loginandregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		final String login = "adm@adm";
		final String senha = "123";

		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
		final EditText edtLogin = (EditText) findViewById(R.id.edtLogin);
		final EditText edtSenha = (EditText) findViewById(R.id.edtSenha);
		Button btnLogin = (Button) findViewById(R.id.btnLogin);

		// Listening to register new account link
		registerScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Switching to Register screen				
				if (v.getId() == R.id.link_to_register) {
					Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
					startActivity(i);
				}
			}
		});
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.getId() == R.id.btnLogin) {
					if ( login.equals(edtLogin.getText().toString()) && senha.equals(edtSenha.getText().toString())){
						Intent btn = new Intent(getApplicationContext(), MainActivity.class);
						startActivity(btn);
					} else{
						finish();
					}
				
			}}
		});
	}
}