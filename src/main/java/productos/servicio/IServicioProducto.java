package productos.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import productos.modelo.Categoria;
import productos.modelo.Producto;
import productos.rest.modelo.ProductoDTO;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioProducto {

	Long darAltaProducto(ProductoDTO productoDTO) throws RepositorioException;
	
	void darBajaProducto(Long idProducto) throws RepositorioException, EntidadNoEncontrada;
	
	void activarProducto(Long idProducto) throws RepositorioException, EntidadNoEncontrada;
	
	Producto getProducto(Long idProducto) throws RepositorioException, EntidadNoEncontrada;
	
	void modificarProducto(Long idProducto, ProductoDTO productoActualizado) throws RepositorioException, EntidadNoEncontrada;
	
	Page<Producto> getListadoProductos(Pageable pageable) throws RepositorioException;
	
	Page<Producto> getListadoProductosActivos(Pageable pageable) throws RepositorioException;
	
	Page<Producto> getListadoProductosInactivos(Pageable pageable) throws RepositorioException;
	
	Page<Producto> filtrarProductos(Pageable pageable, String nombre, String categoria, Float minPrecio, Float maxPrecio) throws RepositorioException;

}
