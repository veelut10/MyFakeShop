package carros.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import carros.modelo.ItemCarro;

@NoRepositoryBean
public interface RepositorioItemCarro extends JpaRepository<ItemCarro, Long>{
	
	List<ItemCarro> findByCarroId(Long carroId);
    Optional<ItemCarro> findByCarroIdAndProductoId(Long carroId, Long productoId);

}
