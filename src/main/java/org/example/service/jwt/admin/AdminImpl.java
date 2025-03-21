package org.example.service.jwt.admin;

import lombok.RequiredArgsConstructor;
import org.example.dto.Car;
import org.example.entity.CarEntity;
import org.example.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminImpl implements AdminService{

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    @Override


   public void addCar(Car car) {
        carRepository.save(modelMapper.map(car, CarEntity.class));


    }

    @Override
    public List<Car> getAll() {
        List<Car> carList=new ArrayList<>();
        List<CarEntity>all=carRepository.findAll();
        all.forEach(CarEntity->{
            carList.add(modelMapper.map(CarEntity, Car.class));
        });



        return carList;
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }
}
