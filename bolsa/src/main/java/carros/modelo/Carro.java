package carros.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Carro {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long usuarioId;
	
	@OneToMany(mappedBy = "carro", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemCarro> productos = new ArrayList<ItemCarro>();;
	
	public Carro() {
		
	}	

	public Carro(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public List<ItemCarro> getProductos() {
		return productos;
	}

	public void setProductos(List<ItemCarro> productos) {
		this.productos = productos;
	}
	
	public void addProducto(ItemCarro producto) {
		productos.add(producto);
	}
	
	public void removeProducto(ItemCarro producto) {
		productos.remove(producto);
	}
	
	@Transient
	public float getPrecioTotal() {
		return productos.stream().map(ItemCarro::getPrecioTotal).reduce(0f, Float::sum);
	}
}
