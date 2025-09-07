package br.com.Library_api.controller;

import br.com.Library_api.domain.user.UserService;
import br.com.Library_api.dto.user.GetDetailingUserDTO;
import br.com.Library_api.dto.user.GetUsersDTO;
import br.com.Library_api.dto.user.PutUserDTO;
import br.com.Library_api.dto.user.UserRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<GetDetailingUserDTO> postUser(@RequestBody @Valid UserRegisterDTO data, UriComponentsBuilder uriBuilder){
        var user = userService.createUser(data);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetDetailingUserDTO(user));
    }

    @PutMapping
    public ResponseEntity<GetDetailingUserDTO> putUser(@RequestBody @Valid PutUserDTO data){
        var user = userService.updateUser(data);

        return ResponseEntity.ok().body(new GetDetailingUserDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<GetUsersDTO>> getUsers (@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<GetUsersDTO> page = userService.getUsers(pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetDetailingUserDTO> getDetailingUser(@PathVariable Long id){
        GetDetailingUserDTO detailingUser = userService.getDetailingUser(id);

        return ResponseEntity.ok(detailingUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
