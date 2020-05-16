package br.com.maddytec.banco.gateway.http;

import br.com.maddytec.banco.gateway.json.PagamentoJson;
import br.com.maddytec.banco.gateway.json.RetornoJson;
import br.com.maddytec.banco.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(name = "Pagamento", path = "pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<RetornoJson> pagamento(@Valid @NotNull @RequestBody PagamentoJson pagamentoJson){
        pagamentoService.pagamento(pagamentoJson);

        RetornoJson retornoJson = RetornoJson.builder()
                .mensagem("Pagamento realizado com sucesso.")
                .build();
        return new ResponseEntity<RetornoJson>(retornoJson, HttpStatus.OK);
    }

    @GetMapping
    public String teste(){
        return "OK";
    }

}
