<%-- 
    Document   : test_upload
    Created on : Jun 14, 2024, 11:54:16 AM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="action">
            <label for="song">Upload a music file</label>
            <br />
            <input type="file" name="song" id="song" onchange="onMusicChanged(this)" />
        </form>
    </body>
</html>
