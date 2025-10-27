package productos.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import productos.modelo.Producto;

public interface IServicioProducto {

	Long altaProducto(String nombre, String descripcion, float precio, int stock, String categoria);
	
	void bajaProducto(Long idProducto, String motivoBaja);
	
	Page<Producto> getListadoProductos(Pageable pageable);
	
	Page<Producto> filtrarProductos(Pageable pageable, String nombre, String categoria, float minPrecio, float maxPrecio);
}
