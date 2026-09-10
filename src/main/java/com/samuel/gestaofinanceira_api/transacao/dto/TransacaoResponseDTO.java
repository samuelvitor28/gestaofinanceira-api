package com.samuel.gestaofinanceira_api.transacao.dto;

import com.samuel.gestaofinanceira_api.transacao.enums.ECategoriaTransacao;
import com.samuel.gestaofinanceira_api.transacao.enums.ETipoTransacao;

import java.time.LocalDate;
import java.util.UUID;

public record TransacaoResponseDTO(
        UUID id,
        String descricao,
        Double valor,
        ECategoriaTransacao categoria,
        ETipoTransacao tipo,
        LocalDate data,
        UUID contaId
) {}
