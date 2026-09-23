package com.samuel.gestaofinanceira_api.auth.dto;

import com.samuel.gestaofinanceira_api.usuario.dto.UsuarioResponseDTO;

public record LoginResponseDTO(
        String token,
        String tipo,
        int expiracao,
        UsuarioResponseDTO usuario
) {}
