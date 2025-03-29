package org.example.repository;

import org.example.dto.BookACar;
import org.example.entity.BookACarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookACarRepository extends JpaRepository<BookACarEntity,Long> {

    List<BookACarEntity> findByUserId(Long userid);
}
