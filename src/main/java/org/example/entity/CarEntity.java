package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.dto.Car;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Car")

public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private String tramsmisson;
    private String brand;
    private String type;
    private String  modelDate;
    private String description;
    private String price;
    private String image;

    private Car convertToCar(CarEntity entity) {
        Car car = new Car();
        car.setBrand(entity.getBrand());
        car.setType(entity.getType());
        car.setTramsmisson(entity.getTramsmisson());
        car.setColor(entity.getColor());
        // Set other fields as needed
        return car;
    }

}
