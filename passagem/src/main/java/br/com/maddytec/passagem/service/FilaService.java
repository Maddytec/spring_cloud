package br.com.maddytec.passagem.service;

import br.com.maddytec.passagem.gateway.exception.CompraNaoEnviadaException;
import br.com.maddytec.passagem.gateway.json.CompraChaveJson;
import br.com.maddytec.passagem.gateway.json.RetornoJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

@Log4j2
@Service
public class FilaService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${fila.saida}")
    private String filaSaida;

    public RetornoJson enviar(CompraChaveJson compraChaveJson) throws CompraNaoEnviadaException {
        try {
            log.info("Enviando para fila de compra.");
            rabbitTemplate.convertAndSend(filaSaida, compraChaveJson);
            log.info("Enviado para fila de compra.");

            return RetornoJson.builder()
                    .mensagem("Compra registrada com sucesso. Aguarde a confirmação do pagamento.")
                    .chavePesquisa(compraChaveJson.getChave())
                    .build();

        } catch (Exception ex) {
            throw new CompraNaoEnviadaException(ex.getMessage());
        }
    }
}

