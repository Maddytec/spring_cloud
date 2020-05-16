package br.com.maddytec.banco.gateway.repository;

import br.com.maddytec.banco.domain.Cartao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, Long> {

    public Cartao findByNumeroCartaoAndCodigoSegurancaCartao(Long numeroCartao, Long codigoSegurancaCartao);

    @Modifying
    @Query("update Cartao set valorCredito = (valorCredito - ?3) where numeroCartao = ?1 and codigoSegurancaCartao = ?2")
    public void atualizarSaldo(Long numeroCartao, Long codigoSegurancaCartao, BigDecimal valorCompra);

    @Query("from Cartao cartao where numeroCartao = ?1 and codigoSegurancaCartao = ?2 and valorCredito >= ?3")
    Cartao isSaldoSuficiente(Long numeroCartao, Long codigoSegurancaCartao, BigDecimal valorCompra);
}
