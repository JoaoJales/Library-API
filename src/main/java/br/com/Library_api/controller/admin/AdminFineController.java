package br.com.Library_api.controller.admin;

import br.com.Library_api.domain.fine.FineService;
import br.com.Library_api.dto.fine.GetFineDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/fines")
@SecurityRequirement(name = "bearer-key")
public class AdminFineController {
    private final FineService fineService;

    public AdminFineController(FineService fineService){
        this.fineService = fineService;
    }

    @Operation(summary = "Consultar todas as multas", tags = {"9 - Admin"})
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GetFineDTO>> getAllFines (@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<GetFineDTO> page = fineService.getFines(pageable);

        return ResponseEntity.ok().body(page);
    }

    @Operation(summary = "Consultar detalhes de um multa", tags = {"9 - Admin"})
    @GetMapping("{fineId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetFineDTO> getFine (@PathVariable Long fineId) {
        GetFineDTO dto = fineService.getFineAdmin(fineId);

        return ResponseEntity.ok().body(dto);
    }

    //TODO: disabled for common user testing
//    @Operation(summary = "Confirmar pagamento da multa", tags = {"9 - Admin"})
//    @PatchMapping("/{id}/paid")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<GetFineDTO> finePaid (@PathVariable Long id){
//        GetFineDTO dto = fineService.finePaid(id);
//
//        return ResponseEntity.ok().body(dto);
//    }
}
