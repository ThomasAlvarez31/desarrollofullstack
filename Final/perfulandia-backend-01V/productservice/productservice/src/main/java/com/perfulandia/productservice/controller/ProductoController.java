package com.perfulandia.productservice.controller;

import com.perfulandia.productservice.assembler.ProductoModelAssembler;
import com.perfulandia.productservice.model.Producto;
import com.perfulandia.productservice.model.Usuario;
import com.perfulandia.productservice.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Producto Controller", description = "Controlador para gestión de productos")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService servicio;
    private final RestTemplate restTemplate;
    private final ProductoModelAssembler assembler;

    public ProductoController(ProductoService servicio, RestTemplate restTemplate, ProductoModelAssembler assembler) {
        this.servicio = servicio;
        this.restTemplate = restTemplate;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar productos")
    @GetMapping
    public List<Producto> listar() {
        return servicio.listar();
    }

    @Operation(summary = "Guardar producto")
    @PostMapping
    public Producto guardar(@RequestBody Producto producto) {
        return servicio.guardar(producto);
    }

    @Operation(summary = "Buscar producto por ID")
    @GetMapping("/{id}")
    public Producto buscar(@PathVariable long id) {
        return servicio.buscarPorId(id);
    }

    @Operation(summary = "Eliminar producto por ID")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable long id) {
        servicio.eliminar(id);
    }

    @Operation(summary = "Obtener usuario por ID desde UsuarioService")
    @GetMapping("/usuario/{id}")
    public Usuario obtenerUsuario(@PathVariable long id) {
        return restTemplate.getForObject("http://localhost:8085/api/usuarios/" + id, Usuario.class);
    }

    @Operation(summary = "Listar productos con HATEOAS")
    @GetMapping("/hateoas")
    public CollectionModel<EntityModel<Producto>> listarConLinks() {
        List<EntityModel<Producto>> productos = servicio.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listarConLinks()).withSelfRel());
    }
}
