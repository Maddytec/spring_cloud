package br.com.maddytec.feedback.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@RedisHash("compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraRedis {

    @Id
    private String id;
    private String mensagem;

    private Long codigoPassagem;
    private Long numeroCartao;
    private BigDecimal valorPassagem;

    private Boolean pagamentoOK;

}
