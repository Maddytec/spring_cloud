package br.com.maddytec.passagem.gateway.http;

import br.com.maddytec.passagem.gateway.json.CompraChaveJson;
import br.com.maddytec.passagem.gateway.json.CompraJson;
import br.com.maddytec.passagem.gateway.json.RetornoJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("compra")
public class CompraController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${fila.saida}")
    private String filaSaida;


    @PostMapping
    public ResponseEntity<RetornoJson> comprar(
            @Valid @NotNull @RequestBody CompraJson compraJson) throws Exception{

        CompraChaveJson compraChaveJson = CompraChaveJson.builder()
        .compraJson(compraJson)
        .chave(UUID.randomUUID().toString())
        .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(compraChaveJson);

       log.info("Enviando para fila de compra.");
        rabbitTemplate.convertAndSend(filaSaida, json);
        log.info("Enviado para fila de compra.");

        RetornoJson retornoJson = RetornoJson.builder()
                .mensagem("Compra registrada com sucesso. Aguarde a confirmação do pagamento.")
                .chavePesquisa(compraChaveJson.getChave())
                .build();

        return new ResponseEntity<RetornoJson>(retornoJson, HttpStatus.OK);
    }

}
