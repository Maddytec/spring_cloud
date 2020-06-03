package br.com.maddytec.feedback.gataway.repository;

import br.com.maddytec.feedback.domain.CompraRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRedisRepository extends CrudRepository<CompraRedis, String> {
}
