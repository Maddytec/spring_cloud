package br.com.maddytec.feedback.service.finalizar;

import br.com.maddytec.feedback.domain.CompraRedis;
import br.com.maddytec.feedback.gataway.json.CompraFinalizadaJson;
import br.com.maddytec.feedback.gataway.repository.CompraRedisRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
public class ListenerService {

    @Autowired
    private CompraRedisRepository compraRedisRepository;

    @RabbitListener(queues = "${fila.finalizado}")
    public void onMessage(Message message) throws JsonParseException, JsonMappingException, IOException{
        String json = new String(message.getBody(), "UTF-8");
        log.info("Mensagem recebida: " + json);

        ObjectMapper objectMapper = new ObjectMapper();
        CompraFinalizadaJson compraFinalizadaJson = objectMapper.readValue(json, CompraFinalizadaJson.class);

        CompraRedis compraRedis = CompraRedis.builder()
                .id(compraFinalizadaJson.getCompraChaveJson().getChave())
                .mensagem(compraFinalizadaJson.getMensagem())
                .numeroCartao(compraFinalizadaJson.getCompraChaveJson().getCompraJson().getNumeroCartao())
                .valorPassagem(compraFinalizadaJson.getCompraChaveJson().getCompraJson().getValorPassagem())
                .codigoPassagem(compraFinalizadaJson.getCompraChaveJson().getCompraJson().getCodigoPassagem())
                .pagamentoOK(compraFinalizadaJson.getPagamentoOK())
                .build();

        log.info("Enviando para o redis...");
        compraRedisRepository.save(compraRedis);
    }

}
