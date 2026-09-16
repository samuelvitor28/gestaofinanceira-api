package com.samuel.gestaofinanceira_api.transacao;

import com.samuel.gestaofinanceira_api.transacao.dto.TransacaoRequestDTO;
import com.samuel.gestaofinanceira_api.transacao.dto.TransacaoResponseDTO;
import com.samuel.gestaofinanceira_api.transacao.dto.TransacaoUpdateDTO;
import com.samuel.gestaofinanceira_api.transacao.enums.ECategoriaTransacao;
import com.samuel.gestaofinanceira_api.transacao.enums.ETipoTransacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping
    public ResponseEntity <List<TransacaoResponseDTO>> listarTodasTransacoes(){
        return ResponseEntity.ok(transacaoService.listarTodasTransacoes());
    }

    @GetMapping("/conta/{idConta}/")
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodasTransacoesPorConta(@PathVariable UUID idConta){
        return ResponseEntity.ok(transacaoService.listarTodasTransacoesPorConta(idConta));
    }

    @GetMapping("/usuario/{idUsuario}/menor/{valor}")
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodasTransacoesPorValorMenorQue(@PathVariable UUID idUsuario, @PathVariable double valor){
        return ResponseEntity.ok(transacaoService.listarTodasTransacoesPorValorMenorQue(idUsuario, valor));
    }

    @GetMapping("/usuario/{idUsuario}/maior/{valor}")
    public ResponseEntity<List<TransacaoResponseDTO>>listarTodasTransacoesPorValorMaiorQue (@PathVariable UUID idUsuario, @PathVariable double valor){
        return ResponseEntity.ok(transacaoService.listarTodasTransacoesPorValorMaiorQue(idUsuario, valor));
    }

    @GetMapping("/usuario/{idUsuario}/tipo/{tipo}")
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodasTransacoesPorTipo (@PathVariable UUID idUsuario, @PathVariable ETipoTransacao tipo){
        return ResponseEntity.ok(transacaoService.listarTodasTransacoesPorTipo(idUsuario, tipo));
    }

    @GetMapping("usuario/{idUsuario}/categoria/{categoria}")
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodasTransacoesPorCategoria (@PathVariable UUID idUsuario, @PathVariable ECategoriaTransacao categoria){
        return ResponseEntity.ok(transacaoService.listarTodasTransacoesPorCategoria(idUsuario, categoria));
    }

    @GetMapping("usuario/{idUsuario}/dataInicio/{dataInicio}/dataFim/{dataFim}")
     public ResponseEntity<List<TransacaoResponseDTO>>  listarTodasTransacoesPorDataInicioEFim(@PathVariable UUID idUsuario, @PathVariable LocalDate dataInicio, @PathVariable LocalDate dataFim){
        return ResponseEntity.ok(transacaoService.listarTodasTransacoesPorDataInicioEFim(idUsuario, dataInicio, dataFim));
    }

    @PatchMapping("{idTransacao}")
    public ResponseEntity<TransacaoResponseDTO> alterarTransacao(@PathVariable UUID idTransacao, @RequestBody TransacaoUpdateDTO transacaoUpdateDTO){
        return ResponseEntity.ok(transacaoService.alterarTransacao(idTransacao,transacaoUpdateDTO));
    }

    @DeleteMapping("{idTransacao}")
    public ResponseEntity<Void> deletarTransacao(@PathVariable UUID idTransacao){
        transacaoService.deletarTransacao(idTransacao);
        return ResponseEntity.noContent().build();
    }
}
