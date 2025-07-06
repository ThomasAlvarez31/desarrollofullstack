package com.perfulandia.boletaservice.assembler;

import com.perfulandia.boletaservice.controller.BoletaController;
import com.perfulandia.boletaservice.model.Boleta;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BoletaModelAssembler implements RepresentationModelAssembler<Boleta, EntityModel<Boleta>> {
    @Override
    public EntityModel<Boleta> toModel(Boleta boleta) {
        return EntityModel.of(boleta,
                linkTo(methodOn(BoletaController.class).emitir(1L, 1L)).withRel("emitir"),
                linkTo(methodOn(BoletaController.class).listar()).withRel("listar"));
    }
}
