package com.samuel.gestaofinanceira_api.usuario;

import com.samuel.gestaofinanceira_api.usuario.dto.UsuarioRequestDTO;
import com.samuel.gestaofinanceira_api.usuario.dto.UsuarioResponseDTO;
import com.samuel.gestaofinanceira_api.usuario.dto.UsuarioUpdateDTO;
import com.samuel.gestaofinanceira_api.usuario.exception.EmailJaCadastradoException;
import com.samuel.gestaofinanceira_api.usuario.exception.UsernameJaCadastradoException;
import com.samuel.gestaofinanceira_api.usuario.exception.UsuarioNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException(dto.email());
        }
        if (usuarioRepository.existsByUsername(dto.username())) {
            throw new UsernameJaCadastradoException(dto.username());
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setUsername(dto.username());
        usuario.setSenha(dto.senha());
        usuario.setCriadoEm(LocalDateTime.now());

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: " + id));
        return toResponseDTO(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public UsuarioResponseDTO atualizar(UUID id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: " + id));

        if (dto.nome() != null) {
            usuario.setNome(dto.nome());
        }

        if (dto.email() != null && !dto.email().equals(usuario.getEmail())) {
            if (usuarioRepository.existsByEmail(dto.email())) {
                throw new EmailJaCadastradoException(dto.email());
            }
            usuario.setEmail(dto.email());
        }

        if (dto.username() != null && !dto.username().equals(usuario.getUsername())) {
            if (usuarioRepository.existsByUsername(dto.username())) {
                throw new UsernameJaCadastradoException(dto.username());
            }
            usuario.setUsername(dto.username());
        }

        return toResponseDTO(usuario);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getCriadoEm()
        );
    }
}