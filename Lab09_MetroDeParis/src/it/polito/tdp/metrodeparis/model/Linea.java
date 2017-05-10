package it.polito.tdp.metrodeparis.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Linea{
	private int id;
	private String nome;
	private double velocita;
	private double intervallo;
	
	public Linea(int id, String nome, double velocita, double intervallo) {
		super();
		this.id = id;
		this.nome = nome;
		this.velocita = velocita;
		this.intervallo = intervallo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getVelocita() {
		return velocita;
	}
	public void setVelocita(double velocita) {
		this.velocita = velocita;
	}
	public double getIntervallo() {
		return intervallo;
	}
	public void setIntervallo(double intervallo) {
		this.intervallo = intervallo;
	}
}
