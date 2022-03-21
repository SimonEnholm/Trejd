package Service;

import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrejdService {

    //todo Atowire respositories

    @Autowired
    UserRepository userRepository;


}
