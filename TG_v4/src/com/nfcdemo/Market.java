package com.nfcdemo;

public class Market {

	//variables
	int ID_MER;
	String NOME_MER;
	String CNPJ_MER;
	String LOGIN_MER;
	String SENHA_MER;

	// Empty constructor
	public Market() {

	}

	// constructor
	public Market(String nome, String cnpj, String login, String senha){
		NOME_MER = nome;
		CNPJ_MER = cnpj;
		LOGIN_MER = login;
		SENHA_MER = senha;
	}
	
	public void setID_MER(int iD_MER) {
		ID_MER = iD_MER;
	}
	
	public void setNOME_MER(String nOME_MER) {
		NOME_MER = nOME_MER;
	}
	
	public void setCNPJ_MER(String cNPJ_MER) {
		CNPJ_MER = cNPJ_MER;
	}
	
	public void setLOGIN_MER(String lOGIN_MER) {
		LOGIN_MER = lOGIN_MER;
	}
	
	public void setSENHA_MER(String sENHA_MER) {
		SENHA_MER = sENHA_MER;
	}
	
	public int getID_MER() {
		return ID_MER;
	}
	
	public String getNOME_MER() {
		return NOME_MER;
	}
	
	public String getCNPJ_MER() {
		return CNPJ_MER;
	}
	
	public String getLOGIN_MER() {
		return LOGIN_MER;
	}
	
	public String getSENHA_MER() {
		return SENHA_MER;
	}
}
