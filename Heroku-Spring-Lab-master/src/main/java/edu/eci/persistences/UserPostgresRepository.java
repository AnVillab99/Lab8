package edu.eci.persistences;
/*
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;*/
import edu.eci.models.User;
import edu.eci.persistences.repositories.IUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Qualifier("UserPostgresRepository")
public class UserPostgresRepository implements IUserRepository {

	private final String url = "jdbc:postgresql://ec2-75-101-133-29.compute-1.amazonaws.com:5432/d7d3tqe5mb1jkc";
	private final String user = "jorgqaevrolidy";
	private final String password = "f8f673b4e4129b2ebc63e13d188486ec9bd7d4fad09e57a51cf4be137f87233c";

	public Connection connect() {
		Connection conn = null;
		try {

			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}


    /*@Autowired
    private DataSource dataSource;*/

    @Override
    public User getUserByUserName(String userName) {
        return null;
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        List<User> users=new ArrayList<>();

        try(
        	Connection connection = connect()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                User user = new User();
                user.setName(rs.getString("name"));
                user.setId(UUID.fromString(rs.getString("id")));
                users.add(user);
            }
            return users;
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(UUID id) {
        return null;
    }

    @Override
    public UUID save(User entity) {
    	long id = 0;
		String SQL = "INSERT INTO users(name,id) " + "VALUES(?,?)";
		try (Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, entity.getName());
			pstmt.setString(2, entity.getId().toString());
			

			int affectedRows = pstmt.executeUpdate();
			// check the affected rows
			System.out.println(findAll());
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		return entity.getId();

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User o) {

    }

    @Override
    public void remove(Long id) {

    }
/*
    @Bean
    public DataSource dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        }
    }*/
}
