package com.samuel.gestaofinanceira_api.conta;

import com.samuel.gestaofinanceira_api.conta.dto.ContaRequestDTO;
import com.samuel.gestaofinanceira_api.conta.dto.ContaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {
    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody ContaRequestDTO contaDTO){
        return ResponseEntity.ok(contaService.criarConta(contaDTO));
    }

    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(contaService.listarTodasContas());
    }
}
