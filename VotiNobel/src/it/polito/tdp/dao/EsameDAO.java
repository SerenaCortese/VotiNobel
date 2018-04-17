package it.polito.tdp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.model.Esame;

public class EsameDAO {

	public List<Esame> getTuttiEsami() {

		final String sql = "SELECT * FROM esami";

		List<Esame> voti = new LinkedList<Esame>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				voti.add(new Esame(rs.getString("codins"), rs.getString("nomecorso"), rs.getInt("crediti"), rs.getInt("voto")));
			}

			return voti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore DB");
		}
	}
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inserisciEsame(Esame esame) {
		//Insert ignore serve per evitare errore in caso ci sia già un altro esame con quel nome
		String sql = "INSERT IGNORE INTO `esamitriennale`.`esami` (`codins`, `nomecorso`, `voto`, `crediti`) VALUES(?,?,?,?)";
		boolean returnValue = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, esame.getCodins());
			st.setString(2, esame.getNomeCorso());
			st.setInt(3, esame.getVoto());
			st.setInt(4, esame.getCrediti());
			
			int res = st.executeUpdate();	

			if (res == 1)
				returnValue = true;

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore inserimento Db");
		}
		
		return returnValue;
	}

	public boolean cancellaTuttiEsami() {
		
		String sql = "DELETE FROM esami";
		boolean returnValue = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			int res = st.executeUpdate();	

			if (res == 1)
				returnValue = true;

			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore cancellazione Db");
		}
		
		return returnValue;

	}
}
