package org.example.repository;

import org.example.entity.BookACarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookACarRepository extends JpaRepository<BookACarEntity,Long> {
}
