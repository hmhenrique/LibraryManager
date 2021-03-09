package com.ensta.librarymanager.models;

/**
 * Books
 */
public class Livre {

    private String titre, auteur, isbn;


    /**
     * Constructor
     * @param titre
     * @param auteur
     * @param isbn
     */
    public Livre(String titre, String auteur, String isbn){
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
    }

    /**
     * Default Constructor
     */
    public Livre(){}


    /**
     * Getter title
     * @return titre
     */
    public String getTitre(){
        return this.titre;
    }

    /**
     * Getter author
     * @return auteur
     */
    public String getAuteur(){
        return this.auteur;
    }

    /**
     * Getter isbn
     * @return isbn
     */
    public String getISBN(){
        return this.isbn;
    }

    /**
     * Setter title
     * @param titre_
     */
    public void setTitre(String titre_){
        this.titre = titre_;
    }

    /**
     * Setter Author
     * @param auteur_
     */
    public void setAuteur(String auteur_){
        this.auteur = auteur_;
    }

    /**
     * Setter isbn
     * @param isbn_
     */
    public void setISBN(String isbn_){
        this.isbn = isbn_;
    }

    /**
     * update toString method to reference all class variables
     */
    @Override
    public String toString(){
        return 
        "\nTitre : " + this.titre +
        "\nAuteur : " + this.auteur +
        "\nISBN : " + this.isbn +
        "\n";
    }
}