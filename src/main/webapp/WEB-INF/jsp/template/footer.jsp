<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="css/footer.css">
<footer>
    
    <% 
        // Vérifier si la page actuelle est celle où vous souhaitez afficher le bouton de déconnexion
        String currentPage1 = request.getRequestURI();
        if (currentPage1.endsWith("contact.jsp")) { 
    %>

    <div class="footer-content">
        <div class="footer-section about">
            <h3>UniPlanify</h3>
            <p>Votre solution en ligne pour la gestion de vos rendez-vous. Planifiez, gérez et suivez vos rendez-vous en toute simplicité.</p>
        </div>

        <div class="footer-section links">
            <h4>Liens Utiles</h4>
            <ul>
                <li><a href="my">Mon Espace / Gérér mes RDV</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="contact">Contact</a></li>
            </ul>
        </div>

        <div class="footer-section social">
            <h4>Suivez-nous</h4>
            <ul class="social-icons">
                <li><a href="https://linkedin.com/in/thomas-dujardin"><img src="icon-linkedin.png" alt="Linkedin"></a></li>
                <li><a href="#"><img src="icon-twitter.png" alt="Twitter"></a></li>
                <li><a href="#"><img src="icon-instagram.png" alt="Instagram"></a></li>
            </ul>
        </div>
    </div>
    
    <div class="footer-bottom">
    <% 
        } 
            %>
        &copy; 2024 UniPlanify | Made by <a href="https://thomasdujardin.fr">Thomas DUJARDIN</a>❤️
    </div>
</footer>
