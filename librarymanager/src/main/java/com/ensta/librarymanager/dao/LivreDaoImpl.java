package com.ensta.librarymanager.dao;

import java.sql.*;
import java.util.*;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class LivreDaoImpl implements LivreDao {

    //Singleton
    private static LivreDaoImpl instance;
    private LivreDaoImpl(){};
    public static LivreDaoImpl getInstance(){
        if (instance == null)
            instance = new LivreDaoImpl();
        return instance;
    }

    private static final String selectAllQuery = "SELECT id, titre, auteur, isbn FROM livre ";
    private static final String selectIDQuery = "SELECT id, titre, auteur, isbn FROM livre WHERE id=?;";
    private static final String createQuery = "INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?);";
    private static final String updateQuery = "UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?";
    private static final String countQuery = "SELECT count(*) AS count FROM livre";
    private static final String deleteQuery = "DELETE FROM livre WHERE id=?;";

    /**
     * Function that returns all books in the database
     * @return the list
     */
    @Override
    public List<Livre> getList() throws DaoException{

        List<Livre> livres = new ArrayList<Livre>();

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(selectAllQuery);
            result = statement.executeQuery();

            while(result.next()){
                livres.add(new Livre(result.getInt("id"),result.getString("titre"), result.getString("auteur"), result.getString("isbn")));
            }

        } catch (SQLException e) {
            throw new DaoException("Error SQL Livres database",e);
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
        return livres;
    }

    /**
     * Get one book of the list with the ID
     * @param id
     * @return Book by the chosen ID
     */
	public Livre getById(int id) throws DaoException{

        Livre livreById = new Livre();

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();
            statement = connection.prepareStatement(selectIDQuery);
            statement.setInt(1, id);

            result = statement.executeQuery();

            if(result.next()){
                livreById = new Livre(result.getInt("id"),result.getString("titre"), result.getString("auteur"), result.getString("isbn"));
            }
        } catch (SQLException e) {
            throw new DaoException("Error SQL Livres database",e);
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
    
        return livreById;
    }


    /**
     * Make a new Book into our DB
     * @param titre
     * @param auteur
     * @param isbn
     * @return the new book's id
     */
    @Override
	public int create(String titre, String auteur, String isbn) throws DaoException{

        int id = -1;

        Connection connection = null;

        PreparedStatement statement = null;

        ResultSet result = null;

        try {
            connection = ConnectionManager.getConnection();

            statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, titre);
            statement.setString(2, auteur);
            statement.setString(3, isbn);

            statement.executeUpdate();
            result = statement.getGeneratedKeys();

            if(result.next()){
                id = result.getInt(1);
            }

        } catch (SQLException e) {
            throw new DaoException("Error SQL Livres database",e);
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
     * Update a book with new values
     * @param Livre
     */
    @Override
	public void update(Livre livre) throws DaoException{

        Connection connection = null;

        PreparedStatement statement = null;

        try {
            connection = ConnectionManager.getConnection();

            statement = connection.prepareStatement(updateQuery);
			statement.setString(1, livre.getTitre());
            statement.setString(2, livre.getAuteur());
            statement.setString(3, livre.getISBN());
            statement.setInt(4, livre.getId());
            statement.executeUpdate();


        } catch (SQLException e) {
            throw new DaoException("Error SQL Livres database",e);
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
     * Delete a book by the id
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
            throw new DaoException("Error SQL Livres database",e);
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
     * Make the count of all books
     * @return the total value
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

            if(result.next()){
                total = result.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Error SQL Livres database",e);
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