package ua.hudyma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.hudyma.entity.User;
import ua.hudyma.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers (){
        return (List<User>) userRepository.findAll();
    }

    public void deleteUser (String id){
        userRepository.deleteById(id);
    }

    public User addUser (User user){
        return userRepository.save(user);
    }
}
