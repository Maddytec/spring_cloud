package br.com.maddytec.feedback.gataway.http;

import br.com.maddytec.feedback.domain.CompraRedis;
import br.com.maddytec.feedback.exceptions.NaoFinalizadoException;
import br.com.maddytec.feedback.gataway.repository.CompraRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CompraController {

    @Autowired
    private CompraRedisRepository compraRedisRepository;

    @GetMapping("/{chave}")
    public CompraRedis status(@PathVariable("chave") String chave){
        Optional<CompraRedis> compraRedisOptional = compraRedisRepository.findById(chave);

        if (!compraRedisOptional.isPresent()){
            throw new NaoFinalizadoException();
        }

        return compraRedisOptional.get();
    }
}
