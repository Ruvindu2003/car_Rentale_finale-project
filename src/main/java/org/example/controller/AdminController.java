package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Car;
import org.example.dto.SearchCar;
import org.example.service.jwt.admin.AdminService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/car")
    public void addCar(@RequestBody Car car) {

        adminService.addCar(car);
    }
    @GetMapping("/getAll")
    public List<Car> getAll(){

        return adminService.getAll();
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable Long id){
        adminService.deleteById(id);

    }

    @GetMapping("/Search-By-Id/{id}")
    public Car searchByID(@PathVariable Long id){

        return   adminService.SearchByID(id);
    }

    @PutMapping("/Update-By-Car/{id}")
    public boolean UpdateByCar(@RequestBody Car car,@PathVariable Long id){
       return adminService.UpdateByCar(car,id);

    }
    @GetMapping("car/booking")
   public ResponseEntity<?>getBooking(){
        return ResponseEntity.ok(adminService.getBooking());
    }
    @GetMapping("car/Booking/{bookId}/{bookCarStatus}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookId,@PathVariable String bookCarStatus){
        boolean sucses=adminService.changeBookingStatus(bookId,bookCarStatus);
        if (sucses)
         return    ResponseEntity.ok().build();

        return  ResponseEntity.notFound().build();

        }

        @PostMapping("search/car")
    public ResponseEntity<?> searcByCar(SearchCar searchCar){
        return  ResponseEntity.ok(adminService.searchCar(searchCar));

        }



    }



