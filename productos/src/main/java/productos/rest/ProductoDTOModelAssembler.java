package productos.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import productos.rest.modelo.ProductoDTO;

@Component
public class ProductoDTOModelAssembler implements RepresentationModelAssembler<ProductoDTO, EntityModel<ProductoDTO>> {

	@Override
	public EntityModel<ProductoDTO> toModel(ProductoDTO productoDTO) {
		EntityModel<ProductoDTO> model = EntityModel.of(productoDTO);

		// Self
		try {
			model.add(linkTo(methodOn(ProductosController.class).getProducto(productoDTO.getId())).withSelfRel());

			// Volver al listado
			model.add(linkTo(methodOn(ProductosController.class).getListadoProductos(0, 10)).withRel("productos"));

			// Si activo → link para desactivar
			if (productoDTO.isActivo()) {
				model.add(linkTo(methodOn(ProductosController.class).darBajaProducto(productoDTO.getId()))
						.withRel("desactivar"));
			}
			// Si inactivo → link para activar
			else {
				model.add(linkTo(methodOn(ProductosController.class).activarProducto(productoDTO.getId()))
						.withRel("activar"));
			}

			// Link para modificar
			model.add(linkTo(methodOn(ProductosController.class).modificarProducto(productoDTO.getId(), null))
					.withRel("modificar"));

		} catch (Exception e) {
			throw new RuntimeException("Error creando enlaces HATEOAS", e);
		}
		return model;
	}
}
