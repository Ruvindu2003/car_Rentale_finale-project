package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.BookACar;
import org.example.dto.Car;
import org.example.dto.SearchCar;
import org.example.entity.CarEntity;
import org.example.service.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/customer")

public class CustomerController {
    private  final CustomerService customerService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Car>> getAllCars(){
        return ResponseEntity.ok(customerService.getAll()) ;

    }

    @GetMapping("/Search-By-Id/{id}")
    public ResponseEntity<Car> searchByID(@PathVariable Long id){
        return ResponseEntity.ok(customerService.SearchByID(id));

    }
    @PostMapping("/Book-Car/{id}")
    public  ResponseEntity<Map<String, String>> bookCar(@PathVariable Long id, @Valid @RequestBody BookACar bookACar){

            if (id == null || bookACar == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("massage","Invalid request: Car ID or booking details are missing"));
            }
            boolean isBooked = customerService.bookCar(id, bookACar);

            if (isBooked) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Car booked successfully!"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("massage","Car booking failed. Please check the car ID or availability."));
            }
        }
        @GetMapping("/car/booking/{userId}")
        public ResponseEntity<?>getBooking(@PathVariable Long userId){
        return ResponseEntity.ok(customerService.getAllBokingsInUserId(userId));

    }

        @PostMapping("/search/car")
       public List<Car>SearchCar(@RequestBody SearchCar searchCar){
        List<Car> carList=customerService.SearchCar(searchCar);
        return  carList;
        }










}
