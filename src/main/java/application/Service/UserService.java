package application.Service;

import application.Entity.Users;
import application.Exception.UserNotFoundException;
import application.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Users findByUsername(String username){
        Optional<Users> foundedUsers = Optional.ofNullable(userRepository.findByUsername(username));
        return foundedUsers.orElseThrow(UserNotFoundException::new);
    }
}
