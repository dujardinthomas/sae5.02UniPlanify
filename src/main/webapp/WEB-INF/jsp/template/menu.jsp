<header>
    <nav class="nav-menu">
        <ul>
            <li><div class="logo"><a href="/"><img src="../img/logo.png" alt="Logo UniPlanify"></a></div></li>
            <li><a href="../my">Mon espace</a></li>
            <li><a href="">Calendrier</a>
                <ul class="submenu">
                    <li><a href="/">Semaine</a></li>
                    <li><a href="/monthly">Mensuel</a></li>
                </ul>
            </li>
            <%-- <li><a href="/week">Semaine</a></li> --%>
            <li><a href="../contact">Contact</a></li>
        <% 
        // Vérifier si la page actuelle est celle où vous souhaitez afficher le bouton de déconnexion
        String currentPage = request.getRequestURI();
        if (currentPage.endsWith("pro.jsp") || currentPage.endsWith("perso.jsp")) { 
        %>
            <li><a href="logout">Deconnexion</a></li>
        <% 
        } 
            %>
        </ul>
    </nav>
</header>