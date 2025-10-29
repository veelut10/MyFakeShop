package productos;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;

import productos.modelo.Producto;
import productos.servicio.ServicioProducto;
import repositorio.RepositorioException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductosApplication {

	public static void main(String[] args) throws RepositorioException {
		ApplicationContext context = SpringApplication.run(ProductosApplication.class, args);
		
		ServicioProducto servicioProducto = context.getBean(ServicioProducto.class);
		
		Long id = servicioProducto.darAltaProducto("nombre1", "descripcion1", 10, 100, "fruta");
		
		var listado = servicioProducto.getListadoProductos(PageRequest.of(0, 10));

        Producto p = listado.getContent().get(0);

        p.toString();
	}

}
