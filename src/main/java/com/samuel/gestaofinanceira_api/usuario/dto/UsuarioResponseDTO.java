package com.samuel.gestaofinanceira_api.usuario.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        String username,
        LocalDateTime criadoEm
) {}