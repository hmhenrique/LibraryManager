package com.ensta.librarymanager.test;

import java.time.LocalDate;

import com.ensta.librarymanager.models.*;

public class ModeleTest{

    public static void main(String args[]){
        Livre livre1 = new Livre("Book Title", "Book Author", "Book ISBN");
        Membre membre1 = new Membre("Member last name", "Member name", "member@email.com", "0701020304","adress", Abonnement.BASIC);
        Emprunt emprunt1 = new Emprunt(membre1, livre1, LocalDate.of(2021,3,9), LocalDate.of(2021,4,9));


        System.out.println(livre1);
        System.out.println(membre1);
        System.out.println(emprunt1);
    }
}