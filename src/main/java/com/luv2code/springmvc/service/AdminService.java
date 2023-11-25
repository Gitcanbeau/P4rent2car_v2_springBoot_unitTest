package com.luv2code.springmvc.service;

import com.luv2code.springmvc.dao.CarRepository;
import com.luv2code.springmvc.dao.CheckoutRepository;
import com.luv2code.springmvc.dao.ReviewRepository;
import com.luv2code.springmvc.entity.Car;
import com.luv2code.springmvc.requestmodels.AddCarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private CarRepository carRepository;
    private ReviewRepository reviewRepository;
    private CheckoutRepository checkoutRepository;

    @Autowired
    public AdminService (CarRepository carRepository,
                         ReviewRepository reviewRepository,
                         CheckoutRepository checkoutRepository) {
        this.carRepository = carRepository;
        this.reviewRepository = reviewRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public void increaseCarQuantity(Long carId) throws Exception {

        Optional<Car> car = carRepository.findById(carId);

        if (!car.isPresent()) {
            throw new Exception("Car cannot be found");
        }

        car.get().setAmountsAvailable(car.get().getAmountsAvailable() + 1);
        car.get().setAmounts(car.get().getAmounts() + 1);

        carRepository.save(car.get());
    }

    public void decreaseCarQuantity(Long carId) throws Exception {

        Optional<Car> car = carRepository.findById(carId);

        if (!car.isPresent() || car.get().getAmountsAvailable() <= 0 || car.get().getAmounts() <= 0) {
            throw new Exception("Car cannot be found or quantity locked");
        }

        car.get().setAmountsAvailable(car.get().getAmountsAvailable() - 1);
        car.get().setAmounts(car.get().getAmounts() - 1);

        carRepository.save(car.get());
    }

    public void postCar(AddCarRequest addCarRequest) {
        Car car = new Car();
        car.setModel(addCarRequest.getModel());
        car.setCompany(addCarRequest.getCompany());
        car.setDescription(addCarRequest.getDescription());
        car.setAmounts(addCarRequest.getAmounts());
        car.setAmountsAvailable(addCarRequest.getAmounts());
        car.setCategory(addCarRequest.getCategory());
        car.setImg(addCarRequest.getImg());
        carRepository.save(car);
    }

    public void deleteCar(Long carId) throws Exception {

        Optional<Car> car = carRepository.findById(carId);

        if (!car.isPresent()) {
            throw new Exception("Car cannot be found");
        }

        carRepository.delete(car.get());
        checkoutRepository.deleteAllByCarId(carId);
        reviewRepository.deleteAllByCarId(carId);
    }
}
