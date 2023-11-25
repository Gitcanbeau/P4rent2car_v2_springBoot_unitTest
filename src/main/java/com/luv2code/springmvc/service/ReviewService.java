package com.luv2code.springmvc.service;

import com.luv2code.springmvc.dao.CarRepository;
import com.luv2code.springmvc.dao.ReviewRepository;
import com.luv2code.springmvc.entity.Review;
import com.luv2code.springmvc.requestmodels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    public boolean isReviewPresentCheck(String userEmail, Long carId){
        Review review=reviewRepository.findByUserEmailAndCarId(userEmail,carId);
        if(review!=null){
            return true;
        }
        return false;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
        Review validateReview = reviewRepository.findByUserEmailAndCarId(userEmail, reviewRequest.getCarId());
        if (validateReview != null) {
            throw new Exception("Review already created");
        }

        Review review = new Review();
        review.setCarId(reviewRequest.getCarId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if (reviewRequest.getReviewDescription().isPresent()) {
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }

    public Boolean userReviewListed(String userEmail, Long carId) {
        Review validateReview = reviewRepository.findByUserEmailAndCarId(userEmail, carId);
        if (validateReview != null) {
            return true;
        } else {
            return false;
        }
    }

}









