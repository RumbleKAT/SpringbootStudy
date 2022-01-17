package com.rumblekat.mreivew.repository;

import com.rumblekat.mreivew.entity.Movie;
import com.rumblekat.mreivew.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovie(Movie movie);
}
