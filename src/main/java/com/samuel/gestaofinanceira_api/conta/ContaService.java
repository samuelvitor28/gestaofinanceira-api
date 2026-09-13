package com.samuel.gestaofinanceira_api.conta;

import com.samuel.gestaofinanceira_api.conta.dto.ContaRequestDTO;
import com.samuel.gestaofinanceira_api.conta.dto.ContaResponseDTO;
import com.samuel.gestaofinanceira_api.conta.dto.ContaUpdateDTO;
import com.samuel.gestaofinanceira_api.conta.exception.ContaNaoEncontradaException;
import com.samuel.gestaofinanceira_api.conta.exception.NomeForaDoLimiteException;
import com.samuel.gestaofinanceira_api.conta.exception.NomeNullException;
import com.samuel.gestaofinanceira_api.usuario.UsuarioRepository;
import com.samuel.gestaofinanceira_api.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    public ContaService(ContaRepository contaRepository, UsuarioRepository usuarioRepository) {
        this.contaRepository = contaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ContaResponseDTO criarConta(ContaRequestDTO dtoConta){
        Conta novaConta = new Conta();
        novaConta.setNome(dtoConta.nome());
        novaConta.setUsuario(usuarioRepository.findById(dtoConta.usuarioId()).orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário não encontrado: " + dtoConta.usuarioId())));
        Conta contaSalva = contaRepository.save(novaConta);
        return toResponseDTO(contaSalva);
    }

    public List<ContaResponseDTO> listarTodasContas(){
        return contaRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public List<ContaResponseDTO> listarContasPorUsuario(UUID idUsuario){
        if(!usuarioRepository.existsById(idUsuario)){
            throw new UsuarioNaoEncontradoException("Usuario não encontrado " + idUsuario);
        }

        return contaRepository.findAllByUsuarioId(idUsuario).stream().map(this::toResponseDTO).toList();
    }

    public ContaResponseDTO alterarNomeConta(UUID idConta, ContaUpdateDTO dtoConta){
        Conta contaParaAtualizar = contaRepository.findById(idConta).orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada!" + idConta));

        if(dtoConta.nome() == null){
            throw new NomeNullException();
        }

        if(dtoConta.nome().length() > 50){
            throw new NomeForaDoLimiteException(dtoConta.nome());
        }

        if(!dtoConta.nome().equals(contaParaAtualizar.getNome())){
            contaParaAtualizar.setNome(dtoConta.nome());
            contaRepository.save(contaParaAtualizar);
        }

        return toResponseDTO(contaParaAtualizar);
    }

    public void deletarConta(UUID idConta){
        if (!contaRepository.existsById(idConta)) {
            throw new UsuarioNaoEncontradoException("Conta não encontrada: " + idConta);
        }
        contaRepository.deleteById(idConta);
    }

    public void deletarTodasContasPorUsuario(UUID idUsuario){
        if(!usuarioRepository.existsById(idUsuario)){
            throw new UsuarioNaoEncontradoException("Usuario não encontrado " + idUsuario);
        }
        for(Conta conta : contaRepository.findAllByUsuarioId(idUsuario)){
            contaRepository.deleteById(conta.getId());
        }
    }

    private ContaResponseDTO toResponseDTO(Conta conta){
        return new ContaResponseDTO(conta.getId(), conta.getNome(), conta.getUsuario().getId());
    }
}
