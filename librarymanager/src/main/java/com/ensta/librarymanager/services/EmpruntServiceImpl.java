package com.ensta.librarymanager.services;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.dao.*;

/**
 * EmpruntServiceImpl is responsable for the connection with the database, assuming the rules
 */
public class EmpruntServiceImpl implements EmpruntService {
    //Singleton
    private static EmpruntServiceImpl instance;
    private EmpruntServiceImpl(){};
    public static EmpruntServiceImpl getInstance(){
        if (instance == null)   instance = new EmpruntServiceImpl();
        return instance;
    }

    /**
     * get a list of all 'emprunts'
     * @return List of total loans in DB
     */
    @Override
    public List<Emprunt> getList() throws ServiceException{

        List<Emprunt> empruntAllList = new ArrayList<Emprunt>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            empruntAllList = empruntDao.getList();
            System.out.println("\n List of all loans : " + empruntAllList);
        } catch (Exception e) {
            throw new ServiceException("Can't get total list of loans.", e);
        }
        return empruntAllList;
    }


    /**
     * get a list of loans actives
     * @return The list 
     */
    @Override
	public List<Emprunt> getListCurrent() throws ServiceException{

        List<Emprunt> empruntActivesList = new ArrayList<Emprunt>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            empruntActivesList = empruntDao.getListCurrent();
            System.out.println("\n List of all loans not returneds yet : " + empruntActivesList);
        } catch (Exception e) {
            throw new ServiceException("Can't get total list of all loans not returneds yet.", e);
        }
        return empruntActivesList;
    }


    /**
     * Loan list by member
     * @param idMembre
     * @return The list by members
     */
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException{

        List<Emprunt> empruntMembresList = new ArrayList<Emprunt>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            empruntMembresList = empruntDao.getListCurrentByMembre(idMembre);
            System.out.println("\n List of all loans not returneds yet by members : " + empruntMembresList);
        } catch (Exception e) {
            throw new ServiceException("Can't get total list of all loans not returneds yet by members.", e);
        }
        return empruntMembresList;
    }


	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
        
        List<Emprunt> empruntLivresList = new ArrayList<Emprunt>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            empruntLivresList = empruntDao.getListCurrentByLivre(idLivre);
            System.out.println("\n List of all loans not returneds yet by books : " + empruntLivresList);
        } catch (Exception e) {
            throw new ServiceException("Can't get total list of all loans not returneds yet by books.", e);
        }
        return empruntLivresList;
    }

    /**
     * Get the 'emprunt' with the ID
     * @param id 
     * @return The loan's id
     */
	public Emprunt getById(int id) throws ServiceException{

        Emprunt theEmprunt = new Emprunt();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            theEmprunt = empruntDao.getById(id);
            System.out.println("\n Chosen loan by id: " + theEmprunt);
        } catch (Exception e) {
            throw new ServiceException("Can't get the loan by id.", e);
        }
        return theEmprunt;
    }

	/**
     * Creat one 'emprunt' 
     * @param idMembre Member id
     * @param idLivre Book id
     * @param dateEmprunt 'emprunt' date
     */
	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException{

        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            empruntDao.create(idMembre, idLivre, dateEmprunt);
            System.out.println("\n Loan created.");
        } catch (Exception e) {
            throw new ServiceException("\n Can't create the loan.", e);
        }
    }


    /**
     * Update the returneDate of a returned loan
     * @param id
     */
    @Override
	public void returnBook(int id) throws ServiceException{

        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            Emprunt empruntToUpdate = empruntDao.getById(id);
            empruntToUpdate.setDateRetour(LocalDate.now());
            empruntDao.update(empruntToUpdate);

            System.out.println("\n  Book returned, loan updated " + empruntToUpdate);
        } catch (Exception e) {
            throw new ServiceException("\n Can't be returned yet.", e);
        }

    }


    /**
     * count the number of loans
     * @return the number of loans
     */
    @Override
	public int count() throws ServiceException{

        int total = -1;
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            total = empruntDao.count();
            System.out.println("\n Total loans : " + total);
        } catch (Exception e) {
            throw new ServiceException("\n Can't count all loans.", e);
        }
        return total;
    }


    /**
     * Check if a book is disponible
     * @param idLivre
     * @return true if is disponible and false if not
     */
    @Override
	public boolean isLivreDispo(int idLivre) throws ServiceException{
        boolean dispo = false;
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            dispo = empruntDao.getListCurrentByLivre(idLivre).isEmpty();
            System.out.println("\n Status of chosen Book: " + dispo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dispo;
    }


	/**
     * Check if a member can get books
     * @param Membre
     * @return true if is disponible and false if not
     */
    @Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		boolean dispo = false;
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            dispo = empruntDao.getListCurrentByMembre(membre.getId()).isEmpty();
            System.out.println("\n The member can get another book?: " + dispo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dispo;
	}

}