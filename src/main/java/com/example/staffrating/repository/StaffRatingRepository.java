package com.example.staffrating.repository;

import com.example.staffrating.model.StaffRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRatingRepository extends JpaRepository<StaffRating, Long> {
    boolean existsByEmail(String email);
}
