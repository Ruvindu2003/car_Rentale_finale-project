package org.example.service.customer;

import org.example.dto.BookACar;
import org.example.dto.Car;
import org.example.entity.BookACarEntity;

import java.util.List;

public interface CustomerService {

    List<Car> getAll();
    Car SearchByID(Long id);
    boolean bookCar(Long id, BookACar bookCarACar);
    List<BookACar> getAllBokingsInUserId(Long userId);



}
