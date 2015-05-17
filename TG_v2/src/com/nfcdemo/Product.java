package com.nfcdemo;

public class Product {

	// private variables
	int ID_PRO;
	String TAG_PRO;
	String NOME_PRO;
	Double PRECO_PRO;
	String DATA_PRO;
	String FRASE_PRO;

	// Empty constructor
	public Product() {

	}

	// constructor
	public Product(String tag, String name, Double preco, String data) {
		this.TAG_PRO = tag;
		this.NOME_PRO = name;
		this.PRECO_PRO = preco;
		this.DATA_PRO = data;
	}
	
	// constructor
		public Product(String name, Double preco) {
			this.NOME_PRO = name;
			this.PRECO_PRO = preco;
		}

	public void setID_PRO(int id_pro) {
		ID_PRO = id_pro;
	}

	public void setTAG_PRO(String tag_pro) {
		TAG_PRO = tag_pro;
	}

	public void setNOME_PRO(String nome_pro) {
		NOME_PRO = nome_pro;
	}

	public void setPRECO_PRO(Double preco_pro) {
		PRECO_PRO = preco_pro;
	}

	public void setDATA_PRO(String data_pro) {
		DATA_PRO = data_pro;
	}

	public void setFRASE_PRO(String frase_pro) {
		FRASE_PRO = frase_pro;
	}

	public int getID_PRO() {
		return ID_PRO;
	}

	public String getNOME_PRO() {
		return NOME_PRO;
	}

	public String getTAG_PRO() {
		return TAG_PRO;
	}

	public Double getPRECO_PRO() {
		return PRECO_PRO;
	}

	public String getDATA_PRO() {
		return DATA_PRO;
	}

	public String getFRASE_PRO() {
		return FRASE_PRO;
	}
}
