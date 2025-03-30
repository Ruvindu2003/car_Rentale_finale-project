package org.example.service.jwt.admin;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookACar;
import org.example.dto.Car;
import org.example.dto.SearchCar;
import org.example.entity.BookACarEntity;
import org.example.entity.CarEntity;
import org.example.enums.BookCarStatus;
import org.example.repository.BookACarRepository;
import org.example.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AdminImpl implements AdminService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final BookACarRepository bookACarRepository;

    @Override


    public void addCar(Car car) {
        carRepository.save(modelMapper.map(car, CarEntity.class));


    }

    @Override
    public List<Car> getAll() {
        List<Car> carList = new ArrayList<>();
        List<CarEntity> all = carRepository.findAll();
        all.forEach(CarEntity -> {
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
    public boolean UpdateByCar(Car car, Long id) {
        if (car == null || id == null) {
            return false;

        }
        Optional<CarEntity> opitonalexitingCar = carRepository.findById(id);
        if (opitonalexitingCar.isPresent()) {
            CarEntity exsitingCar = opitonalexitingCar.get();
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
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<BookACar> getBooking() {

        return bookACarRepository.findAll().stream().map(BookACarEntity::getBookingCars).collect(Collectors.toList());
    }

    @Override
    public boolean changeBookingStatus(Long bookingId, String status) {
        Optional<BookACarEntity> bookACarEntity = bookACarRepository.findById(bookingId);
        if (bookACarEntity.isPresent()) {
            BookACarEntity bookACar = bookACarEntity.get();
            if (Objects.equals(status, "APPROVED")) {
                bookACar.setBookCarStatus(BookCarStatus.APPROVED);
            } else {
                bookACar.setBookCarStatus(BookCarStatus.REJECT);
            }
            bookACarRepository.save(bookACar);
            return true;
        }
        return false;
    }
@Override
    public List<Car> searchCar(SearchCar searchCar) {
        List<CarEntity> carEntities = carRepository.findAll().stream()
                .filter(car -> searchCar.getBrand() == null ||
                        car.getBrand().toLowerCase().contains(searchCar.getBrand().toLowerCase()))
                .filter(car -> searchCar.getType() == null ||
                        car.getType().toLowerCase().contains(searchCar.getType().toLowerCase()))
                .filter(car -> searchCar.getTramsmisson() == null ||
                        car.getTramsmisson().toLowerCase().contains(searchCar.getTramsmisson().toLowerCase()))
                .filter(car -> searchCar.getColor() == null ||
                        car.getColor().toLowerCase().contains(searchCar.getColor().toLowerCase()))
                .collect(Collectors.toList());

        return carEntities.stream()
                .map(entity -> modelMapper.map(entity, Car.class))
                .collect(Collectors.toList());
    }


    }


