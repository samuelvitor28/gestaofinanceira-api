package com.samuel.gestaofinanceira_api.conta;

import com.samuel.gestaofinanceira_api.conta.dto.ContaRequestDTO;
import com.samuel.gestaofinanceira_api.conta.dto.ContaResponseDTO;
import com.samuel.gestaofinanceira_api.conta.dto.ContaUpdateDTO;
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

    public ContaResponseDTO alterarNomeConta(UUID idConta, ContaUpdateDTO dtoConta){
        Conta contaParaAtualizar = contaRepository.findById(idConta).orElseThrow();

        if(dtoConta.nome() == null){
            System.out.println("Nome vazio, não pode ser substituído!");
            throw new RuntimeException();
        }

        if(dtoConta.nome() == contaParaAtualizar.getNome()){
            return toResponseDTO(contaParaAtualizar);
        }

        contaParaAtualizar.setNome(dtoConta.nome());
        return toResponseDTO(contaParaAtualizar);

    }

    private ContaResponseDTO toResponseDTO(Conta conta){
        return new ContaResponseDTO(conta.getId(), conta.getNome(), conta.getUsuario().getId());
    }
}
