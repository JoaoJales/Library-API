package br.com.Library_api.controller.admin;

import br.com.Library_api.domain.user.UserService;
import br.com.Library_api.dto.user.GetUsersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;

    public AdminUserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<GetUsersDTO>> getUsers (@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<GetUsersDTO> page = userService.getUsers(pageable);

        return ResponseEntity.ok(page);
    }

}
