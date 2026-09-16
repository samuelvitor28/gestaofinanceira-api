package com.samuel.gestaofinanceira_api.transacao;

import com.samuel.gestaofinanceira_api.conta.ContaRepository;
import com.samuel.gestaofinanceira_api.conta.exception.ContaNaoEncontradaException;
import com.samuel.gestaofinanceira_api.transacao.dto.TransacaoRequestDTO;
import com.samuel.gestaofinanceira_api.transacao.dto.TransacaoResponseDTO;
import com.samuel.gestaofinanceira_api.transacao.dto.TransacaoUpdateDTO;
import com.samuel.gestaofinanceira_api.transacao.enums.ECategoriaTransacao;
import com.samuel.gestaofinanceira_api.transacao.enums.ETipoTransacao;
import com.samuel.gestaofinanceira_api.usuario.UsuarioRepository;
import com.samuel.gestaofinanceira_api.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TransacaoService   {
    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, ContaRepository contaRepository, UsuarioRepository usuarioRepository){
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
        this.usuarioRepository = usuarioRepository;
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

    public List<TransacaoResponseDTO> listarTodasTransacoesPorValorMaiorQue(UUID usuarioId, double valor){
        if(!usuarioRepository.existsById(usuarioId)){
            throw new UsuarioNaoEncontradoException("Usuário não encontrado: " + usuarioId);
        }

        if(valor < 0){
            throw new RuntimeException("O valor não pode ser negativo!");
        }

        return transacaoRepository.findAllByContaUsuarioIdAndValorGreaterThan(usuarioId, valor).stream().map(this::toResponseDto).toList();
    }

    public List<TransacaoResponseDTO> listarTodasTransacoesPorValorMenorQue(UUID usuarioId, double valor){
        if(!usuarioRepository.existsById(usuarioId)){
            throw new UsuarioNaoEncontradoException("Usuário não encontrado: " + usuarioId);
        }

        if(valor < 0){
            throw new RuntimeException("O valor não pode ser negativo!");
        }

        return transacaoRepository.findAllByContaUsuarioIdAndValorLessThan(usuarioId, valor).stream().map(this::toResponseDto).toList();
    }

    public List<TransacaoResponseDTO> listarTodasTransacoesPorTipo(UUID usuarioId, ETipoTransacao tipo){
        if(!usuarioRepository.existsById(usuarioId)){
            throw new UsuarioNaoEncontradoException("Usuário não encontrado: " + usuarioId);
        }
        return transacaoRepository.findAllByContaUsuarioIdAndTipo(usuarioId, tipo).stream().map(this::toResponseDto).toList();
    }

    public List<TransacaoResponseDTO> listarTodasTransacoesPorCategoria(UUID usuarioId, ECategoriaTransacao categoriaTransacao){
        if(!usuarioRepository.existsById(usuarioId)){
            throw new UsuarioNaoEncontradoException("Usuário não encontrado: " + usuarioId);
        }
        return transacaoRepository.findAllByContaUsuarioIdAndCategoria(usuarioId, categoriaTransacao).stream().map(this::toResponseDto).toList();
    }

    public List<TransacaoResponseDTO> listarTodasTransacoesPorDataInicioEFim(UUID usuarioId, LocalDate dataInicio, LocalDate dataFim){
        if(!usuarioRepository.existsById(usuarioId)){
            throw new UsuarioNaoEncontradoException("Usuário não encontrado: " + usuarioId);
        }
        return transacaoRepository.findAllByContaUsuarioIdAndDataBetween(usuarioId, dataInicio, dataFim).stream().map(this:: toResponseDto).toList();
    }

    public TransacaoResponseDTO alterarTransacao(UUID idTransacao, TransacaoUpdateDTO dtoTransacao){
        Transacao transacaoParaAtualizar = transacaoRepository.findById(idTransacao).orElseThrow(() -> new RuntimeException("Transação não encontrada: " + idTransacao));
        if(dtoTransacao.descricao()!= null && !dtoTransacao.descricao().equals(transacaoParaAtualizar.getDescricao())){
            transacaoParaAtualizar.setDescricao(dtoTransacao.descricao());
        }

        if(dtoTransacao.valor() != null && dtoTransacao.valor() != transacaoParaAtualizar.getValor()){
            transacaoParaAtualizar.setValor(dtoTransacao.valor());
        }

        if(dtoTransacao.categoria() != null && dtoTransacao.categoria() != transacaoParaAtualizar.getCategoria()){
            transacaoParaAtualizar.setCategoria(dtoTransacao.categoria());
        }

        if(dtoTransacao.tipo() != null && dtoTransacao.tipo() != transacaoParaAtualizar.getTipo()){
            transacaoParaAtualizar.setTipo(dtoTransacao.tipo());
        }

        if(dtoTransacao.data() != null && dtoTransacao.data() != transacaoParaAtualizar.getData()){
            transacaoParaAtualizar.setData(dtoTransacao.data());
        }

        if(dtoTransacao.contaId() != null && dtoTransacao.contaId() != transacaoParaAtualizar.getId()){
            transacaoParaAtualizar.setConta(contaRepository.findById(dtoTransacao.contaId()).orElseThrow(()-> new ContaNaoEncontradaException("Conta não encontrada!" + dtoTransacao.contaId())));
        }

        transacaoRepository.save(transacaoParaAtualizar);
        return toResponseDto(transacaoParaAtualizar);
    }

    public void deletarTransacao (UUID idTransacao){
        if(!transacaoRepository.existsById(idTransacao)){
            throw new RuntimeException("Transação não encontrada!" + idTransacao);
        }
        transacaoRepository.deleteById(idTransacao);
    }

    private TransacaoResponseDTO toResponseDto(Transacao transacao){
        return new TransacaoResponseDTO(transacao.getId(), transacao.getDescricao(), transacao.getValor(), transacao.getCategoria(), transacao.getTipo(), transacao.getData(), transacao.getConta().getId());
    }

}
