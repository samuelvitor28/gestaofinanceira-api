package com.samuel.gestaofinanceira_api.transacao;

import com.samuel.gestaofinanceira_api.conta.Conta;
import com.samuel.gestaofinanceira_api.transacao.enums.ECategoriaTransacao;
import com.samuel.gestaofinanceira_api.transacao.enums.ETipoTransacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="transacoes")
@Getter
@Setter
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(nullable = false)
    private double valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ECategoriaTransacao categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ETipoTransacao tipo;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
}
