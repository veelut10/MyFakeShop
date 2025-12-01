package carros.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class ItemCarro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long productoId;
	private String nombreProducto;
	private int cantidad;
	private float precioUnidad;

	@ManyToOne
	@JoinColumn(name = "carro_id")
	private Carro carro;

	public ItemCarro() {

	}

	public ItemCarro(Long productoId, String nombre, int cantidad, float precioUnidad, Carro carro) {
		super();
		this.productoId = productoId;
		this.nombreProducto = nombre;
		this.cantidad = cantidad;
		this.precioUnidad = precioUnidad;
		this.carro = carro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductoId() {
		return productoId;
	}

	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}

	public String getNombre() {
		return nombreProducto;
	}

	public void setNombre(String nombre) {
		this.nombreProducto = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecioUnidad() {
		return precioUnidad;
	}

	public void setPrecioUnidad(float precioUnidad) {
		this.precioUnidad = precioUnidad;
	}

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	
	@Transient
	public float getPrecioTotal() {
		return cantidad * precioUnidad;
	}
}
