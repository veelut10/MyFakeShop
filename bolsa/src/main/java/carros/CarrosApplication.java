package carros;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import carros.modelo.Carro;
import carros.servicio.ServicioCarro;

@SpringBootApplication
public class CarrosApplication {

	public static void main(String[] args) throws Exception{
		//SpringApplication.run(CarroApplication.class, args);
		
		ApplicationContext context = SpringApplication.run(CarrosApplication.class, args);
		
		ServicioCarro servicio = context.getBean(ServicioCarro.class);
		
		Carro carro = servicio.obtenerCarroUsuario((long) 1.0);
		
		carro = servicio.agregarProductoToCarro(1l, 5l, "LA ÑAPA", 7, 10f);
		
		System.out.println(servicio.calcularTotal(1l));
		
		carro = servicio.agregarProductoToCarro(1l, 6l, "LA CRETA", 4, 5f);
		
		System.out.println(servicio.calcularTotal(1l));
		
		servicio.actualizarCantidad(1l, 5l, 6);
		
		System.out.println(servicio.calcularTotal(1l));
		
		servicio.eliminarProducto(1l, 5l);
		
		System.out.println(servicio.calcularTotal(1l));
		
		servicio.vaciarCarro(1l);
		
		System.out.println(servicio.calcularTotal(1l));
	}

}
