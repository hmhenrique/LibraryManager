package com.ensta.librarymanager.services;

import java.util.List;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.models.*;

public interface MembreService {

	public List<Membre> getList() throws ServiceException;
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException;
	public Membre getById(int id) throws ServiceException;
	public int create(String nom, String prenom, String adresse, String email, String telephone, Abonnement abonnement) throws ServiceException;
	public void update(Membre membre) throws ServiceException;
	public void delete(int id) throws ServiceException;
	public int count() throws ServiceException;

}
