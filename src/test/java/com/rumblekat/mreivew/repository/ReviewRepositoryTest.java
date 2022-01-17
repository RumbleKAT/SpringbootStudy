package com.rumblekat.mreivew.repository;

import com.rumblekat.mreivew.entity.Member;
import com.rumblekat.mreivew.entity.Movie;
import com.rumblekat.mreivew.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews(){
        //200개의 리뷰를 등록한다.
        IntStream.rangeClosed(1,200).forEach(i->{
            //movie no
            Long mno = (long)(Math.random()*100)+1;

            //reviewer no
            Long mid = ((long)(Math.random()*100)+1);
            Member member = Member.builder().mid(mid).build();

            Review movieReview = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()*5)+1)
                    .text("이 영화에 대한 느낌...."+i)
                    .build();

            reviewRepository.save(movieReview);
        });

    }

    @Test
    public void testGetMovieReviews(){
        Movie movie = Movie.builder().mno(92L).build();
        List<Review> results = reviewRepository.findByMovie(movie);
        results.forEach(movieReview->{
            System.out.println(movieReview.getReviewnum());
            System.out.println("\t" + movieReview.getGrade());
            System.out.println("\t" + movieReview.getText());
            System.out.println("\t" + movieReview.getMember().getEmail());
            System.out.println("-----------------------------");
        });
    }
}