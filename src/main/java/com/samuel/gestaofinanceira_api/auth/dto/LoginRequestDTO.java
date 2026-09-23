package com.samuel.gestaofinanceira_api.auth.dto;

public record LoginRequestDTO(
        String username,
        String senha
) {}
