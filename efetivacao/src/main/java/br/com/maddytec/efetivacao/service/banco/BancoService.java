package br.com.maddytec.efetivacao.service.banco;

import br.com.maddytec.efetivacao.gateway.json.BancoRetornoJson;
import br.com.maddytec.efetivacao.gateway.json.CompraChaveJson;
import br.com.maddytec.efetivacao.gateway.json.PagamentoJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class BancoService {

    @Value("${banco.link}")
    private String link;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public PagamentoRetorno pagar(CompraChaveJson compraChaveJson) throws IOException {

        PagamentoJson pagamentoJson = PagamentoJson.builder()
                .numeroCartao(compraChaveJson.getCompraJson().getNumeroCartao())
                .codigoSegurancaCartao(compraChaveJson.getCompraJson().getCodigoSegurancaCartao())
                .valorCompra(compraChaveJson.getCompraJson().getValorPassagem())
                .build();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PagamentoJson> entity = new HttpEntity<>(pagamentoJson, httpHeaders);

        try {
            ResponseEntity<BancoRetornoJson> bancoRetorno = restTemplate.exchange(link, HttpMethod.POST, entity, BancoRetornoJson.class);
            return new PagamentoRetorno(bancoRetorno.getBody().getMensagem(),true);
        } catch (HttpClientErrorException ex){
            if(ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
                ObjectMapper objectMapper = new ObjectMapper();
                BancoRetornoJson bancoRetornoJson = objectMapper.readValue(ex.getResponseBodyAsString(), BancoRetornoJson.class);
                return  new PagamentoRetorno(bancoRetornoJson.getMensagem(), false);
            }
            throw ex;
        } catch (RuntimeException ex){
            throw ex;
        }
    }
}
