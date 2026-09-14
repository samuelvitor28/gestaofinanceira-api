package com.samuel.gestaofinanceira_api.transacao;

import com.samuel.gestaofinanceira_api.conta.ContaRepository;
import com.samuel.gestaofinanceira_api.conta.exception.ContaNaoEncontradaException;
import com.samuel.gestaofinanceira_api.transacao.dto.TransacaoRequestDTO;
import com.samuel.gestaofinanceira_api.transacao.dto.TransacaoResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransacaoService   {
    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, ContaRepository contaRepository){
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
    }

    public TransacaoResponseDTO criarNovaTransacao (TransacaoRequestDTO dtoTransacao){
        Transacao novaTransacao = new Transacao();
        novaTransacao.setDescricao(dtoTransacao.descricao());
        novaTransacao.setValor(dtoTransacao.valor());
        novaTransacao.setCategoria(dtoTransacao.categoria());
        novaTransacao.setTipo(dtoTransacao.tipo());
        novaTransacao.setData(dtoTransacao.data());
        novaTransacao.setConta(contaRepository.findById(dtoTransacao.contaId()).orElseThrow(()-> new ContaNaoEncontradaException("Conta não encontrada!" + dtoTransacao.contaId())));
        Transacao transacaoSalva = transacaoRepository.save(novaTransacao);
        return toResponseDto(transacaoSalva);
    }

    public List<TransacaoResponseDTO> listarTodasTransacoes(){
        return transacaoRepository.findAll().stream().map(this::toResponseDto).toList();
    }

    public List<TransacaoResponseDTO> listarTodasTransacoesPorConta(UUID idConta){
        if(!contaRepository.existsById(idConta)){
            throw new ContaNaoEncontradaException("Conta não encontrada: " + idConta);
        }
        return transacaoRepository.findAllByContaId(idConta).stream().map(this::toResponseDto).toList();
    }

    public List<TransacaoResponseDTO> listarTodasTransacoesPorValorMaiorQue(double valor){
        if(valor < 0){
            throw new RuntimeException("O valor não pode ser negativo!");
        }

        return transacaoRepository.findAllByValorGreaterThan(valor).stream().map(this::toResponseDto).toList();
    }

    private TransacaoResponseDTO toResponseDto(Transacao transacao){
        return new TransacaoResponseDTO(transacao.getId(), transacao.getDescricao(), transacao.getValor(), transacao.getCategoria(), transacao.getTipo(), transacao.getData(), transacao.getConta().getId());
    }

}
