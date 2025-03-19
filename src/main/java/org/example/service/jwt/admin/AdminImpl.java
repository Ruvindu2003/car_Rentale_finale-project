package org.example.service.jwt.admin;

import lombok.RequiredArgsConstructor;
import org.example.dto.Car;
import org.example.entity.CarEntity;
import org.example.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AdminImpl implements AdminService{

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    @Override


   public void addCar(Car car) {
        carRepository.save(modelMapper.map(car, CarEntity.class));


    }
}
