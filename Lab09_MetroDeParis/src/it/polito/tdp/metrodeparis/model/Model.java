package it.polito.tdp.metrodeparis.model;

import java.util.*;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private List<Fermata> listaFermate;
	
	public List<Fermata> getTutteFermate(){
		MetroDAO dao=new MetroDAO();
		if(listaFermate==null){
			listaFermate=dao.getAllFermate();
		}
		return listaFermate;
	}
}
