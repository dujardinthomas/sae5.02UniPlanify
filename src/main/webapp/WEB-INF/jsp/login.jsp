<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - UniPlanify</title>
    <link rel="stylesheet" href="css/style.css">

</head>
<body>

 <h1><a href="login.jsp">Login</a></h1>

  <% String mess=request.getParameter("mess"); if (mess!=null) out.println("<h2>"+mess+"</h2>");

    String origine=(String)session.getAttribute("origine");
    if (origine==null) origine="" ;


    //a mettre dans toutes les servlets !
        //on ecrit que l'interieur de la balise body le header, footer c'est dans HeaderFooterFilter !!
        request.setAttribute("pageTitle", "Se connecter - UniPlanity");
        request.setAttribute("cheminAccueil", "");
    %>

    <form action="login" method="post">
      <div class="container">
        <label for="uname"><b>Username</b></label>
        <input type="text" placeholder="Enter Username" name="mail" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>

        <input type="hidden" name="origine" value=<%=origine%> >

        <button type="submit">Login</button>
      </div>

    </form>

    <h1><a href="inscription.jsp">Inscription</a></h1>
</body>
</html>    























 

</body>
</html>