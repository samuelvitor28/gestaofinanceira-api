package com.samuel.gestaofinanceira_api.conta.dto;

import java.util.UUID;

public record ContaResponseDTO(
        UUID id,
        String nome,
        UUID usuarioId
) {}
