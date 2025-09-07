package br.com.Library_api.domain.user;

import br.com.Library_api.dto.user.GetDetailingUserDTO;
import br.com.Library_api.dto.user.GetUsersDTO;
import br.com.Library_api.dto.user.PutUserDTO;
import br.com.Library_api.dto.user.UserRegisterDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(UserRegisterDTO data){
        if (userRepository.existsByEmail(data.email())){
            throw new IllegalArgumentException("email already registered");
        }

        var user = new User(data);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User updateUser(@Valid PutUserDTO data) {
        if (!userRepository.existsById(data.id())){
            throw new IllegalArgumentException("user id not found");
        }

        if (userRepository.existsByEmail(data.email())){
            throw new IllegalArgumentException("email already registered");
        }

        var user = findUser(data.id());
        user.updateInfoUser(data);
        userRepository.save(user);

        return user;
    }

    public Page<GetUsersDTO> getUsers(Pageable pageable){
        return userRepository.findAll(pageable).map(GetUsersDTO::new);
    }

    public GetDetailingUserDTO getDetailingUser(Long id){
        var user = findUser(id);

        return new GetDetailingUserDTO(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        var user = findUser(id);
//        user.deleteUser();
        userRepository.save(user);
    }

    private User findUser(Long id){
        var user = userRepository.findById(id);

        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        return user.get();
    }
}
