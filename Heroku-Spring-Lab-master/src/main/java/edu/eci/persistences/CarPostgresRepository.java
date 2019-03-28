package edu.eci.persistences;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.eci.models.Car;
import edu.eci.persistences.repositories.ICarRepository;

@Component
@Qualifier("CarPostgresRepository")
public class CarPostgresRepository implements ICarRepository {

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

	@Override
	public Car getCarByPlate(String plate) {
		System.out.println("by plate");
		String query = "SELECT * FROM cars where plate =(?);";
		Car car = new Car();

		try (Connection connection = connect()) {

			PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, plate);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				car.setBrand(rs.getString("brand"));
				car.setLicencePlate(rs.getString("plate"));

			}
			return car;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Car> findAll() {
		System.out.println("all");
		String query = "SELECT * FROM cars;";
		List<Car> cars = new ArrayList<Car>();

		try (Connection connection = connect()) {

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Car car = new Car();

				car.setBrand(rs.getString("brand"));
				
				car.setLicencePlate(rs.getString("plate"));

				
				cars.add(car);

			}
			return cars;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	
	}

	@Override
	public Car find(String plate) {
		System.out.println("by plate 2");
		String query = "SELECT * FROM cars where plate =(?);";
		Car car = new Car();

		try (Connection connection = connect()) {

			PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, plate);

			ResultSet rs = pstmt.executeQuery(query);
			while (rs.next()) {
				car.setBrand(rs.getString("brand"));
				car.setLicencePlate(rs.getString("plate"));

			}
			return car;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public String save(Car c) {
		System.out.println("save");
		
		String SQL = "INSERT INTO cars(plate,brand) " + "VALUES(?,?);";
		try (Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, c.getLicencePlate());
			pstmt.setString(2, c.getBrand());
			pstmt.execute();	
			
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
		return c.getLicencePlate();
	}

	@Override
	public void update(Car c) {
		System.out.println("update");
		String SQL = "UPDATE cars SET brand = (?) WHERE plate=(?);";
		try (Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, c.getBrand());
			pstmt.setString(2, c.getLicencePlate());

			pstmt.execute();
			// check the affected rows
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());

		}

	}


	@Override
	public void delete(Car c) {
		String query = "DELETE FROM cars WHERE plate =(?);";	
		System.out.println("borrar");

		try (Connection connection = connect()) {

			PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, c.getLicencePlate());
			pstmt.execute();
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

	}
	
	

	@Override
	public void remove(String plate) {
		// TODO Auto-generated method stub

	}

}
