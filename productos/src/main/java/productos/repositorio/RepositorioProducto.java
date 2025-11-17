package productos.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import productos.modelo.Categoria;
import productos.modelo.Producto;

@NoRepositoryBean
public interface RepositorioProducto extends JpaRepository<Producto, Long> {

	@Query("""
			    SELECT p FROM Producto p
			    WHERE p.activo = true
			    AND (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
			    AND (:categoria IS NULL OR p.categoria = :categoria)
			    AND (:minPrecio IS NULL OR p.precio >= :minPrecio)
			    AND (:maxPrecio IS NULL OR p.precio <= :maxPrecio)
			""")
	Page<Producto> buscar(@Param("nombre") String nombre, @Param("categoria") Categoria categoria,
			@Param("minPrecio") Float minPrecio, @Param("maxPrecio") Float maxPrecio, Pageable pageable);

	Page<Producto> findByActivoTrue(Pageable pageable);

	Page<Producto> findByActivoFalse(Pageable pageable);
}
