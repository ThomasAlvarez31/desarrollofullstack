package com.perfulandia.boletaservice.controller;

import com.perfulandia.boletaservice.assembler.BoletaModelAssembler;
import com.perfulandia.boletaservice.model.Boleta;
import com.perfulandia.boletaservice.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Boleta Controller", description = "Controlador para emisión y listado de boletas")
@RestController
@RequestMapping("/api/boletas")
public class BoletaController {

    private final BoletaService service;
    private final BoletaModelAssembler assembler;

    public BoletaController(BoletaService service, BoletaModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar boletas")
    @ApiResponse(responseCode = "200", description = "Listado de boletas obtenido correctamente")
    @GetMapping
    public List<Boleta> listar() {
        return service.listar();
    }

    @Operation(summary = "Emitir boleta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Boleta emitida"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/emitir")
    public Boleta emitir(@RequestParam Long usuarioId, @RequestParam Long productoId) {
        return service.emitirBoleta(usuarioId, productoId);
    }

    @Operation(summary = "Listar boletas con links HATEOAS")
    @GetMapping("/hateoas")
    public CollectionModel<EntityModel<Boleta>> listarConLinks() {
        List<EntityModel<Boleta>> boletas = service.listar().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(boletas,
                linkTo(methodOn(BoletaController.class).listarConLinks()).withSelfRel());
    }
}
