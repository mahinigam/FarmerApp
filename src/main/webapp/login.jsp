<%-- login.jsp --%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<h2>Login</h2>

<%-- Display any error messages --%>
<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>

<form action="login" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required /><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required /><br>

    <button type="submit">Login</button>
</form>

</body>
</html>
