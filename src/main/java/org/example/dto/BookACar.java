package org.example.dto;

import lombok.*;
import org.example.enums.BookCarStatus;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class BookACar {
    private Long bookId;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long amount;
    private BookCarStatus bookCarStatus;
    private Long userId;
    private String email;
    private String userName;


}
