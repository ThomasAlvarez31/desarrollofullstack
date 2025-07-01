package com.perfulandia.boletaservice.controller;

import com.perfulandia.boletaservice.model.Boleta;
import com.perfulandia.boletaservice.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Boleta Controller", description = "Controlador para emisión y listado de boletas")
@RestController
@RequestMapping("/api/boletas")
public class BoletaController {
    private final BoletaService service;

    public BoletaController(BoletaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar boletas")
    @ApiResponse(responseCode = "200", description = "Listado de boletas obtenido correctamente")
    @GetMapping
    public List<Boleta> listar() {
        return service.listar();
    }

    @Operation(summary = "Emitir boleta", description = "Genera una boleta para un usuario y un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Boleta emitida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes")
    })
    @PostMapping("/emitir")
    public Boleta emitir(@RequestParam Long usuarioId, @RequestParam Long productoId) {
        return service.emitirBoleta(usuarioId, productoId);
    }
}