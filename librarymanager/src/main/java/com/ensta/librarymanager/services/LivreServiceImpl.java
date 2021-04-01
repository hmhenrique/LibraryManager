package com.ensta.librarymanager.services;

import java.sql.*;
import java.util.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.dao.*;

/**
 * LivreServiceImpl is responsable for the connection with the database, assuming the rules
 */
public class LivreServiceImpl implements LivreService {
    //Singleton
    private static LivreServiceImpl instance;
    private LivreServiceImpl(){};
    public static LivreServiceImpl getInstance(){
        if (instance == null)   instance = new LivreServiceImpl();
        return instance;
    }

    /**
     * get a list of all books
     * @return List of total members in DB
     */
    @Override
    public List<Livre> getList() throws ServiceException{

        List<Livre> livreAllList = new ArrayList<Livre>();
        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            livreAllList = livreDao.getList();
            System.out.println("\n List of all books : " + livreAllList);
        } catch (Exception e) {
            throw new ServiceException("Can't get total list of books.", e);
        }

        return livreAllList;
    }

    /**
     * Get the list of all disponibles books 
     * @return List of books disponibles
     */
    @Override
	public List<Livre> getListDispo() throws ServiceException{
        List<Livre> livreDipoList = new ArrayList<Livre>();
        LivreDao livreDao = LivreDaoImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Livre> livreAllList  = new ArrayList<Livre>();
        
        try {
            livreAllList = livreDao.getList();
            for (int i = 0; i < livreAllList.size(); i++) {
                if (empruntService.isLivreDispo(livreAllList.get(i).getId()))
                    livreDipoList.add(livreAllList.get(i));
            }
            System.out.println("\n All books disponibles : " + livreDipoList);
        } catch (Exception e) {
            throw new ServiceException("\n Can't get disponible books list.", e);
        }
        return livreDipoList;
    }

    /**
     * Get the book with the ID
     * @param id 
     * @return The id's book
     */
    @Override
	public Livre getById(int id) throws ServiceException{

        Livre theLivre = new Livre();
        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            theLivre = livreDao.getById(id);
            System.out.println("\n Chosen book by id: " + theLivre);
        } catch (Exception e) {
            throw new ServiceException("Can't get the book by id.", e);
        }
        return theLivre;
    }


    /**
     * Verifying if the title is empty
     * @param titre
     * @param auteur
     * @param isbn
     * @return The book's created id
     */
    @Override
	public int create(String titre, String auteur, String isbn) throws ServiceException{
        
        int id = -1;
        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            if (titre == null || titre == ""){
                throw new ServiceException("\n Empty Title! Can't create.");
            } else{
                id = livreDao.create(titre, auteur, isbn);
                System.out.println("\n Book created ID : " + id);
            }
        } catch (Exception e) {
            throw new ServiceException("\n Can't create the book.", e);
        }
        return id;
    }

    /**
     * update verifying if title is empty
     * @param Livre
     */
    @Override
	public void update(Livre livre) throws ServiceException{

        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            if (livre.getTitre() == null || livre.getTitre() == ""){
                throw new ServiceException("\n Empty Title! Can't update the book.");
            } else{
                livre.setTitre(livre.getTitre().toUpperCase());
                livreDao.update(livre);

                System.out.println("\n Book successfully updated.");
            }
        } catch (Exception e) {
            throw new ServiceException("\n Book can't be updated.", e);
        }
    }


    /**
     * Delete one book by id
     * @param id the id
     */
    @Override
	public void delete(int id) throws ServiceException{

        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            livreDao.delete(id);
            System.out.println("\n Book " + id + " deleted.");
        } catch (Exception e) {
            throw new ServiceException("\n Book can't be deleted.", e);
        }
    }


    /**
     * count the number of books
     * @return the number of books
     */
    @Override
	public int count() throws ServiceException{

        int total = -1;
        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            total = livreDao.count();
            System.out.println("\n Total books : " + total);
        } catch (Exception e) {
            throw new ServiceException("\n Can't count all books.", e);
        }
        return total;
    }

}