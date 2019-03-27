package edu.eci.services.contracts;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.eci.models.Car;
import edu.eci.models.User;

@Service
public interface ICarServices {


    List<Car> list();
    Car create(Car car);
    Car get(String plate);
	void updateCar(Car c);
	void delete(String plate);


}
