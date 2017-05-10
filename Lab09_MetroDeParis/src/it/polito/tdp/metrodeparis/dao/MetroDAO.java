package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataPair;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

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
				Fermata partenza= new Fermata(rs.getInt("idStazPartenza"), rs.getString("nomePartenza"), new LatLng(rs.getDouble("coordXPartenza"), rs.getDouble("coordYPartenza")));
				Fermata arrivo =new Fermata(rs.getInt("idStazArrivo"), rs.getString("nomeArrivo"), new LatLng(rs.getDouble("coordXArrivo"), rs.getDouble("coordYArrivo")));
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
