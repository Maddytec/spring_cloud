package br.com.maddytec.efetivacao.configuracao;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EfetivacaoRabbitConfiguration {

    @Value("${fila.entrada}")
    private String queueEntrada;

    @Value("${fila.saida}")
    private String queueSaida;

    @Bean
    Queue queueEntrada(){
        return new Queue(queueEntrada, true, false,false);
    }

    @Bean
    Queue queueSaida(){
        return new Queue(queueSaida, true, false,false);
    }

    @Bean
    public Binding bindingQueueEntrada(){
        return BindingBuilder.bind(queueEntrada())
                .to(DirectExchange.DEFAULT)
                .with(queueEntrada);
    }

    @Bean
    public Binding bindingQueueSaida(){
        return BindingBuilder.bind(queueSaida())
                .to(DirectExchange.DEFAULT)
                .with(queueSaida);
    }

}
