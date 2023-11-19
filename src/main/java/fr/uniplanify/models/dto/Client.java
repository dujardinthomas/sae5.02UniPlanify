package fr.uniplanify.models.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idC;
    private String nomC;
    private String prenomC;
    private String mailC;
    private String password;
    private Boolean pro;

    
    public Client(int idC, String nomC, String prenomC, String mailC, String password, Boolean pro) {
        this.idC = idC;
        this.nomC = nomC;
        this.prenomC = prenomC;
        this.mailC = mailC;
        this.password = password;
        this.pro = pro;
    }

    public Client(String nomC, String prenomC, String mailC, String password, Boolean pro) {
        this.nomC = nomC;
        this.prenomC = prenomC;
        this.mailC = mailC;
        this.password = password;
        this.pro = pro;
    }

    public Client(){
        
    }


    public int getIdC() {
        return idC;
    }
    public void setIdC(int idC) {
        this.idC = idC;
    }
    public String getNomC() {
        return nomC;
    }
    public void setNomC(String nomC) {
        this.nomC = nomC;
    }
    public String getPrenomC() {
        return prenomC;
    }
    public void setPrenomC(String prenomC) {
        this.prenomC = prenomC;
    }
    public String getMailC() {
        return mailC;
    }
    public void setMailC(String mailC) {
        this.mailC = mailC;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPro() {
        return pro;
    }

    public void setPro(Boolean pro) {
        this.pro = pro;
    }

    @Override
    public String toString() {
        return "Client [idC=" + idC + ", nomC=" + nomC + ", prenomC=" + prenomC + ", mailC=" + mailC + ", password="
                + password + ", pro=" + pro + "]";
    }
    
    
}
