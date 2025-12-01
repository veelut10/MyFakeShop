package carros.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import carros.modelo.Carro;
import carros.modelo.ItemCarro;
import carros.repositorio.RepositorioCarro;
import carros.repositorio.RepositorioItemCarro;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@Service
@Transactional
public class ServicioCarro implements IServicioCarro {

	private RepositorioCarro repositorioCarro;
	private RepositorioItemCarro repositorioItemCarro;

	@Autowired
	public ServicioCarro(RepositorioCarro repositorioCarro, RepositorioItemCarro repositorioItemCarro) {
		this.repositorioCarro = repositorioCarro;
		this.repositorioItemCarro = repositorioItemCarro;
	}

	@Override
	public Carro obtenerCarroUsuario(Long idUsuario) throws RepositorioException{

		if (idUsuario == null || idUsuario < 0)
			throw new IllegalArgumentException("idUsuario: no puede ser menor que 0 o null");

		Optional<Carro> c = repositorioCarro.findByUsuarioId(idUsuario);
		if (c.isEmpty()) {
			Carro carro = new Carro(idUsuario);
			return repositorioCarro.save(carro);
		} else {
			return c.get();
		}
	}

	@Override
	public Carro agregarProductoToCarro(Long idUsuario, Long idProducto, String nombreProducto, int cantidad, float precioUnidad) throws RepositorioException{
		
		if (idUsuario == null || idUsuario < 0)
			throw new IllegalArgumentException("idUsuario: no puede ser menor que 0 o null");
		
		if (idProducto == null || idProducto < 0)
			throw new IllegalArgumentException("idProducto: no puede ser menor que 0 o null");
		
		if (nombreProducto == null || nombreProducto.isEmpty())
			throw new IllegalArgumentException("nombreProducto: no debe ser nula ni vacia");
		
		if (cantidad <= 0)
			throw new IllegalArgumentException("cantidad: no puede ser menor o igual a 0");
		
		Carro carro = obtenerCarroUsuario(idUsuario);
		
		Optional<ItemCarro> itemCarroOptional = repositorioItemCarro.findByCarroIdAndProductoId(carro.getId(), idProducto);
		
		ItemCarro itemCarro;
		
		if(itemCarroOptional.isEmpty()) {
			itemCarro = new ItemCarro(idProducto, nombreProducto, cantidad, precioUnidad, carro);
			carro.addProducto(itemCarro);
		}
		else {
			itemCarro = itemCarroOptional.get();
			itemCarro.setCantidad(cantidad);
		}
		
		return repositorioCarro.save(carro);
	}

	@Override
	public Carro actualizarCantidad(Long idUsuario, Long idProducto, int cantidad) throws RepositorioException, EntidadNoEncontrada {
		
		if (idUsuario == null || idUsuario < 0)
			throw new IllegalArgumentException("idUsuario: no puede ser menor que 0 o null");
		
		if (idProducto == null || idProducto < 0)
			throw new IllegalArgumentException("idProducto: no puede ser menor que 0 o null");
		
		if (cantidad <= 0)
			throw new IllegalArgumentException("cantidad: no puede ser menor o igual a 0");
		
		Carro carro = obtenerCarroUsuario(idUsuario);
		
		Optional<ItemCarro> itemCarroOptional = repositorioItemCarro.findByCarroIdAndProductoId(carro.getId(), idProducto);
		
		if(itemCarroOptional.isPresent()) {
			ItemCarro itemCarro = itemCarroOptional.get();
			itemCarro.setCantidad(cantidad);
			return repositorioCarro.save(carro);
		}
		else {
			throw new EntidadNoEncontrada("Producto con id: " + idProducto + " en carro con id: " + carro.getId() + " no encontrado");
		}
	}

	@Override
	public void eliminarProducto(Long idUsuario, Long idProducto) throws RepositorioException , EntidadNoEncontrada{

		if (idUsuario == null || idUsuario < 0)
			throw new IllegalArgumentException("idUsuario: no puede ser menor que 0 o null");
		
		if (idProducto == null || idProducto < 0)
			throw new IllegalArgumentException("idProducto: no puede ser menor que 0 o null");
		
		Carro carro = obtenerCarroUsuario(idUsuario);
		
		Optional<ItemCarro> itemCarroOptional = repositorioItemCarro.findByCarroIdAndProductoId(carro.getId(), idProducto);
	
		
		if(itemCarroOptional.isPresent()) {
			ItemCarro itemCarro = itemCarroOptional.get();
			carro.removeProducto(itemCarro);
			repositorioCarro.save(carro);
		}
		else {
			throw new EntidadNoEncontrada("Producto con id: " + idProducto + " en carro con id: " + carro.getId() + " no encontrado");
		}
		
	}

	@Override
	public void vaciarCarro(Long idUsuario) throws RepositorioException {

		if (idUsuario == null || idUsuario < 0)
			throw new IllegalArgumentException("idUsuario: no puede ser menor que 0 o null");
		
		Carro carro = obtenerCarroUsuario(idUsuario);
		
		carro.getProductos().clear();
	}

	@Override
	public void confirmarCompra(Long idUsuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public double calcularTotal(Long idUsuario) throws RepositorioException {
		if (idUsuario == null || idUsuario < 0)
			throw new IllegalArgumentException("idUsuario: no puede ser menor que 0 o null");
		
		Carro carro = obtenerCarroUsuario(idUsuario);
		
		return carro.getPrecioTotal();
	}

}
