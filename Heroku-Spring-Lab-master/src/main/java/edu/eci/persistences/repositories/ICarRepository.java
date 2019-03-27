package edu.eci.persistences.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import edu.eci.models.Car;

@Repository
public interface ICarRepository extends DAOC {

	Car getCarByPlate(String userName);

	public List<Car> findAll();

	public Car find(String plate);

	public String save(Car c);

	public void update(Car c);

	public void remove(String plate);

	public void delete(Car c);
}
