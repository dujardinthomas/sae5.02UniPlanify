<!DOCTYPE html>
<html lang="fr">

<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="style/style.css">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
</head>

<body>
  <h1><a href="login.jsp">Login</a></h1>

  <% String mess=request.getParameter("mess"); if (mess!=null) out.println("<h2>"+mess+"</h2>");

    String origine=(String)session.getAttribute("origine");
    if (origine==null) origine="private/welcome1" ;
    %>

    <form action="verif" method="post">
      <div class="container">
        <label for="uname"><b>Username</b></label>
        <input type="text" placeholder="Enter Username" name="login" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="pwd" required>

        <input type="hidden" name="origine" value=<%=origine%> >

        <button type="submit">Login</button>
      </div>

    </form>

    <h1><a href="inscription.jsp">Inscription</a></h1>

</body>

</html>