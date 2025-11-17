package productos.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import productos.modelo.Categoria;
import productos.modelo.Producto;
import productos.repositorio.RepositorioProducto;
import productos.rest.modelo.ProductoDTO;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@Service
@Transactional
public class ServicioProducto implements IServicioProducto {

	private RepositorioProducto repositorioProducto;

	@Autowired
	public ServicioProducto(RepositorioProducto repositorioProducto) {
		this.repositorioProducto = repositorioProducto;
	}

	@Override
	public Long darAltaProducto(ProductoDTO productoDTO)
			throws RepositorioException {
		if (productoDTO.getNombre() == null || productoDTO.getNombre().isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");
		
		if (productoDTO.getDescripcion() == null || productoDTO.getDescripcion().isEmpty())
			throw new IllegalArgumentException("descripcion: no debe ser nula ni vacia");

		if (productoDTO.getPrecio() < 0)
			throw new IllegalArgumentException("precio: no puede ser nulo");

		if (productoDTO.getStock() <= 0)
			throw new IllegalArgumentException("stock: no puede 0 ni ser nulo");

		if (productoDTO.getCategoria() == null ) {
		    throw new IllegalArgumentException("La categoria es inválida o no proporcionada");
		}

		Producto producto = new Producto(productoDTO.getNombre(), productoDTO.getDescripcion(), productoDTO.getPrecio(), productoDTO.getStock(), productoDTO.getCategoria());
		return repositorioProducto.save(producto).getId();
	}

	@Override
	public void darBajaProducto(Long idProducto) throws RepositorioException, EntidadNoEncontrada {

		Optional<Producto> p = repositorioProducto.findById(idProducto);

		if (p.isPresent()) {
			Producto producto = p.get();
			producto.setActivo(false);
			repositorioProducto.save(producto);
		} else {
			throw new EntidadNoEncontrada("Producto no encontrado con id: " + idProducto);
		}
	}
	
	@Override
	public void activarProducto(Long idProducto) throws RepositorioException, EntidadNoEncontrada {

		Optional<Producto> p = repositorioProducto.findById(idProducto);

		if (p.isPresent()) {
			Producto producto = p.get();
			producto.setActivo(true);
			repositorioProducto.save(producto);
		} else {
			throw new EntidadNoEncontrada("Producto no encontrado con id: " + idProducto);
		}
	}
	
	@Override
	public Producto getProducto(Long idProducto) throws RepositorioException, EntidadNoEncontrada {
		Optional<Producto> p = repositorioProducto.findById(idProducto);

		if (p.isPresent()) {
			Producto producto = p.get();
			return producto;
		} else {
			throw new EntidadNoEncontrada("Producto no encontrado con id: " + idProducto);
		}
	}

	@Override
	public void modificarProducto(Long idProducto, ProductoDTO productoActualizado)
			throws RepositorioException, EntidadNoEncontrada {

		Optional<Producto> p = repositorioProducto.findById(idProducto);

		if (p.isPresent()) {
			Producto producto = p.get();
			producto.setNombre(productoActualizado.getNombre());
			producto.setDescripcion(productoActualizado.getDescripcion());
			producto.setPrecio(productoActualizado.getPrecio());
			producto.setStock(productoActualizado.getStock());
			producto.setCategoria(productoActualizado.getCategoria());
			producto.setActivo(productoActualizado.isActivo());
			repositorioProducto.save(producto);
		} else {
			throw new EntidadNoEncontrada("Producto no encontrado con id: " + idProducto);
		}
	}

	@Override
	public Page<Producto> getListadoProductos(Pageable pageable) throws RepositorioException {
		return repositorioProducto.findAll(pageable);

	}
	
	public Page<Producto> getListadoProductosActivos(Pageable pageable) throws RepositorioException {
		return repositorioProducto.findByActivoTrue(pageable);

	}
	
	public Page<Producto> getListadoProductosInactivos(Pageable pageable) throws RepositorioException {
		return repositorioProducto.findByActivoFalse(pageable);

	}

	@Override
	public Page<Producto> filtrarProductos(Pageable pageable, String nombre, String categoria, Float minPrecio,
			Float maxPrecio) throws RepositorioException {

		Categoria cat = null;
		
		if(categoria != null && !categoria.isBlank()) {

		try {
			cat = Categoria.valueOf(categoria.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("categoria: no es una categoria valida");
		}
	}

		return repositorioProducto.buscar(nombre, cat, minPrecio, maxPrecio, pageable);
	}

}
