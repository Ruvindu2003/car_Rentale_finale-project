package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.BookACar;
import org.example.dto.Car;
import org.example.service.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public  ResponseEntity<String> bookCar(@PathVariable Long id,@Valid @RequestBody BookACar bookACar){

            if (id == null || bookACar == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: Car ID or booking details are missing");
            }
            System.out.println(id+" "+bookACar);
            boolean isBooked = customerService.bookCar(id, bookACar);

            if (isBooked) {
                return ResponseEntity.ok("Car booked successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car booking failed. Please check the car ID or availability.");
            }
        }






}
