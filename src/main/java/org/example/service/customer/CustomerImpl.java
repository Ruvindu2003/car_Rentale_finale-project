package org.example.service.customer;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookACar;
import org.example.dto.Car;
import org.example.entity.BookACarEntity;
import org.example.entity.CarEntity;
import org.example.entity.UserEntity;
import org.example.enums.BookCarStatus;
import org.example.repository.BookACarRepository;
import org.example.repository.CarRepository;
import org.example.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerImpl implements CustomerService{
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BookACarRepository bookACarRepository;


    @Override
    public List<Car> getAll() {
        List<Car>carList=new ArrayList<>();
        List<CarEntity>carEntities=carRepository.findAll();
        carEntities.forEach(CarEntity->{
            carList.add(modelMapper.map(CarEntity, Car.class));
        });
            return  carList;
    }

    @Override
    public Car SearchByID(Long id) {
      CarEntity carEntity=carRepository.findById(id).orElseThrow(()->new RuntimeException("Car Not find"+id));
            return modelMapper.map(carEntity, Car.class);
    }

    @Override
    public boolean bookCar(Long id, BookACar bookCarACar) {
        Optional<UserEntity> optionalUserEntity=(userRepository.findById(bookCarACar.getUserId()));
        Optional<CarEntity>optionalCarEntity=carRepository.findById(id);
        if (optionalCarEntity.isPresent() && optionalCarEntity.isPresent()){
            BookACarEntity bookaACarEntity=new BookACarEntity();
            long difMilliSecond=bookCarACar.getToDate().getTime() -bookCarACar.getFromDate().getTime();


            long days = difMilliSecond / 86400000;


            bookaACarEntity.setDays(days);
            bookaACarEntity.setUser(optionalUserEntity.get());
            bookaACarEntity.setCar(optionalCarEntity.get());
            bookaACarEntity.setAmount(Integer.parseInt(optionalCarEntity.get().getPrice()) * days);
            bookaACarEntity.setFromDate(bookCarACar.getFromDate());
            bookaACarEntity.setToDate(bookCarACar.getToDate());
            bookaACarEntity.setBookCarStatus(BookCarStatus.PENDING);
            bookACarRepository.save(bookaACarEntity);
            return true;
        }
            return  false;
    }

    @Override
    public List<BookACar> getAllBokingsInUserId(Long userId) {

        return bookACarRepository.findByUserId(userId).stream().map(BookACarEntity::getBookingCars).collect(Collectors.toList());
    }


}
