package com.example.Trejd;

import com.example.Trejd.Service.TrejdService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TrejdApplicationTests {

	@Autowired
	TrejdService service;

	@Test
	void contextLoads() {
	}

	// testar att skapa en användare och jämför listan
	@Test
	void findAllUsers() {
		List<User> userList = service.findAllUsers();
		int lengthList = userList.size();
		User user = new User("admin","admin","123","admin@test.se");
		service.createUser(user);
		userList = service.findAllUsers();
		Assertions.assertEquals(true, lengthList + 1 == userList.size());
	}

	// Hittar annonser från request. (hårdkodat från databasen) - skapas även ett nytt "målaroffer" på rad 45
	@Test
	void findOfferByRequest() {
		List<OfferTrejd>offerList = service.findOfferByRequest("målare");
		Assertions.assertEquals(7,offerList.size());
	}

	// Skapar en Offer (hårdkodade users och skills)
	@Test
	void createOffer() {
		List<OfferTrejd> offerList = service.getAllOffers();
		int lengthList = offerList.size();
		User user = service.getUserById();
		Skill skill = service.getSkillById();
		OfferTrejd offerTrejd = new OfferTrejd(user,skill,"Sollentuna","efwfwefwe");
		service.createOffer(offerTrejd);
		offerList = service.getAllOffers();
		Assertions.assertEquals(true, lengthList + 1 == offerList.size());
	}

	// testar att hämta alla Kategorier
	@Test
	void findAllCategories() {
		List<Category> categoryList = service.getAllCategories();
		Assertions.assertEquals(2, categoryList.size());
	}


}
