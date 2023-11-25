package com.luv2code.springmvc.service;

import com.luv2code.springmvc.dao.CarRepository;
import com.luv2code.springmvc.dao.CheckoutRepository;
import com.luv2code.springmvc.dao.HistoryRepository;
import com.luv2code.springmvc.dao.PaymentRepository;
import com.luv2code.springmvc.entity.Car;
import com.luv2code.springmvc.entity.Checkout;
import com.luv2code.springmvc.entity.History;
import com.luv2code.springmvc.entity.Payment;
import com.luv2code.springmvc.responsemodels.ShelfCurrentLoansResponse;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
@Transactional
public class CarService {

    private CarRepository carRepository;

    private CheckoutRepository checkoutRepository;

    private HistoryRepository historyRepository;

    private PaymentRepository paymentRepository;

    @Autowired
    public CarService(CarRepository carRepository, CheckoutRepository checkoutRepository,
                      HistoryRepository historyRepository, PaymentRepository paymentRepository) {
        this.carRepository = carRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
        this.paymentRepository = paymentRepository;
    }

    public boolean isCarPresentCheck(Long carId){
        Optional<Car> car=carRepository.findById(carId);
        if(car.isPresent()){
            return true;
        }
        return false;
    }
    public boolean isCheckoutPresentCheck(String userEmail, Long carId){
        Checkout ch=checkoutRepository.findByUserEmailAndCarId(userEmail,carId);
        if(ch!=null){
            return true;
        }
        return false;
    }



    public Car checkoutCar (String userEmail, Long carId) throws Exception {

        Optional<Car> car = carRepository.findById(carId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndCarId(userEmail, carId);

        if (!car.isPresent() || validateCheckout != null || car.get().getAmountsAvailable() <= 0) {
            throw new Exception("Car doesn't exist or already checked out by user");
        }

        List<Checkout> currentCarsCheckedOut = checkoutRepository.findCarsByUserEmail(userEmail);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        boolean carNeedsReturned = false;

        for (Checkout checkout: currentCarsCheckedOut) {
            Date d1 = sdf.parse(checkout.getReturnDate());
            Date d2 = sdf.parse(LocalDate.now().toString());

            TimeUnit time = TimeUnit.DAYS;

            double differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

            if (differenceInTime < 0) {
                carNeedsReturned = true;
                break;
            }
        }

        Payment userPayment = paymentRepository.findByUserEmail(userEmail);

        if ((userPayment != null && userPayment.getAmount() > 0) || (userPayment != null && carNeedsReturned)) {
            throw new Exception("Outstanding fees");
        }

        if (userPayment == null) {
            Payment payment = new Payment();
            payment.setAmount(00.00);
            payment.setUserEmail(userEmail);
            paymentRepository.save(payment);
        }

        car.get().setAmountsAvailable(car.get().getAmountsAvailable() - 1);
        carRepository.save(car.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                car.get().getId()
        );

        checkoutRepository.save(checkout);

        return car.get();
    }

    public Boolean checkoutCarByUser(String userEmail, Long carId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndCarId(userEmail, carId);
        if (validateCheckout != null) {
            return true;
        } else {
            return false;
        }
    }

    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findCarsByUserEmail(userEmail).size();
    }

    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {

        List<ShelfCurrentLoansResponse> storagehouseCurrentLoansResponses = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.findCarsByUserEmail(userEmail);
        List<Long> carIdList = new ArrayList<>();

        for (Checkout i: checkoutList) {
            carIdList.add(i.getCarId());
        }

        List<Car> cars = carRepository.findCarsByCarIds(carIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Car car : cars) {
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(x -> x.getCarId() == car.getId()).findFirst();

            if (checkout.isPresent()) {

                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;

                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(),
                        TimeUnit.MILLISECONDS);

                storagehouseCurrentLoansResponses.add(new ShelfCurrentLoansResponse(car, (int) difference_In_Time));
            }
        }
        return storagehouseCurrentLoansResponses;
    }

    public void returnCar (String userEmail, Long carId) throws Exception {

        Optional<Car> car = carRepository.findById(carId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndCarId(userEmail, carId);

        if (!car.isPresent() || validateCheckout == null) {
            throw new Exception("Car does not exist or not checked out by user");
        }

        car.get().setAmountsAvailable(car.get().getAmountsAvailable() + 1);

        carRepository.save(car.get());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = sdf.parse(validateCheckout.getReturnDate());
        Date d2 = sdf.parse(LocalDate.now().toString());

        TimeUnit time = TimeUnit.DAYS;

        double differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

        if (differenceInTime < 0) {
            Payment payment = paymentRepository.findByUserEmail(userEmail);

            payment.setAmount(payment.getAmount() + (differenceInTime * -1));
            paymentRepository.save(payment);
        }

        checkoutRepository.deleteById(validateCheckout.getId());

        History history = new History(
                userEmail,
                validateCheckout.getCheckoutDate(),
                LocalDate.now().toString(),
                car.get().getModel(),
                car.get().getCompany(),
                car.get().getDescription(),
                car.get().getImg()
        );

        historyRepository.save(history);
    }

    public void renewLoan(String userEmail, Long carId) throws Exception {

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndCarId(userEmail, carId);

        if (validateCheckout == null) {
            throw new Exception("Car does not exist or not checked out by user");
        }

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
        Date d2 = sdFormat.parse(LocalDate.now().toString());

        if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {

            Date targetDate=sdFormat.parse(LocalDate.now().plusDays(7).toString());
            if(targetDate.compareTo(d1)>0) {

                validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
                checkoutRepository.save(validateCheckout);
            }
        }
    }

}















