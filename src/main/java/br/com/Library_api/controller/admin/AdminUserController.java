package br.com.Library_api.controller.admin;

import br.com.Library_api.domain.user.User;
import br.com.Library_api.domain.user.UserService;
import br.com.Library_api.dto.fine.GetFineDTO;
import br.com.Library_api.dto.loan.GetLoanSummaryDTO;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import br.com.Library_api.dto.user.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;

    public AdminUserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<GetUsersDTO>> getUsers (@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<GetUsersDTO> page = userService.getUsers(pageable);

        return ResponseEntity.ok(page);
    }

}
