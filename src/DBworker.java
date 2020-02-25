import java.sql.*;
import java.text.spi.DateFormatSymbolsProvider;
import java.time.LocalDate;

import static java.time.chrono.JapaneseEra.values;

public class DBworker {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "farmuses";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection( url, user, password );
            System.out.println( "Connected to the PostgreSQL server successfully." );
        } catch (SQLException e) {
            System.out.println( e.getMessage() );
        }
        return conn;

    }

    public void addNews(String news_title, String news_text) {
        String SQL = "insert into news1(news_title,news_text,print_time) values (?,?,?)";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement( SQL )) {
            preparedStatement.setString( 2, news_title );
            preparedStatement.setString( 3, news_text );
            Timestamp print_time = new Timestamp( System.currentTimeMillis() );
            preparedStatement.setTimestamp( 4, print_time );
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println( e.getMessage() );
        }
    }

    public void showNews(String news_title, String news_text) {
        String SQL = " select news_title, news_text from news1";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery( SQL )) {
            while (rs.next()) {
                System.out.println( rs.getString( 2 ) + ":" + rs.getString( 3 ) );
            }
        } catch (SQLException e) {
            System.out.println( e.getMessage() );
        }
    }

    public void deleteNews(int ID) {
        String SQL = "delete from news1 where id=?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement( SQL )) {
            preparedStatement.setInt( 1, id );
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println( e.getMessage() );
        }
    }

    public void UpdateNews(String news_title, String news_text) {
        String SQL = " update news1 set news_title=?, news_text=? where id=?";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement( SQL )) {
            preparedStatement.setString( 2, news_title );
            preparedStatement.setString( 3, news_text );
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println( e.getMessage() );
        }
    }
}
