package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataConLinea;
import it.polito.tdp.metrodeparis.model.FermataPair;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {
	
	public List<Fermata> getAllFermateSemplici() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return fermate;
	}
	
	public List<FermataConLinea> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy, id_linea FROM fermata, connessione "+
				"WHERE connessione.id_stazP=fermata.id_fermata OR connessione.id_stazA=fermata.id_fermata "+
				"GROUP BY id_linea "+
				"ORDER BY nome ASC;";
		List<FermataConLinea> fermate = new ArrayList<FermataConLinea>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				FermataConLinea f = new FermataConLinea(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")),rs.getInt("id_linea"));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return fermate;
	}

	public List<FermataPair> getCoppieAdiacenti() {
		List<FermataPair> lista=new ArrayList<FermataPair>();
		
		String sql="SELECT c1.id_fermata as idStazPartenza, c1.nome as nomePartenza, c1.coordX as coordXPartenza,c1.coordY as coordYPartenza, "+
			"c2.id_fermata as idStazArrivo, c2.nome as nomeArrivo, c2.coordX as coordXArrivo,c2.coordY as coordYArrivo, id_linea "+
			"FROM connessione, fermata c1, fermata c2 "+
			"WHERE connessione.id_StazP = c1.id_fermata AND connessione.id_StazA = c2.id_fermata; ";
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				FermataConLinea partenza= new FermataConLinea(rs.getInt("idStazPartenza"), rs.getString("nomePartenza"), new LatLng(rs.getDouble("coordXPartenza"), rs.getDouble("coordYPartenza")),rs.getInt("id_linea"));
				FermataConLinea arrivo =new FermataConLinea(rs.getInt("idStazArrivo"), rs.getString("nomeArrivo"), new LatLng(rs.getDouble("coordXArrivo"), rs.getDouble("coordYArrivo")),rs.getInt("id_linea"));
				FermataPair f= new FermataPair(partenza, arrivo, rs.getInt("id_linea"));
				lista.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return lista;
	}

	public List<Linea> getLinee() {
		List<Linea> linee=new ArrayList<Linea>();
		String sql="SELECT id_linea, nome, velocita, intervallo FROM linea";
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea l=new Linea(rs.getInt("id_linea"),rs.getString("nome"),rs.getDouble("velocita"),rs.getDouble("intervallo"));
				linee.add(l);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return linee;
	}
}
