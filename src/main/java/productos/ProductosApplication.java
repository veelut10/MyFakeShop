package productos;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;

import productos.modelo.Categoria;
import productos.modelo.Producto;
import productos.rest.modelo.ProductoDTO;
import productos.servicio.ServicioProducto;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductosApplication {

	public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada {
		SpringApplication.run(ProductosApplication.class, args);
		
		/*
		
		ApplicationContext context = SpringApplication.run(ProductosApplication.class, args);
		
		ServicioProducto servicioProducto = context.getBean(ServicioProducto.class);
		 
		ProductoDTO producto = new ProductoDTO();
		producto.setNombre("nombre2");
		producto.setDescripcion("Descripcion2");
		producto.setPrecio( (float) 10.01);
		producto.setStock(100);
		producto.setCategoria(Categoria.ALIMENTACION);
		Long id = servicioProducto.darAltaProducto(producto);
		
		var listado = servicioProducto.getListadoProductos(PageRequest.of(0, 10));

        Producto p = listado.getContent().get(0);
        
        System.out.println(p.toString());
        
        ProductoDTO producto2 = new ProductoDTO();
		producto2.setNombre("nombre3");
		producto2.setDescripcion("Descripcion3");
		producto2.setPrecio( (float) 10.01);
		producto2.setStock(100);
		producto2.setCategoria(Categoria.DEPORTE);
		producto2.setActivo(true);
		servicioProducto.modificarProducto(id, producto2);

		listado = servicioProducto.getListadoProductos(PageRequest.of(0, 10));

        p = listado.getContent().get(0);
        
        System.out.println(p.toString());
        
        listado = servicioProducto.filtrarProductos(PageRequest.of(0, 10), null, "Deporte", null, null);
        
        
        
        
	    listado = servicioProducto.getListadoProductos(PageRequest.of(0, 10));

        System.out.println("Numero de productos: " + listado.getContent().size());
        
        listado = servicioProducto.getListadoProductosActivos(PageRequest.of(0, 10));

        System.out.println("Numero de productos activos: " + listado.getContent().size());
        
        listado = servicioProducto.filtrarProductos(PageRequest.of(0, 10), null, "Deporte", null, null);

        System.out.println("Numero de productos filtrados: " + listado.getContent().size());
        */
	}

}
