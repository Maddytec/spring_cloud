package br.com.maddytec.passagem.configuracao;

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
public class PassagemRabbitConfiguration {

    @Value("${fila.saida}")
    private String queue;

    @Bean
    Queue queue(){
        return new Queue(queue, true, false,false);
    }

    @Bean
    public Binding bindingQueue(){
        return BindingBuilder.bind(queue())
                .to(DirectExchange.DEFAULT)
                .with(queue);
    }

}
