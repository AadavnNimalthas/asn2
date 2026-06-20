package com.example.staffrating.service;

import com.example.staffrating.model.StaffRating;
import com.example.staffrating.repository.StaffRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffRatingService {

    private final StaffRatingRepository repository;

    @Autowired
    public StaffRatingService(StaffRatingRepository repository) {
        this.repository = repository;
    }

    public List<StaffRating> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Optional<StaffRating> findById(Long id) {
        return repository.findById(id);
    }

    public StaffRating save(StaffRating staffRating) {
        return repository.save(staffRating);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public boolean emailExists(String email) {
        return repository.existsByEmail(email);
    }
}
