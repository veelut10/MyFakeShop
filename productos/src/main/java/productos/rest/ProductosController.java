package productos.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import productos.modelo.Producto;
import productos.rest.modelo.ProductoDTO;
import productos.servicio.IServicioProducto;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

//ESPECIFICACIÓN OpenAPI E INTERFAZ Swagger UI
//http://localhost:8080/api/productos para obtener el token
//http://localhost:8080/v3/api-docs
//http://localhost:8080/swagger-ui.html
@RestController
@RequestMapping("/api/productos")
public class ProductosController {
	private IServicioProducto servicioProducto;

	@Autowired
	private PagedResourcesAssembler<ProductoDTO> pagedResourcesAssemblerProductoDTO;
	
	private ProductoDTOModelAssembler assembler;


	@Autowired
	public ProductosController(IServicioProducto servicioProducto, ProductoDTOModelAssembler assembler) {
		this.servicioProducto = servicioProducto;
		this.assembler = assembler;
	}
	
	// Dar de alta
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> darAltaProducto(@Valid @RequestBody ProductoDTO producto) throws RepositorioException {
    	Long idProducto = servicioProducto.darAltaProducto(producto);

    	URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(idProducto).toUri();
    	
    	return ResponseEntity.created(nuevaURL).build();
    }
    
    // Dar baja
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> darBajaProducto(@PathVariable Long id) throws RepositorioException, EntidadNoEncontrada {
    	servicioProducto.darBajaProducto(id);
    	return ResponseEntity.noContent().build();
    }

    // Activar producto
    @PatchMapping("/{id}")
    public ResponseEntity<Void> activarProducto(@PathVariable Long id) throws RepositorioException, EntidadNoEncontrada {
        servicioProducto.activarProducto(id);
        return ResponseEntity.noContent().build();
    }
    
    // Obtener producto
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductoDTO>> getProducto(@PathVariable Long id) throws EntidadNoEncontrada, RepositorioException {
        Producto producto = servicioProducto.getProducto(id);
        ProductoDTO productoDTO = fromProductoToDTO(producto);
        return ResponseEntity.ok(assembler.toModel(productoDTO));
     
    }

    // Modificar producto
    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> modificarProducto(@PathVariable Long id, @RequestBody ProductoDTO nuevo)
            throws RepositorioException, EntidadNoEncontrada {
    	servicioProducto.modificarProducto(id, nuevo);
    	return ResponseEntity.noContent().build();
    }

    // Listar todos paginados (activos + inactivos)
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ProductoDTO>>> getListadoProductos(
    		@RequestParam int page,
			@RequestParam int size) throws RepositorioException {
    	
    	Pageable paginacion = PageRequest.of(page, size, Sort.by("id").ascending());
    	
        Page<ProductoDTO> listado = servicioProducto.getListadoProductos(paginacion).map((producto) -> {
        	ProductoDTO productoDTO = fromProductoToDTO(producto);
        	return productoDTO;
        });
        
        PagedModel<EntityModel<ProductoDTO>> pagedModel =
        		pagedResourcesAssemblerProductoDTO.toModel(listado, assembler);

        return ResponseEntity.ok(pagedModel);
    }

    // Listar activos
    @GetMapping("/activos")
    public ResponseEntity<PagedModel<EntityModel<ProductoDTO>>> getListadoProductosActivos(
    		@RequestParam int page,
			@RequestParam int size) throws RepositorioException {
    	
    	Pageable paginacion = PageRequest.of(page, size, Sort.by("id").ascending());

    	Page<ProductoDTO> listado = servicioProducto.getListadoProductosActivos(paginacion).map((producto) -> {
        	ProductoDTO productoDTO = fromProductoToDTO(producto);
        	return productoDTO;
        });
    	
    	PagedModel<EntityModel<ProductoDTO>> pagedModel =
        		pagedResourcesAssemblerProductoDTO.toModel(listado, assembler);

        return ResponseEntity.ok(pagedModel);
    }

    // Listar inactivos
    @GetMapping("/inactivos")
    public ResponseEntity<PagedModel<EntityModel<ProductoDTO>>> getListadoProductosInactivos(
    		@RequestParam int page,
			@RequestParam int size) throws RepositorioException {

    	Pageable paginacion = PageRequest.of(page, size, Sort.by("id").ascending());

    	Page<ProductoDTO> listado = servicioProducto.getListadoProductosInactivos(paginacion).map((producto) -> {
        	ProductoDTO productoDTO = fromProductoToDTO(producto);
        	return productoDTO;
        });
    	
    	PagedModel<EntityModel<ProductoDTO>> pagedModel =
        		pagedResourcesAssemblerProductoDTO.toModel(listado, assembler);

        return ResponseEntity.ok(pagedModel);
    }

    // Filtrar con paginación
    @GetMapping("/filtrar")
    public ResponseEntity<PagedModel<EntityModel<ProductoDTO>>> filtrarProductos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Float minPrecio,
            @RequestParam(required = false) Float maxPrecio,
            @RequestParam int page,
			@RequestParam int size
    ) throws RepositorioException {
    	
    	Pageable paginacion = PageRequest.of(page, size, Sort.by("id").ascending());
        
        Page<ProductoDTO> listado = servicioProducto.filtrarProductos(paginacion, nombre, categoria, minPrecio, maxPrecio).map((producto) -> {
        	ProductoDTO productoDTO = fromProductoToDTO(producto);
        	return productoDTO;
        });
        
        PagedModel<EntityModel<ProductoDTO>> pagedModel =
        		pagedResourcesAssemblerProductoDTO.toModel(listado, assembler);

        return ResponseEntity.ok(pagedModel);
    }
    
    private ProductoDTO fromProductoToDTO(Producto producto) {
    	ProductoDTO productoDTO = new ProductoDTO();
    	productoDTO.setId(producto.getId());
    	productoDTO.setNombre(producto.getNombre());
    	productoDTO.setDescripcion(producto.getDescripcion());
    	productoDTO.setPrecio(producto.getPrecio());
    	productoDTO.setStock(producto.getStock());
    	productoDTO.setCategoria(producto.getCategoria());
    	productoDTO.setActivo(producto.isActivo());
    	return productoDTO;
    }
	
}
