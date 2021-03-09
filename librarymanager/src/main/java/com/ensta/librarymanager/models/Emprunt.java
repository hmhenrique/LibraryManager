package com.ensta.librarymanager.models;

import java.time.LocalDate;
/**
 * Emprunt
 */
public class Emprunt {

    private Membre eMembre;
    private Livre eLivre;
    private LocalDate dateEmprunt, dateRetour;

    /**
     * Constructor
     * @param eMembre
     * @param eLivre
     * @param dateEmprunt
     * @param dateRetour
     */
    public Emprunt(Membre eMembre, Livre eLivre, LocalDate dateEmprunt, LocalDate dateRetour){
        this.eMembre = eMembre;
        this.eLivre = eLivre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }
    
    /**
     * Default constructor
     */
    public Emprunt(){}

    /**
     * Getter member
     * @return eMembre
     */
    public Membre getMembre(){
        return this.eMembre;
    }

    /**
     * Getter book
     * @return eLivre
     */
    public Livre getLivre(){
        return this.eLivre;
    }

    /**
     * Getter starts date
     * @return dateEmprunt
     */
    public LocalDate getDateEmprunt(){
        return this.dateEmprunt;
    }

    /**
     * Getter return date
     * @return dateRetour
     */
    public LocalDate getDateRetour(){
        return this.dateRetour;
    }


    /**
     * Setter member
     * @param eMembre_
     */
    public void setMembre(Membre eMembre_){
        this.eMembre = eMembre_;
    }

    /**
     * Setter book
     * @param eLivre_
     */
    public void setLivre(Livre eLivre_){
        this.eLivre = eLivre_;
    }

    /**
     * Setter starts date
     * @param dateEmprunt_
     */
    public void setDateEmprunt(LocalDate dateEmprunt_){
        this.dateEmprunt = dateEmprunt_;
    }

    /**
     * Setter return date
     * @param dateRetour_
     */
    public void setDateRetour(LocalDate dateRetour_){
        this.dateRetour = dateRetour_;
    }

    /**
     * update toString method to reference all class variables
     */
    @Override
    public String toString(){
        return
            "\nMembre : " + this.eMembre.getNom() +
            "\nLivre : " + this.eLivre.getTitre() +
            "\nDateEmprunt : " + this.dateEmprunt +
            "\nDateRetour : " + this.dateRetour +
            "\n";
    }

}