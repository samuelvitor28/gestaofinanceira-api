package com.samuel.gestaofinanceira_api.conta.dto;

import java.util.UUID;

public record ContaRequestDTO(
        String nome,
        UUID usuarioId
) {}
