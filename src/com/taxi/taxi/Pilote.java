package com.taxi.taxi;

import java.util.ArrayList;

public class Pilote {

	String nom;
	String prenom;
	int id;

	public Pilote(String nom, String prenom, int id) {

		this.nom = nom;
		this.prenom = prenom;
		this.id = id;

	}

	private ArrayList<Pilote> pilotes = new ArrayList<Pilote>();
	{

	}

	public Pilote() {
		super();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public ArrayList<Pilote> getPilotes() {
		return pilotes;
	}

	public void setPilotes(ArrayList<Pilote> pilotes) {
		this.pilotes = pilotes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
