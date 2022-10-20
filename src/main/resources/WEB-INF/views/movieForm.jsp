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
        <a href="/movies">All movies</a>
    </h2>

    <div>
        <c:if test="${movie != null}">
        <form action="update" method="post">
            </c:if>
            <c:if test="${movie == null}">
            <form action="insert" method="post">
                </c:if>
                <table>
                    <caption>
                        <h2>
                            <c:if test="${movie != null}">
                                Edit movie
                            </c:if>
                            <c:if test="${movie == null}">
                                Add new movie
                            </c:if>
                        </h2>
                    </caption>
                    <c:if test="${movie != null}">
                        <input type="hidden" name="id" value="<c:out value='${movie.id}' />" />
                    </c:if>
                    <tr>
                        <th>Title: </th>
                        <td>
                            <input type="text" name="title" size="45"
                                   value="<c:out value='${movie.title}' />"
                            />
                        </td>
                    </tr>
                    <tr>
                        <th>Director: </th>
                        <td>
                            <input type="text" name="director" size="45"
                                   value="<c:out value='${movie.director}' />"
                            />
                        </td>
                    </tr>
                    <tr>
                        <th>Year: </th>
                        <td>
                            <input type="text" name="year" size="4"
                                   value="<c:out value='${movie.year}' />"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value="Save" />
                        </td>
                    </tr>
                </table>
            </form>
    </div>
</body>
</html>