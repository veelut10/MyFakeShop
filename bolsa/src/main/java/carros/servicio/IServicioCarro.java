package carros.servicio;

import carros.modelo.Carro;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioCarro {
	
	public Carro obtenerCarroUsuario(Long idUsuario) throws RepositorioException;

	public Carro agregarProductoToCarro(Long idUsuario, Long idProducto, String nombreProducto, int cantidad, float precioUnidad) throws RepositorioException;

	public Carro actualizarCantidad(Long idUsuario, Long idProducto, int cantidad) throws RepositorioException, EntidadNoEncontrada;

	public void eliminarProducto(Long idUsuario, Long idProducto) throws RepositorioException, EntidadNoEncontrada;

	public void vaciarCarro(Long idUsuario) throws RepositorioException;

	public double calcularTotal(Long idUsuario) throws RepositorioException;

	public void confirmarCompra(Long idUsuario);

}
