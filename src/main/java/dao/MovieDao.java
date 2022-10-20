package dao;

import entity.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDao {
    private final String jdbcUrl;
    private final String jdbcUsername;
    private final String jdbcPassword;
    private Connection jdbcConnection;

    public MovieDao(String jdbcUrl,
                    String jdbcUsername,
                    String jdbcPassword) {
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException exception) {
                throw new SQLException(exception);
            }
        }
        jdbcConnection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public void insertMovie(Movie movie) throws SQLException {
        String sql = "INSERT INTO movie (title, director, year) VALUES (?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, movie.getTitle());
        statement.setString(2, movie.getDirector());
        statement.setInt(3, movie.getYear());

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public List<Movie> listAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie";
        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            String director = resultSet.getString("director");
            int year = resultSet.getInt("year");

            Movie movie = new Movie(id, title, director, year);
            movies.add(movie);
        }

        resultSet.close();
        statement.close();
        disconnect();
        return movies;
    }

    public void deleteMovie(Movie movie) throws SQLException {
        String sql = "DELETE FROM movie WHERE id=?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, movie.getId());

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public void updateMovie(Movie movie) throws SQLException {
        String sql = "UPDATE movie SET title=?, director=?, year=? WHERE id=?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, movie.getTitle());
        statement.setString(2, movie.getDirector());
        statement.setInt(3, movie.getYear());
        statement.setLong(4, movie.getId());

        statement.executeUpdate();
        statement.close();
        disconnect();
    }

    public Movie getMovie(long id) throws SQLException {
	    // String surprise = null; // haha
        Movie movie = null;
        String sql = "SELECT * FROM movie WHERE id=?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String director = resultSet.getString("director");
            int year = resultSet.getInt("year");

            movie = new Movie(id, title, director, year);
        }

        resultSet.close();
        statement.close();
        disconnect();
        return movie;
    }
}
