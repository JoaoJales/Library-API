package br.com.Library_api.controller.user;

import br.com.Library_api.domain.fine.FineService;
import br.com.Library_api.dto.fine.GetFineDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fines")
@Tag(name = "7 - Fines")
@SecurityRequirement(name = "bearer-key")
public class FineController {
    private final FineService fineService;

    public FineController (FineService fineService){
        this.fineService = fineService;
    }


    @Operation(summary = "Consultar detalhes de uma multa")
    @GetMapping("{fineId}")
    public ResponseEntity<GetFineDTO> getFine (@PathVariable Long fineId) {
        GetFineDTO dto = fineService.getFine(fineId);

        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Pagar Multa")
    @PatchMapping("/{fineId}/paid")
//    @PreAuthorize("hasRole('ADMIN')") --> real business rule
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')") //-->test rule
    public ResponseEntity<GetFineDTO> finePaid (@PathVariable Long fineId){
        GetFineDTO dto = fineService.finePaid(fineId);

        return ResponseEntity.ok().body(dto);
    }
}
