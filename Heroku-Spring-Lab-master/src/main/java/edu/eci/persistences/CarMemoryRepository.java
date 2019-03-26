package edu.eci.persistences;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.eci.models.Car;
import edu.eci.models.User;
import edu.eci.persistences.repositories.ICarRepository;
@Component
@Qualifier("CarMemoryRepository")

public class CarMemoryRepository implements ICarRepository {
	
	public static List<Car> carsContainer;
	
	public static List<Car> getContainer(){
        if(CarMemoryRepository.carsContainer == null)
        	CarMemoryRepository.carsContainer = new ArrayList<>();
        return CarMemoryRepository.carsContainer;
    }
	@Override
	public Car getCarByPlate(String plate) {
		return CarMemoryRepository.getContainer()
                .stream()
                .filter(u -> plate.equals(u.getLicencePlate()))
                .findFirst()
                .get();
	}

	@Override
	public List<Car> findAll() {
		 return CarMemoryRepository.getContainer();
	}

	@Override
	public Car find(String plate) {
		 Optional<Car> answer = CarMemoryRepository.getContainer()
	                .stream()
	                .filter(u -> plate.equals(u.getLicencePlate()))
	                .findFirst();
	        return answer.isPresent() ? answer.get() : null;
	}

	@Override
	public String save(Car c) {
		CarMemoryRepository.getContainer().add(c);
	        return c.getLicencePlate();
	}

	@Override
	public void update(Car c) {
		CarMemoryRepository.carsContainer = CarMemoryRepository.getContainer()
	                .stream()
	                .map(u -> u.getLicencePlate().equals(c.getLicencePlate()) ? c : u)
	                .collect(toList());
		
	}

	@Override
	public void remove(String plate) {
		 UserMemoryRepository.usersContainer = UserMemoryRepository.getContainer()
	                .stream()
	                .filter(u -> !u.getId().equals(plate))
	                .collect(toList());
		
	}

	@Override
	public void delete(Car c) {
		 UserMemoryRepository.usersContainer = UserMemoryRepository.getContainer()
	                .stream()
	                .filter(u -> !u.getId().equals(c.getLicencePlate()))
	                .collect(toList());
		
	}

}
