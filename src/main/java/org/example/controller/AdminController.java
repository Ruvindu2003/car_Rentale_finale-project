package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Car;
import org.example.service.jwt.admin.AdminService;

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

}
