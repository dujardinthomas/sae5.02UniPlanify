package fr.sae502.uniplanify.controllers;
import java.beans.JavaBean;
import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.sae502.uniplanify.login.SessionBean;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.UtilisateurRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/* The Java file upload Servlet example */

@WebServlet(name = "FileUploadServlet", urlPatterns = { "/fileuploadservlet" })
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 10,      // 10 MB
  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
@Component

public class FileUploadServlet extends HttpServlet {

  @Autowired
  private SessionBean sessionBean;

  @Autowired
  private UtilisateurRepository utilisateurRepository;

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    /* Receive file uploaded to the Servlet from the HTML5 form */
    Part filePart = request.getPart("file");
    String fileName = filePart.getSubmittedFileName();
    String extension = fileName.substring(fileName.lastIndexOf("."));
    System.out.println("on est dans la servlet fileUploadServlet...");
    String emplacement = "/home/infoetu/thomas.dujardin2.etu/Documents/maven/sae5.02UniPlanify/src/main/resources/static/img/profils/";
    for (Part part : request.getParts()) {
      Utilisateur user = sessionBean.getUtilisateur();
      String newName = "profil_" + user.getId() + extension;

      part.write(emplacement + newName);
      System.out.println("on a écrit le fichier " + fileName + " dans " + emplacement + newName );
      enregistrePhoto("../img/profils/" + newName);
    }
    response.getWriter().print("The file uploaded sucessfully.");
    System.out.println("Tout s'est bien passé !");
  }

  private void enregistrePhoto(String string) {
    Utilisateur user = utilisateurRepository.findById(sessionBean.getUtilisateur().getId()).get();
    user.setUrlphoto(string);
    utilisateurRepository.save(user);
    sessionBean.setUtilisateur(user);
  }

}