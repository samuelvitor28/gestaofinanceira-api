package com.samuel.gestaofinanceira_api.transacao;

import com.samuel.gestaofinanceira_api.transacao.dto.TransacaoRequestDTO;
import com.samuel.gestaofinanceira_api.transacao.dto.TransacaoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> criarNovaTransacao (@RequestBody TransacaoRequestDTO dtoTransacao){
        return ResponseEntity.ok(transacaoService.criarNovaTransacao(dtoTransacao));
    }

    @GetMapping("/usuario/{idUsuario}/{valor}")
    public ResponseEntity<List<TransacaoResponseDTO>>listarTodasTransacoesPorValorMaiorQue (@PathVariable UUID idUsuario, @PathVariable double valor){
        return ResponseEntity.ok(transacaoService.listarTodasTransacoesPorValorMaiorQue(idUsuario, valor));
    }
}
