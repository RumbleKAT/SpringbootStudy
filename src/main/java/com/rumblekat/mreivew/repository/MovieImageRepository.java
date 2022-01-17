package com.rumblekat.mreivew.repository;

import com.rumblekat.mreivew.entity.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
}
