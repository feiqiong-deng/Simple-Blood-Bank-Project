<%-- 
    Document   : showTable-CreateDonationRecord
    Created on : Apr. 10, 2021, 2:34:20 p.m.
    Author     : Feiqiong Deng
--%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:out value="${title}"/></title>
        <link rel="stylesheet" type="text/css" href="style/tablestyle.css">
    </head>
    <body>
        <div style="text-align: center;">
            <div style="display: inline-block; text-align: left;">
                <form method="post">
                     <label for="person_id">Person ID:</label><br>
                     <input type="text" name="person_id"><br><br>
                     <label for="donation_id">Donation ID:</label><br>
                     <input type="text" name="donation_id"><br><br>
                     <label for="tested">Tested:</label><br>
                     <select name="tested">
                        <option value="True">True</option>
                        <option value="False">False</option>
                     </select>
                     <br><br>
                     <label for="administrator">Administrator:</label><br>
                     <input type="text" name="administrator"><br><br>
                     <label for="hospital">Hospital:</label><br>
                     <input type="text" name="hospital"><br><br>
                     <label for="created">Date Created:</label><br>
                     <input type="datetime-local" name="created" step="1">
                     <br><br>
                     <input type="submit" name="view" value="Add and View">
                     <input type="submit" name="add" value="Add">
                </form>
                <font color="red">${error}</font><br>  
                <pre>${path}</br>${request}</pre>  
            </div>
         </div>                    
    </body>
</html>
