package br.com.maddytec.banco.gateway.repository;

import br.com.maddytec.banco.domain.Pagamento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends CrudRepository<Pagamento, Long> {
}
