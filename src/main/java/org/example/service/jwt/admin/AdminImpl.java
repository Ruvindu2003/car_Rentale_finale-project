package org.example.service.jwt.admin;

import lombok.RequiredArgsConstructor;
import org.example.dto.Car;
import org.example.entity.CarEntity;
import org.example.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Car SearchByID(Long id) {
        return modelMapper.map(carRepository.findById(id), Car.class);
    }

    @Override
    public boolean UpdateByCar(Car car,Long id) {
       if (car==null||id==null){
           return  false;

       }
       Optional<CarEntity> opitonalexitingCar=carRepository.findById(id);
       if (opitonalexitingCar.isPresent()){
           CarEntity exsitingCar=opitonalexitingCar.get();
           exsitingCar.setName(car.getName());
           exsitingCar.setDescription(car.getDescription());
           exsitingCar.setType(car.getType());
           exsitingCar.setBrand(car.getBrand());
           exsitingCar.setImage(car.getImage());
           exsitingCar.setTramsmisson(car.getTramsmisson());
           exsitingCar.setColor(car.getColor());
           exsitingCar.setModelDate(car.getModelDate());
           exsitingCar.setPrice(car.getPrice());

           carRepository.save(exsitingCar);
           return  true;
       }else {
           return false;
       }

    }
}
