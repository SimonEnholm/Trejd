package com.example.Trejd.Service;

import com.example.Trejd.*;
import com.example.Trejd.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrejdService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TrejdRepository trejdRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    SkillRepository skillRepository;

    public TrejdService(){

    }
public boolean saveUser(User user){
    if (userRepository.findByEmail(user.getEmail())==null){
           userRepository.save(user);
           return true;
    }else {
        return false;
    }

            }
            public void createOrder(String location, User user, Skill skill){
                OrderTrejd order = new OrderTrejd(location, user, skill);
                orderRepository.save(order);
            }

            public void saveTrejd (Trejd trejd){
                trejdRepository.save(trejd);

            }
            public void createReview(User customer, User performer, Trejd trejd, String description, int rating){
            Review review = new Review(customer, performer, trejd, description, rating);
            reviewRepository.save(review);
            }
            public List<Review> getAllReviewsByCustomerId (Long id){
            return reviewRepository.findAllByCustomerId(id);
            }

    public User getUserById() {
        return userRepository.findById(1L).get();
    }
}

