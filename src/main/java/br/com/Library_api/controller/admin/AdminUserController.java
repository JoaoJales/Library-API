package br.com.Library_api.controller.admin;

import br.com.Library_api.domain.user.UserService;
import br.com.Library_api.dto.user.GetUsersDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users")
@SecurityRequirement(name = "bearer-key")
public class AdminUserController {
    private final UserService userService;

    public AdminUserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Consultar todos os usuários", tags = {"9 - Admin"})
    @GetMapping("/all")
    public ResponseEntity<Page<GetUsersDTO>> getUsers (@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<GetUsersDTO> page = userService.getUsers(pageable);

        return ResponseEntity.ok(page);
    }

}
