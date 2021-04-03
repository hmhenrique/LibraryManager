package com.ensta.librarymanager.services;

import java.sql.*;
import java.util.*;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.dao.*;

/**
 * MembreServiceImpl is responsable for the connection with the database, assuming the rules
 */
public class MembreServiceImpl implements MembreService {
    //Singleton
    private static MembreServiceImpl instance;
    private MembreServiceImpl(){};
    public static MembreServiceImpl getInstance(){
        if (instance == null)
           instance = new MembreServiceImpl();
        return instance;
    }


    /**
     * get a list of all members
     * @return List of total members in DB
     */
    @Override
    public List<Membre> getList() throws ServiceException{

        List<Membre> membreAllList = new ArrayList<Membre>();
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            membreAllList = membreDao.getList();
            System.out.println("\n List of all members : " + membreAllList);
        } catch (Exception e) {
            throw new ServiceException("Can't get total list of members.", e);
        }

        return membreAllList;
    }

    /**
     * Return all 'emprunt' possibles
     * @return The list of possible 'emprunts' by member
     */
    @Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException{

        List<Membre> membreEmpruntList = new ArrayList<Membre>();
        List<Membre> membres = new ArrayList<Membre>();
        MembreDao membreDao = MembreDaoImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();

        try {
            membres = membreDao.getList();
            for (int i = 0; i < membres.size(); i++){
                if (empruntService.isEmpruntPossible(membres.get(i))){
                    membreEmpruntList.add(membres.get(i));
                }
            }
            System.out.println("\n All 'emprunts' possibles by member: " + membreEmpruntList);
        } catch (Exception e) {
            throw new ServiceException("Can't get possibles 'emprunts' by member.", e);
        }
        return membreEmpruntList;
    }


    /**
     * Get the member with the ID
     * @param id 
     * @return The member
     */
    @Override
	public Membre getById(int id) throws ServiceException{
        Membre theMembre = new Membre();
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            theMembre = membreDao.getById(id);
            System.out.println("\n Chosen member by id: " + theMembre);
        } catch (Exception e) {
            throw new ServiceException("Can't get the member by id.", e);
        }
        return theMembre;
    }

    /**
     * Verifying if first and last names are empties
     * @param nom
     * @param prenom
     * @param adresse
     * @param email
     * @param telephone
     * @param abonnement
     * @return The id of the member created
     */
    @Override
	public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) throws ServiceException{

        int id = -1;
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            if (nom == null || nom == "" || prenom == null  || prenom == ""){
                throw new ServiceException("\n Camps of name empties! Can't create the member.");
            } else{
                nom = nom.toUpperCase();
                
                id = membreDao.create(nom, prenom, adresse, email, telephone, abonnement);
                System.out.println("\n Member created ID : " + id);
            }
        } catch (Exception e) {
            throw new ServiceException("\n Can't create the member.", e);
        }
        return id;
    }


    /**
     * update verifying if first and last names are empties
     * @param Membre
     */
    @Override
	public void update(Membre membre) throws ServiceException{
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            if(membre.getNom() == null || membre.getNom() == "" || membre.getPrenom() == null  || membre.getPrenom() == ""){
                throw new ServiceException("\n Camps of name empties! Can't update the member.");
            } else{
                membre.setNom(membre.getNom().toUpperCase());
                membreDao.update(membre);

                System.out.println("\nMember successfully updated.");
            }
        } catch (Exception e) {
            throw new ServiceException("\nMember can't be updated.", e);
        }
    }

    /**
     * Delete one member by id
     * @param id the id
     */
    @Override
	public void delete(int id) throws ServiceException{

        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            membreDao.delete(id);
            System.out.println("\n Member " + id + " deleted.");
        } catch (Exception e) {
            throw new ServiceException("\n Member can't be deleted.", e);
        }
    }

    /**
     * count the number of members
     * @return the number of members
     */
    @Override
	public int count() throws ServiceException{

        int total = -1;
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            total = membreDao.count();
            System.out.println("\n Total members : " + total);
        } catch (Exception e) {
            throw new ServiceException("\n Can't count all members.", e);
        }
        return total;
    }
}