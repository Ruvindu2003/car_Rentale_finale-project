package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.dto.BookACar;
import org.example.enums.BookCarStatus;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Time;
import java.util.Date;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BookInCar")
public class BookACarEntity {
    @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long amount;
    @Enumerated(EnumType.STRING)
    private BookCarStatus bookCarStatus;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "userid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "carid",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CarEntity car;




    public BookACar getBookingCars(){
      BookACar bookACar=new BookACar();
     bookACar.setBookId(bookId);
     bookACar.setFromDate(fromDate);
     bookACar.setToDate(toDate);
     bookACar.setDays(days);
     bookACar.setAmount(amount);
     bookACar.setBookCarStatus(bookCarStatus);
     bookACar.setEmail(user.getEmail());
     bookACar.setUserName(user.getName());

     return bookACar;
    }



}
