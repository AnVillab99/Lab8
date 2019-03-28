package edu.eci.models;

public class Car {

    private String licencePlate;
    private String brand;
    
    
    public Car() {};
    
    public Car(String plate, String brands) {
    	plate=licencePlate;
    	brand=brands;
    }
	public String getLicencePlate() {
		return licencePlate;
	}
	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}

}
