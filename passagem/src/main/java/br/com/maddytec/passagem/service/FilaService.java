package br.com.maddytec.passagem.service;

import br.com.maddytec.passagem.gateway.json.CompraChaveJson;
import br.com.maddytec.passagem.gateway.json.RetornoJson;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class FilaService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${fila.saida}")
    private String filaSaida;

    public RetornoJson enviar(CompraChaveJson compraChaveJson) {
        try {
            log.info("Enviando para fila de compra.");
            rabbitTemplate.convertAndSend(filaSaida, compraChaveJson);
            log.info("Enviado para fila de compra.");

            return RetornoJson.builder()
                    .mensagem("Compra registrada com sucesso. Aguarde a confirmação do pagamento.")
                    .chavePesquisa(compraChaveJson.getChave())
                    .build();

        } catch (Exception ex) {
           return RetornoJson.builder()
                    .mensagem("Serviço indisponivel, tente mais tarde.")
                    .build();
        }
    }
}

