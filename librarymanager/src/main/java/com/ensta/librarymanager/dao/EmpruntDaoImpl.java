package com.ensta.librarymanager.dao;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDaoImpl implements EmpruntDao {

	//Singleton
    private static EmpruntDaoImpl instance;
    private EmpruntDaoImpl(){};
    public static EmpruntDaoImpl getInstance(){
        if (instance == null)
           instance = new EmpruntDaoImpl();
        return instance;
    }

	
    private static final String selectAllQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
    private static final String selectNoReturnedQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
    private static final String selectNotReturnedMemberQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
	private static final String selectNotReturnedBookQuery = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
	private static final String selectOneQuery = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
	private static final String createQuery = "INSERT INTO Emprunt (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private static final String updateQuery = "UPDATE Emprunt SET idMembre=?, idLivre=?,dateEmprunt=?, dateRetour=? WHERE id=?;";
    private static final String countQuery = "SELECT COUNT(*) AS count FROM emprunt WHERE idMembre IN (SELECT id FROM membre) and idLivre IN (SELECT id FROM livre);";


	/**
     * Function that returns all 'emprunts' in the database
     * @return the list
     */
    @Override
    public List<Emprunt> getList() throws DaoException{
		List<Emprunt> emprunts = new ArrayList<Emprunt>();

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(selectAllQuery);
            result = statement.executeQuery();

            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            while(result.next()){
                emprunts.add(new Emprunt(result.getInt("id"), membreDao.getById(result.getInt("idMembre")), livreDao.getById(result.getInt("idLivre")), result.getDate("dateEmprunt").toLocalDate(), result.getDate("dateRetour") == null ? null : result.getDate("dateRetour").toLocalDate()));
            }

        } catch (SQLException e) {
            throw new DaoException("Error SQL Emprunt database",e);
        } finally{
            try {
				result.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return emprunts;
	}

	/**
     * Get currents lists of 'emprunts' not returned yet
	 * @return the list
     */
    @Override
	public List<Emprunt> getListCurrent() throws DaoException{
		List<Emprunt> currentEmprunts = new ArrayList<Emprunt>();

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(selectNoReturnedQuery);
            result = statement.executeQuery();

            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            while(result.next()){
                currentEmprunts.add(new Emprunt(result.getInt("id"), membreDao.getById(result.getInt("idMembre")), livreDao.getById(result.getInt("idLivre")), result.getDate("dateEmprunt").toLocalDate(), result.getDate("dateRetour") == null ? null : result.getDate("dateRetour").toLocalDate()));
            }

        } catch (SQLException e) {
            throw new DaoException("Error SQL Emprunt database",e);
        } finally{
            try {
				result.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return currentEmprunts;
	}

	/**
     * Get currents lists of 'emprunts' not returned yet by member
	 * @param idMembre member's id
	 * @return the list
     */
    @Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
		List<Emprunt> currentByMemberEmprunts = new ArrayList<Emprunt>();

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(selectNotReturnedMemberQuery);
			statement.setInt(1, idMembre);
            result = statement.executeQuery();

            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            while(result.next()){
                currentByMemberEmprunts.add(new Emprunt(membreDao.getById(result.getInt("idMembre")), livreDao.getById(result.getInt("idLivre")), result.getDate("dateEmprunt").toLocalDate(), result.getDate("dateRetour") == null ? null : result.getDate("dateRetour").toLocalDate()));
            }

        } catch (SQLException e) {
            throw new DaoException("Error SQL Emprunt database",e);
        } finally{
            try {
				result.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return currentByMemberEmprunts;
	}

	/**
     * Get currents lists of 'emprunts' not returned yet by book
	 * @param idLivre book's id
	 * @return the list
     */
    @Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
		List<Emprunt> currentByBookEmprunts = new ArrayList<Emprunt>();

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(selectNotReturnedBookQuery);
			statement.setInt(1, idLivre);
            result = statement.executeQuery();

            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            while(result.next()){
                currentByBookEmprunts.add(new Emprunt(membreDao.getById(result.getInt("idMembre")), livreDao.getById(result.getInt("idLivre")), result.getDate("dateEmprunt").toLocalDate(), result.getDate("dateRetour") == null ? null : result.getDate("dateRetour").toLocalDate()));
            }

        } catch (SQLException e) {
            throw new DaoException("Error SQL Emprunt database",e);
        } finally{
            try {
				result.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return currentByBookEmprunts;
	}


	/**
     * Get one 'emprunt' of the list with the ID
     * @param id
     * @return 'emprunt' by the chosen ID
     */
	@Override
	public Emprunt getById(int id) throws DaoException{

		Emprunt empruntById = new Emprunt();

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(selectOneQuery);
			statement.setInt(1, id);
            result = statement.executeQuery();

            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao livreDao = LivreDaoImpl.getInstance();
            if (result.next()) {
                empruntById = new Emprunt(result.getInt("id"), membreDao.getById(result.getInt("idMembre")), livreDao.getById(result.getInt("idLivre")), result.getDate("dateEmprunt").toLocalDate(), result.getDate("dateRetour") == null ? null : result.getDate("dateRetour").toLocalDate());
            }

        } catch (SQLException e) {
            throw new DaoException("Error SQL Emprunt database",e);
        } finally{
            try {
				result.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return empruntById;
	}


	/**
     * Creat one 'emprunt' 
     * @param idMembre Member id
     * @param idLivre Book id
     * @param dateEmprunt 'emprunt' date
     */
	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException{

        Connection connection = null;

        PreparedStatement statement = null;

        try {
			connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(createQuery);
            statement.setInt(1, idMembre);
            statement.setInt(2, idLivre);
            statement.setString(3, dateEmprunt + "");
            statement.setDate(4, null);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error SQL Emprunt database",e);
        } finally{
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}

	/**
     * Update a 'emprunt' with new values
     * @param Emprunt
     */
	@Override
	public void update(Emprunt emprunt) throws DaoException{

		Connection connection = null;

        PreparedStatement statement = null;

        try {
            connection = ConnectionManager.getConnection();

            statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, emprunt.getMembre().getId());
            statement.setInt(2, emprunt.getLivre().getId());
            statement.setString(3, emprunt.getDateEmprunt()+"");
            statement.setString(4, emprunt.getDateRetour()+"");
            statement.setInt(5, emprunt.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error SQL Membres database",e);
        } finally{
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}

	/**
     * Make the count of all 'emprunts'
	 * @return number of 'emprunts'
     */
    @Override
	public int count() throws DaoException{
		int total = -1;

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

		try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(countQuery);
            result = statement.executeQuery();

            if (result.next()) {
                total = result.getInt(1);
			}

        } catch (SQLException e) {
            throw new DaoException("Error SQL Emprunt database",e);
        } finally{
            try {
				result.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return total;
	}
}