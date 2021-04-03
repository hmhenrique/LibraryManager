package com.ensta.librarymanager.models;

/**
 * Membre
 */
public class Membre {

    private String nom, prenom, email, telephone, adresse;
    private Abonnement abonnement;
    private int id;

    /**
     * Default consctrutor
     */
    public Membre() {
		super();
	}

    /**
     * Constructor
     * @param nom
     * @param prenom
     * @param email
     * @param telephone
     * @param adresse
     * @param abonnement
     */
    public Membre(String nom, String prenom, String email, String telephone, String adresse, Abonnement abonnement){
        this();
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.abonnement = abonnement;
    }

    /**
     * Constructor with id
     * @param id
     * @param nom
     * @param prenom
     * @param email
     * @param telephone
     * @param adresse
     * @param abonnement
     */
    public Membre(int id, String nom, String prenom, String email, String telephone, String adresse, Abonnement abonnement){
        this(nom, prenom, email, telephone, adresse, abonnement);
        this.id = id;
    }
    
    
    /**
     * Getter member ID
     * @return id of a member
     */
    public int getId(){
         return this.id;
    }

    /**
     * Getter nom
     * @return nom
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * Getter prenom
     * @return prenom
     */
    public String getPrenom(){
        return this.prenom;
    }

    /**
     * Getter email
     * @return email
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Getter telephone
     * @return telephone
     */
    public String getTelephone(){
        return this.telephone;
    }

    /**
     * Getter adresse
     * @return adresse
     */
    public String getAdresse(){
        return this.adresse;
    }

    /**
     * Getter subscription type
     * @return abonnement
     */
    public Abonnement getAbonnement(){
        return this.abonnement;
    }

    /**
     * Setter nom
     * @param nom_
     */
    public void setNom(String nom_){
        this.nom = nom_;
    }

    /**
     * Setter prenom
     * @param prenom_
     */
    public void setPrenom(String prenom_){
        this.prenom = prenom_;
    }

    /**
     * Setter email
     * @param email_
     */
    public void setEmail(String email_){
        this.email = email_;
    }

    /**
     * Setter telephone
     * @param telephone_
     */
    public void setTelephone(String telephone_){
        this.telephone = telephone_;
    }

    /**
     * Setter adresse
     * @param adresse_
     */
    public void setAdresse(String adresse_){
        this.adresse = adresse_;
    }

    /**
     * Setter Subscription type
     * @param abonnement_
     */
    public void setAbonnement(Abonnement abonnement_){
        this.abonnement = abonnement_;
    }

    /**
     * update toString method to reference all class variables
     * @return the String
     */
    @Override
    public String toString(){
        return 
        "\nNom : " + this.nom +
        "\nPrenom : " + this.prenom +
        "\nAddresse : " + this.adresse +
        "\nEmail : " + this.email +
        "\nTelephone : " + this.telephone +
        "\nAbonnement : " + this.abonnement +
        "\n";
    }
}