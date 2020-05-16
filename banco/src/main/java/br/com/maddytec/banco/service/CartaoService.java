package br.com.maddytec.banco.service;

import br.com.maddytec.banco.domain.Cartao;
import br.com.maddytec.banco.gateway.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;


    public Boolean isValido(Long numeroCartao, Long codigoSegurancaCartao){
       Cartao cartao = cartaoRepository.findByNumeroCartaoAndCodigoSegurancaCartao(numeroCartao, codigoSegurancaCartao);
        return cartao != null ? Boolean.TRUE : Boolean.FALSE;
    }

    public Boolean isSaldoSuficiente(Long numeroCartao, Long codigoSegurancaCartao, BigDecimal valorCompra) {
        Cartao cartao = cartaoRepository.isSaldoSuficiente(numeroCartao, codigoSegurancaCartao, valorCompra);
         return cartao != null ? Boolean.TRUE : Boolean.FALSE;
    }

    public Cartao getCartao(Long numeroCartao, Long codigoSegurancaCartao) {
        return cartaoRepository.findByNumeroCartaoAndCodigoSegurancaCartao(numeroCartao, codigoSegurancaCartao);
    }


    @Transactional
    public void atualizarSaldo(Long numeroCartao, Long codigoSegurancaCartao, BigDecimal valorCompra) {
        cartaoRepository.atualizarSaldo(numeroCartao, codigoSegurancaCartao, valorCompra);
    }
}
