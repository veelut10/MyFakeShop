package productos.rest.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import productos.modelo.Categoria;

public class ProductoDTO {
	
	private Long id;
	
	@NotNull(message = "La descripción no puede ser nula")
	@NotBlank(message = "El nombre no puede estar vacío")
	private String nombre;
	
	@NotNull(message = "La descripción no puede ser nula")
	@NotBlank(message = "La descripción no puede estar vacía")
	private String descripcion;
	private Float precio;
	private Integer stock;
	private Categoria categoria;
	private Boolean activo;
	
	public ProductoDTO() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Float getPrecio() {
		return precio;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Boolean isActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
