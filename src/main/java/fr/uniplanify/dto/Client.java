package fr.uniplanify.dto;

public class Client {
    private int idC;
    private String nomC;
    private String prenomC;
    private String mailC;
    private String password;

    
    public Client(int idC, String nomC, String prenomC, String mailC, String password) {
        this.idC = idC;
        this.nomC = nomC;
        this.prenomC = prenomC;
        this.mailC = mailC;
        this.password = password;
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

    
}