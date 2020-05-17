package br.com.maddytec.efetivacao.service.processar;

import br.com.maddytec.efetivacao.gateway.json.CompraChaveJson;
import br.com.maddytec.efetivacao.gateway.json.CompraFinalizadaJson;
import br.com.maddytec.efetivacao.service.banco.BancoService;
import br.com.maddytec.efetivacao.service.banco.PagamentoRetorno;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
public class ListenerService {

    @Autowired
    private BancoService bancoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${fila.entrada}")
    private String filaRepublicar;

    @Value("${fila.finalizado}")
    private String filaFinalizado;

    @HystrixCommand(fallbackMethod = "republicarOnMessage")
    @RabbitListener(queues = "${fila.entrada}")
    public void onMessage(Message message) throws JsonParseException, JsonMappingException, IOException {

        String json = new String(message.getBody(), "UTF-8");
        log.info("Mensagem recebida: " + json);

        ObjectMapper objectMapper = new ObjectMapper();
        CompraChaveJson compraChaveJson = objectMapper.readValue(json, CompraChaveJson.class);

        PagamentoRetorno pagamentoRetorno = bancoService.pagar(compraChaveJson);

        CompraFinalizadaJson compraFinalizadaJson = CompraFinalizadaJson.builder()
                .compraChaveJson(compraChaveJson)
                .pagamentoOK(pagamentoRetorno.getPagamentoOK())
                .mensagem(pagamentoRetorno.getMensagem())
                .build();

        ObjectMapper obj = new ObjectMapper();
        String jsonFinalizado = obj.writeValueAsString(compraFinalizadaJson);
        rabbitTemplate.convertAndSend(filaFinalizado, jsonFinalizado);
    }

    public void republicarOnMessage(Message message) throws JsonParseException, JsonMappingException, IOException {
        log.info("Republicando mensagem.");
        rabbitTemplate.convertAndSend(filaRepublicar, message);
    }

}
