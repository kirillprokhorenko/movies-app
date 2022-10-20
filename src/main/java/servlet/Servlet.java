package servlet;

import dao.MovieDao;
import entity.Movie;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;

public class Servlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private MovieDao movieDao;

    public void init() {
        String jdbcUrl = getServletContext().getInitParameter("jdbcUrl");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        movieDao = new MovieDao(jdbcUrl, jdbcUsername, jdbcPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new" -> showNewForm(request, response);
                case "/insert" -> insertMovie(request, response);
                case "/delete" -> deleteMovie(request, response);
                case "/edit" -> showEditForm(request, response);
                case "/update" -> updateMovie(request, response);
                default -> listMovies(request, response);
            }
        } catch (SQLException exception) {
            throw new ServletException(exception);
        }
    }

    private void listMovies(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Movie> movies = movieDao.listAllMovies();
        request.setAttribute("listMovies", movies);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/movies.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/movieForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Movie existingMovie = movieDao.getMovie(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/movieForm.jsp");
        request.setAttribute("movie", existingMovie);
        dispatcher.forward(request, response);
    }

    private void insertMovie(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String title = request.getParameter("title");
        String director = request.getParameter("director");
        int year = Integer.parseInt(request.getParameter("year"));

        Movie movie = new Movie(title, director, year);
        movieDao.insertMovie(movie);
        response.sendRedirect("movies");
    }
    private void updateMovie(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String title = request.getParameter("title");
        String director = request.getParameter("director");
        int year = Integer.parseInt(request.getParameter("year"));

        Movie movie = new Movie(id, title, director, year);
        movieDao.updateMovie(movie);
        response.sendRedirect("movies");
    }

    private void deleteMovie(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));

        Movie movie = movieDao.getMovie(id);
        movieDao.deleteMovie(movie);

        response.sendRedirect("movies");
    }
}
