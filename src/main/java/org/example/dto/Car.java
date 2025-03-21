package org.example.dto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class Car {
    private Long id;
    private String name;
    private String color;
    private String tramsmisson;
    private String brand;
    private String type;
    private String modelDate;
    private String description;
    private String price;
    private String image;


}
