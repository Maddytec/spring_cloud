package br.com.maddytec.banco.service;

import br.com.maddytec.banco.domain.Pagamento;
import br.com.maddytec.banco.gateway.exceptions.PagamentoException;
import br.com.maddytec.banco.gateway.json.PagamentoJson;
import br.com.maddytec.banco.gateway.repository.PagamentoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Log4j2
@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CartaoService cartaoService;

    @Transactional
    public void pagamento(PagamentoJson pagamentoJson){

       log.info("Validando cartão");
        if(!cartaoService.isValido(pagamentoJson.getNumeroCartao(), pagamentoJson.getCodigoSegurancaCartao() )){
           throw new PagamentoException("Cartão inválido.");
        }

        log.info("Validando saldo.");
        if(!cartaoService.isSaldoSuficiente(pagamentoJson.getNumeroCartao(), pagamentoJson.getCodigoSegurancaCartao(), pagamentoJson.getValorCompra())){
           throw new PagamentoException("Saldo insuficiente.");
        }

        Pagamento pagamento = Pagamento.builder()
                .valorCompra(pagamentoJson.getValorCompra())
                .cartao(cartaoService.getCartao(pagamentoJson.getNumeroCartao(), pagamentoJson.getCodigoSegurancaCartao()))
                .build();
        log.info("Salvando pagamento.");
        pagamentoRepository.save(pagamento);

        cartaoService.atualizarSaldo(pagamentoJson.getNumeroCartao(), pagamentoJson.getCodigoSegurancaCartao(), pagamentoJson.getValorCompra());
    }
}
