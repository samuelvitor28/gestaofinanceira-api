package com.samuel.gestaofinanceira_api.usuario.dto;

public record UsuarioRequestDTO(
        String nome,
        String email,
        String senha,
        String username
) {}