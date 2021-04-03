package com.ensta.librarymanager.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.ensta.librarymanager.utils.FillDatabase;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.models.*;

/**
 * Tests the implemented daos
 */
public class DaoTest{
	public static void main(String[] args) throws Exception {
        FillDatabase.main(args);
        //testMembre();
        //testLivre();
        testEmprunt();
    }

    /**
     * Tests for member's dao class
     * @throws DaoException
     */
    public static void testMembre() throws DaoException {
        MembreDao membreDao = MembreDaoImpl.getInstance();

        System.out.println("\nMembers tests\n");

        List<Membre> list = new ArrayList<Membre>();
        list = membreDao.getList();
        System.out.println("\n Total list utilized " + list);

        Membre membreByID = new Membre();
        membreByID = membreDao.getById(1);
        System.out.println("\n Member by ID : " + membreByID);

        int idMember = membreDao.create("Rodrigues", "Henrique", "ENSTA", "henrique@email.fr", "+33071234567", Abonnement.VIP);
        System.out.println("\n New id initialized: " + idMember);
        Membre membreByID2 = new Membre();
        membreByID2 = membreDao.getById(idMember);
        System.out.println("\n Member by ID : " + membreByID2);

        Membre test = new Membre(idMember, "LASTNAME", "NAME", "ENSTA", "email@ensta-paris.fr", "+33077654321", Abonnement.PREMIUM);
        membreDao.update(test);
        Membre membreByID3 = new Membre();
        membreByID3 = membreDao.getById(idMember);
        System.out.println("\n Member by ID : " + membreByID3);

        membreDao.delete(idMember);
        list = membreDao.getList();
        System.out.println("\n Total list updated : " + list);

        int totalMembers = membreDao.count();
        System.out.println("\n Total members : " + totalMembers);
    }


    /**
     * Tests for books's dao class
     * @throws DaoException
     */
    public static void testLivre() throws DaoException {

        LivreDao livreDao = LivreDaoImpl.getInstance();

        List<Livre> list = new ArrayList<Livre>();
        list = livreDao.getList();
        System.out.println("Book list: " + list);

        Livre livreByID = new Livre();
        livreByID = livreDao.getById(4);
        System.out.println("\n Book by ID: " + livreByID);

        int id_book = livreDao.create("My Book", "My Autor", "123456");
        System.out.println("\n New book id : " + id_book);
        Livre livreByID2 = new Livre();
        livreByID2 = livreDao.getById(id_book);
        System.out.println("\n Book by ID: " + livreByID2);

        Livre test = new Livre(id_book, "My Book 2", "My Autor 2", "123456");
        livreDao.update(test);
        list = livreDao.getList();
        System.out.println("\n Book list: " + list);

        livreDao.delete(id_book);
        list = livreDao.getList();
        System.out.println("\n Book list updated : " + list);

        int numerOfBooks = livreDao.count();
        System.out.println("\n Total number of books in DB: " + numerOfBooks);

    }


    /**
     * Tests for emprunt's dao class
     * @throws DaoException
     */
    public static void testEmprunt() throws DaoException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        LivreDao livreDao = LivreDaoImpl.getInstance();
        MembreDao membreDao = MembreDaoImpl.getInstance();

        List<Emprunt> list = new ArrayList<Emprunt>();
        list = empruntDao.getList();
        System.out.println("\n 'Emprunts' list: " + list);

        list = empruntDao.getListCurrent();
        System.out.println("\n Current 'Emprunts' list: " + list);

        list = empruntDao.getListCurrentByMembre(5);
        System.out.println("\n Current 'Emprunts' list by member: " + list);

        list = empruntDao.getListCurrentByLivre(3);
        System.out.println("\n Current 'Emprunts' list by book: " + list);

        Emprunt test = empruntDao.getById(5);
        System.out.println("\n Selected 'Emprunt' : " + test);

        empruntDao.create(1, 4, LocalDate.now());
        list = empruntDao.getList();
        System.out.println("\n List updated with one creation: " + list);


        int id_book = livreDao.create("My Book", "My Autor", "123456");
        Livre test_livre = new Livre();
        test_livre = livreDao.getById(id_book);
        int idMember = membreDao.create("Rodrigues", "Henrique", "ENSTA", "henrique@email.fr", "+33071234567", Abonnement.VIP);
        Membre test_membre = new Membre();
        test_membre = membreDao.getById(idMember);
        test = new Emprunt(2, test_membre, test_livre, LocalDate.now(), LocalDate.now().plusDays(60));
        empruntDao.update(test);
        list = empruntDao.getList();
        System.out.println("\n List updated: " + list);
        //System.out.println("\n " + test_livre.getId() + " " + test_membre.getId());


        int total = empruntDao.count();
        System.out.println("\n Number of 'emprunts' in DB : " + total);

    }
}
