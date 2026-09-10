package com.samuel.gestaofinanceira_api.transacao;

import com.samuel.gestaofinanceira_api.transacao.enums.ECategoriaTransacao;
import com.samuel.gestaofinanceira_api.transacao.enums.ETipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    List<Transacao> findAllByContaId(UUID contaId);
    List<Transacao> findAllByValorGreaterThan(double valor);
    List<Transacao> findAllByValorLessThan(double valor);
    List<Transacao> findAllByTipo(ETipoTransacao tipo);
    List<Transacao> findAllByCategoria(ECategoriaTransacao categoria);
    List<Transacao> findAllByDataBetween(LocalDate dataInicio, LocalDate dataFinal);
}
