package org.example.service.jwt.admin;

import org.example.dto.BookACar;
import org.example.dto.Car;
import org.example.dto.CarList;
import org.example.dto.SearchCar;
import org.example.entity.CarEntity;

import java.util.List;

public interface AdminService {

    void addCar(Car car);
    List<Car> getAll();

    void deleteById(Long id);
    Car SearchByID(Long id);
   boolean  UpdateByCar(Car car,Long id);
    List<BookACar> getBooking();
    boolean changeBookingStatus(Long bookingId,String status);
    CarList searchCar(SearchCar searchCar);

}
