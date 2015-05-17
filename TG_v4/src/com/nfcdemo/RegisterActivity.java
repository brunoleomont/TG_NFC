package com.nfcdemo;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
        
        final DatabaseMarket dm = new DatabaseMarket(this);
        final DatabaseHandler db = new DatabaseHandler(this);
        
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// Switching to Login Screen/closing register screen
				finish();
			}
		});
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/**
		         * CRUD Operations
		         * */
		        // Inserting Products
		        Log.d("Insert: ", "Inserting .."); 
		        //db.addProduct(new Product("COCA2L", "Coca Cola 2 litros", 4.20, "20 Dezembro 2015", 1));
		        //dm.addMarket(new Market("Extra S/A", "123456789", "extra@extra", "123"));
		        
		        // Reading all contacts
		        Log.d("Reading: ", "Reading all markets.."); 
		        List<Product> product = db.getAllProducts();
		        //List<Market> market = dm.getAllMarkets();
		        
		        for (Product pr : product) {
		            String log = "Id: "+ pr.getID_PRO()+" ,TAG: " + pr.getTAG_PRO() + " ,Name: " + pr.getNOME_PRO() + " ,Preco: " + pr.getPRECO_PRO() + " , Data: " + pr.getDATA_PRO();
		        // Writing products to log
		        Log.d("Product: ", log);
		        }
		        
		        /*for (Market mk : market) {
		            String logm = "Id: "+ mk.getID_MER()+" ,Name: " + mk.getNOME_MER() + " ,CNPJ: " + mk.getCNPJ_MER() + " , Login: " + mk.getLOGIN_MER() + " ,Senha: " + mk.getSENHA_MER();
		        // Writing markets to log
		        Log.d("Market: ", logm);
		        }*/
			}
		});
    }
}