package com.example.Trejd.Service;
import com.example.Trejd.*;
import com.example.Trejd.Repositories.*;
import com.example.Trejd.*;
import com.example.Trejd.Repositories.*;
import com.example.Trejd.Category;
import com.example.Trejd.OfferTrejd;
import com.example.Trejd.Repositories.*;
import com.example.Trejd.Skill;
import com.example.Trejd.Repositories.UserRepository;
import com.example.Trejd.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
    public void createUser(User user) {
        userRepo.save(user);
    }

    // Hittar alla skapade användare
    public List<User> findAllUsers(Long id) {
        return userRepo.findAllByQuery(id);
    }

    // Skapa en annons inom Users expertis-område.
    public void createOffer(OfferTrejd offerTrejd) {
        offerRepo.save(offerTrejd);
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
    public User getUserById(Long id) {
        return userRepo.findById(id).get();
    }

    // hämtar en skill med (hårdkodad id)
    public Skill getSkillById(Long skillId) {
        return skillRepo.findById(skillId).orElse(null);
    }

    // find all categories
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepo.findAll();
    }

    //find all orders by name
    public List<OrderTrejd> getOrdersByName(String name) {
        return orderRepo.findByName(name);
    }

    //find all orders by location and Profession
    public List<OrderTrejd> getOrdersByLocation(String location, String skill) {
        return orderRepo.findByLocation(location, skill);
    }

    public void createOrder2(String location, User user, Skill skill) {
        OrderTrejd order = new OrderTrejd(location, user, skill);
        orderRepo.save(order);
    }


    public boolean saveUser(User user) {
        if (userRepo.findByEmail(user.getEmail()).size() == 0) {
            userRepo.save(user);
            return true;
        } else {
            return false;
        }


    }

    public void createOrder(OrderTrejd orderTrejd) {
        orderRepo.save(orderTrejd);
    }

    public void saveTrejd(Trejd trejd) {
        trejdRepo.save(trejd);
    }

    public void createReview(Review review) {
        reviewRepo.save(review);
    }

    public List<Review> getAllReviewsByCustomerId(Long id) {
        return reviewRepo.findAllByCustomerId(id);
    }

    public User getUser(String email, String password) {
        System.out.println("restar testar");
       return userRepo.getUserByEmailAndPassword(email,password);

//        System.out.println("testar testar");
//       // User user = users.get(0);
//
//        if (user.getPassword().equals(password)) {
//            return true;
//        } else {
//            return false;
//        }
//
//
//
//       return user;
//        List<User> users = userRepo.findByEmail(email);
//        if (users.size() <= 0) {
//            return false;
//        }
//
//        User user = users.get(0);
//        if (user.getPassword().equals(password)) {
//            return user;
//        } else {
//            return false;
//        }

    }


    public List<Skill> getAllByCategoryId(Long id) {
        return (List<Skill>) skillRepo.findAllByCategoryId(id);
    }

    public List<String> getAllCategoriesByUser(Long id) {
        return (List<String>) userSkillsRepo.getAllCategoryNamesByUserId(id);
    }

    public List<OrderTrejd> getAllOrders() {
        return (List<OrderTrejd>) orderRepo.findAll();
    }

    public List<Skill> getAllSkillsByCategoryId(Long id) {
        return skillRepo.getAllSkillsByCategoryId(id);
    }

    //optional innebär att det kanske inte hittar trejden med det id:t och att vi kanske får andra alternativa grejer
    public Trejd getTrejd(Long id) {
        Optional<Trejd> trejd = trejdRepo.findById(id);
        if (trejd.isPresent()) {
            return trejd.get();
        }else {
            return null;
        }
    }


    public List<Skill> getUserSkills(Long id) {
        return skillRepo.getSkillsByUserId(id);
    }

    public List<Skill> getAllSkills() {
        return (List<Skill>) skillRepo.findAll();
    }

    public List<OfferTrejd> getOffersJoinUserJoinSkill() {
        // overkill? kommer man åt user-firstname via findAll?
        return offerRepo.getOffersJoinUserJoinSkill();
    }

    private double calculate(User currentUser, User compareUser){

        double pow1 = Math.pow((compareUser.getLatitude() - currentUser.getLatitude()),2);
        double pow2 = Math.pow((compareUser.getLongitude() - currentUser.getLongitude()),2);
        double distance = Math.sqrt(pow1 + pow2);
        return distance;

    }
    public List<User> bubbleSortUser(User user, List<User> listToSort) {


        for (int j = 0; j < listToSort.size(); j++) {

            // Gå igenom array förutom sista då den redan är högsta (-j)
            for (int i = 1; i < listToSort.size()-j; i++) {

                if(calculate(user,listToSort.get(i)) < calculate(user,listToSort.get(i-1)))
                    swap(listToSort,i,i-1);
            }
        }
        return listToSort;
    }

    private static void swap(List<User> users, int from, int to) {

        User temp = users.get(from);
        users.set(from,users.get(to));
        users.set(to,temp);

    }

    public List<User> findAllUsersSorted(User user, Boolean sortByDistance) {
        List<User> users;
        if(sortByDistance){
            users = bubbleSortUser(user,(List<User>) userRepo.findAllByQuery(user.getId()));
        }
        else{
            //sortByRating
            users = (List<User>) userRepo.findAllByQuery(user.getId());
        }
        return users;
    }

    public void saveOrder(OrderTrejd order) {
        orderRepo.save(order);
    }

    public void saveUserSkill(UserSkills us) {
        userSkillsRepo.save(us);
    }
}


