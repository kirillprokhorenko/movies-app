<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Movies</title>
</head>
<body>
    <h1>Movies management</h1>
    <h2>
        <a href="new">Add new movie</a>
    </h2>

    <div>
        <table>
            <caption><h2>List of movies</h2></caption>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Director</th>
                <th>Year</th>
            </tr>

            <c:forEach var="movie" items="${listMovies}">
                <tr>
                    <td><c:out value="${movie.id}" /></td>
                    <td><c:out value="${movie.title}" /></td>
                    <td><c:out value="${movie.director}" /></td>
                    <td><c:out value="${movie.year}" /></td>
                    <td>
                        <a href="edit?id=<c:out value='${movie.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="delete?id=<c:out value='${movie.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>