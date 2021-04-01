package com.ensta.librarymanager.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.ensta.librarymanager.utils.FillDatabase;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.*;

/**
 * Tests the implemented services
 */
public class ServiceTest {
    public static void main(String[] args) throws Exception{
        FillDatabase.main(args);

        //membreTest();
        //livreTest();
        empruntTest();
        
    }
    
    /**
     * Test MembreService
     * @throws ServiceException
     */
    public static void membreTest() throws ServiceException{
        MembreService membreService = MembreServiceImpl.getInstance();

        membreService.getList();
        membreService.getListMembreEmpruntPossible();
        membreService.getById(5);
        membreService.create("Rodrigues", "Henrique", "ENSTA", "henrique@email.fr", "+33071234567", Abonnement.VIP);
        //membreService.create("", "Henrique", "ENSTA", "henrique@email.fr", "+33071234567", Abonnement.VIP);
        //membreService.create("Rodrigues", "", "ENSTA", "henrique@email.fr", "+33071234567", Abonnement.VIP);

        Membre test = new Membre(5, "LASTNAME", "NAME", "ENSTA", "email@ensta-paris.fr", "+33077654321", Abonnement.PREMIUM);
        membreService.update(test);
        //Membre test_ = new Membre(5, "", "NAME", "ENSTA", "email@ensta-paris.fr", "+33077654321", Abonnement.PREMIUM);
        //membreService.update(test_);
        membreService.getList();

        membreService.delete(5);
        membreService.getList();

        membreService.count();
    }
    
    /**
     * Test LivreService
     * @throws ServiceException
     */
    public static void livreTest() throws ServiceException{
        LivreService livreService = LivreServiceImpl.getInstance();

        livreService.getList();
        livreService.getListDispo();
        livreService.getById(7);
        livreService.create("My Book", "My Autor", "123456");
        //livreService.create("", "My Autor", "123456");
        livreService.getList();

        Livre test = new Livre(7, "My Book 2", "My Autor 2", "123456");
        livreService.update(test);
        //Livre test_ = new Livre(7, "", "My Autor 2", "123456");
        //livreService.update(test_);
        livreService.getList();

        livreService.delete(7);
        livreService.getList();

        livreService.count();
    }


    /**
     * Test EmpruntService
     * @throws ServiceException
     */
    public static void empruntTest() throws ServiceException{
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();

        empruntService.getList();
        empruntService.getListCurrent();
        empruntService.getListCurrentByMembre(5);
        empruntService.getListCurrentByLivre(2);
        empruntService.getById(3);

        empruntService.create(3, 2, LocalDate.now());
        empruntService.getList();

        empruntService.returnBook(4);
        empruntService.getList();

        empruntService.count();

        empruntService.isLivreDispo(7);
        // empruntService.isEmpruntPossible(membre); // deja testé dans membreTest
    }
}