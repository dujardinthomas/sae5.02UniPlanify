    <h1><a href="inscription.jsp">Inscription</a></h1>

    <% String mess=request.getParameter("mess"); if (mess!=null) out.println("<h2>"+mess+"</h2>");

        String origine=(String)session.getAttribute("origine");
        if (origine==null) origine="" ;

        //a mettre dans toutes les servlets !
        //on ecrit que l'interieur de la balise body le header, footer c'est dans HeaderFooterFilter !!
        request.setAttribute("pageTitle", "Créer mon compte - UniPlanity");
        request.setAttribute("cheminAccueil", "");
        %>

        <form action="inscription" method="post">
            <div class="container">
                <label for="uname"><b>Nom</b></label>
                <input type="text" placeholder="Enter votre nom" name="nom" required>

                <label for="uname"><b>Prenom</b></label>
                <input type="text" placeholder="Entrer votre prénom" name="prenom" required>

                <label for="uname"><b>Mail (identifiant)</b></label>
                <input type="text" placeholder="Entrer votre mail" name="login" required>

                <label for="psw"><b>Mot de passe</b></label>
                <input type="password" placeholder="Entrer votre mot de passe" name="pwd" required>

                <input type="hidden" name="origine" value=<%=origine%> >

                <button type="submit">Créer mon compte</button>
            </div>

        </form>

        <h1><a href="login.jsp">Login</a></h1>
</body>
</html>

</html>