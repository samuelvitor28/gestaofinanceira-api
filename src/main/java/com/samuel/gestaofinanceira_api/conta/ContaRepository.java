package com.samuel.gestaofinanceira_api.conta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
    List<Conta> findAllByUsuarioId(UUID usuarioId);
}
