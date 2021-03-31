package com.ensta.librarymanager.dao;

import java.sql.*;
import java.util.*;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl implements MembreDao {


    /**
     * Singleton
     */
    private static MembreDaoImpl instance;
    private MembreDaoImpl(){};
    public static MembreDaoImpl getInstance(){
        if (instance == null)   
            instance = new MembreDaoImpl();
        return instance;
    }

    private static final String selectAllQuery = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
    private static final String selectIDQuery = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id=?;";
    private static final String createQuery = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String updateQuery = "UPDATE membre SET nom=?, prenom=?, adresse=?, email=?, telephone=?, abonnement=? WHERE id=?;";
    private static final String deleteQuery = "DELETE FROM membre WHERE id=?;";
    private static final String counterQuery = "SELECT COUNT(id) AS count FROM membre;";


    /**
     * Function that returns all members in the database
     * @return the list
     */
    @Override
    public List<Membre> getList() throws DaoException{
        List<Membre> membres = new ArrayList<Membre>();

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(selectAllQuery);
            result = statement.executeQuery();

            while(result.next()){
                membres.add(new Membre(result.getInt("id"),result.getString("nom"), result.getString("prenom"), result.getString("email"), result.getString("telephone"), result.getString("adresse"), Abonnement.valueOf(result.getString("abonnement"))));
            }

        } catch (SQLException e) {
            throw new DaoException("Error SQL Membres database",e);
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
        return membres;
    }

    /**
     * Get one member of the list with the ID
     * @param id
     * @return Member by the chosen ID
     */
    public Membre getById(int id) throws DaoException{

        Membre membreById = new Membre();

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(selectIDQuery);
            statement.setInt(1, id);

            result = statement.executeQuery();

            if(result.next()){
                membreById = new Membre(result.getInt("id"),result.getString("nom"), result.getString("prenom"), result.getString("email"), result.getString("telephone"), result.getString("adresse"), Abonnement.valueOf(result.getString("abonnement")));
            }
        } catch (SQLException e) {
            throw new DaoException("Error SQL Membres database",e);
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
    
        return membreById;
    }



    /**
     * Make a new MemberFile into our DB
     * @param nom
     * @param prenom
     * @param adresse
     * @param email
     * @param telephone
     * @param subscription
     * @return the new member'id
     */
    @Override
	public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) throws DaoException{

        int id = -1;

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();

            statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, adresse);
            statement.setString(4, email);
            statement.setString(5, telephone);
            statement.setString(6, abonnement.toString());

            statement.executeUpdate();
            result = statement.getGeneratedKeys();

            if(result.next()){
                id = result.getInt(1);
            }

        } catch (SQLException e) {
            throw new DaoException("Error SQL Membres database",e);
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
        return id;
    }

    /**
     * Update a member with new values
     * @param Membre
     */
    @Override
	public void update(Membre membre) throws DaoException{
        
        Connection connection = null;

        PreparedStatement statement = null;

        try {
            connection = ConnectionManager.getConnection();

            statement = connection.prepareStatement(updateQuery);
            statement.setString(1, membre.getNom());
            statement.setString(2, membre.getPrenom());
            statement.setString(3, membre.getAdresse());
            statement.setString(4, membre.getEmail());
            statement.setString(5, membre.getTelephone());
            statement.setString(6, membre.getAbonnement().toString());
            statement.setInt(7, membre.getId());
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
     * Delete a member by the id
     * @param id
     */
    @Override
	public void delete(int id) throws DaoException{

        Connection connection = null;

        PreparedStatement statement = null;

        try {
            connection = ConnectionManager.getConnection();

            statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, id);
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
     * Make the count of all members
     * @return num of members
     */
    @Override
	public int count() throws DaoException{

        int total = -1;

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();

            statement = connection.prepareStatement(counterQuery);
            result = statement.executeQuery();

            if(result.next()){
                total = result.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Error SQL Membres database",e);
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