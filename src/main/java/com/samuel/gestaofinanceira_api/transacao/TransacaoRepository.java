package com.samuel.gestaofinanceira_api.transacao;

import com.samuel.gestaofinanceira_api.transacao.enums.ECategoriaTransacao;
import com.samuel.gestaofinanceira_api.transacao.enums.ETipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    List<Transacao> findAllByContaId(UUID contaId);
    List<Transacao> findAllByContaUsuarioIdAndValorGreaterThan(UUID idUsuario, double valor);
    List<Transacao> findAllByContaUsuarioIdAndValorLessThan(UUID idUsuario, double valor);
    List<Transacao> findAllByContaUsuarioIdAndTipo(UUID idUsuario, ETipoTransacao tipo);
    List<Transacao> findAllByContaUsuarioIdAndCategoria(UUID idUsuario, ECategoriaTransacao categoria);
    List<Transacao> findAllByContaUsuarioIdAndDataBetween(UUID idUsuario, LocalDate dataInicio, LocalDate dataFinal);
}
