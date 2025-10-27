package productos.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import productos.modelo.Producto;

@NoRepositoryBean
public interface RepositorioProducto extends PagingAndSortingRepository<Producto, Long> {

	@Query("""
	        SELECT p FROM Producto p
	        WHERE (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
	        AND (:categoria IS NULL OR LOWER(p.categoria) = LOWER(:categoria))
	        AND (:minPrecio IS NULL OR p.precio >= :minPrecio)
	        AND (:maxPrecio IS NULL OR p.precio <= :maxPrecio)
	    """)
	    Page<Producto> buscar(
	        @Param("nombre") String nombre,
	        @Param("categoria") String categoria,
	        @Param("minPrecio") float minPrecio,
	        @Param("maxPrecio") float maxPrecio,
	        Pageable pageable
	    );
}
