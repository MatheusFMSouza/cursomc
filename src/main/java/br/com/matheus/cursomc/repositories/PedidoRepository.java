package br.com.matheus.cursomc.repositories;

import br.com.matheus.cursomc.domain.Cliente;
import br.com.matheus.cursomc.domain.Pedido;
import org.hibernate.cache.spi.entry.StructuredCacheEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {


    @Transactional(readOnly = true)
    Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);

}
