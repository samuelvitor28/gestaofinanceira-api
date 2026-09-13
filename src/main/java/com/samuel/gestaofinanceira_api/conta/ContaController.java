package com.samuel.gestaofinanceira_api.conta;

import com.samuel.gestaofinanceira_api.conta.dto.ContaRequestDTO;
import com.samuel.gestaofinanceira_api.conta.dto.ContaResponseDTO;
import com.samuel.gestaofinanceira_api.conta.dto.ContaUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contas")
public class ContaController {
    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@RequestBody ContaRequestDTO dtoConta){
        return ResponseEntity.ok(contaService.criarConta(dtoConta));
    }

    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(contaService.listarTodasContas());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ContaResponseDTO>> listarContasPorUsuario(@PathVariable UUID idUsuario){
        return ResponseEntity.ok(contaService.listarContasPorUsuario(idUsuario));
    }

    @PatchMapping("/{idConta}")
    public ResponseEntity<ContaResponseDTO> alterarNomeConta(@PathVariable UUID idConta, @RequestBody ContaUpdateDTO dtoConta){
        return ResponseEntity.ok(contaService.alterarNomeConta(idConta, dtoConta));
    }

    @DeleteMapping("/{idConta}")
    public ResponseEntity<Void> deletarConta (@PathVariable UUID idConta){
        contaService.deletarConta(idConta);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<Void> deletarTodasContasPorUsuario (@PathVariable UUID idUsuario){
        contaService.deletarTodasContasPorUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
