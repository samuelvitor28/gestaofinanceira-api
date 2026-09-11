package com.samuel.gestaofinanceira_api.conta;

import com.samuel.gestaofinanceira_api.conta.dto.ContaRequestDTO;
import com.samuel.gestaofinanceira_api.conta.dto.ContaResponseDTO;
import com.samuel.gestaofinanceira_api.usuario.UsuarioRepository;
import com.samuel.gestaofinanceira_api.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

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


    private ContaResponseDTO toResponseDTO(Conta conta){
        return new ContaResponseDTO(conta.getId(), conta.getNome(), conta.getUsuario().getId());
    }
}
