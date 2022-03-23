package com.example.Trejd.Service;


import com.example.Trejd.Category;
import com.example.Trejd.OfferTrejd;
import com.example.Trejd.Repositories.*;
import com.example.Trejd.Skill;
import com.example.Trejd.Repositories.UserRepository;
import com.example.Trejd.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrejdService {

    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    OfferTrejdRepository offerRepo;
    @Autowired
    OrderTrejdRepository orderRepo;
    @Autowired
    ReviewRepository reviewRepo;
    @Autowired
    SkillRepository skillRepo;
    @Autowired
    TrejdRepository trejdRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    UserSkillsRepository userSkillsRepo;


    // Skapar en ny användare
    public void createUser(String fn, String ln, String pw, String email) {
        User user = new User(fn, ln, pw, email);
        userRepo.save(user);
    }

    // Hittar alla skapade användare
    public List<User> findAllUsers() {
        return (List<User>) userRepo.findAll();
    }

    // Skapa en annons inom Users expertis-område.
    public void createOffer(User user, Skill skill, String location, String description) {
        OfferTrejd offer = new OfferTrejd(user, skill, location, description);
        offerRepo.save(offer);
    }

    //Hittar en annons med sökord ex. "målare"
    public List<OfferTrejd> findOfferByRequest(String skill) {
        return offerRepo.findAllBySkill(skill);
    }

    // hittar alla offers.
    public List<OfferTrejd> getAllOffers() {
        return (List<OfferTrejd>) offerRepo.findAll();
    }

    // hämtar en user(hårdkodad id)
    public User getUserById() {
        return userRepo.findById(1L).get();
    }
    // hämtar en skill med (hårdkodad id)
    public Skill getSkillById() {
        return skillRepo.findById(1L).get();
    }
    // find all categories
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepo.findAll();
    UserRepository userRepository;

    public TrejdService(){

    }
    public void saveUser(User user){
    userRepository.save(user);
    }
}
