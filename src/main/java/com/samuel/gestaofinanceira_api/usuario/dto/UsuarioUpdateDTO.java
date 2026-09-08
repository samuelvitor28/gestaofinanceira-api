package com.samuel.gestaofinanceira_api.usuario.dto;

public record UsuarioUpdateDTO(
        String nome,
        String email,
        String username
) {}