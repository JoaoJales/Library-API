package br.com.Library_api.controller;

import br.com.Library_api.domain.fine.FineService;
import br.com.Library_api.dto.fine.GetFineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fines")
public class FineController {
    private final FineService fineService;

    public FineController (FineService fineService){
        this.fineService = fineService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GetFineDTO>> getAllFines (@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<GetFineDTO> page = fineService.getFines(pageable);

        return ResponseEntity.ok().body(page);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GetFineDTO> getFine (@PathVariable Long id) {
        GetFineDTO dto = fineService.getFine(id);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{id}/paid")
//    @PreAuthorize("hasRole('ADMIN')") --> real business rule
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'VISITOR', 'ADMIN')") //test rule
    public ResponseEntity<GetFineDTO> finePaid (@PathVariable Long id){
        GetFineDTO dto = fineService.finePaid(id);

        return ResponseEntity.ok().body(dto);
    }
}
