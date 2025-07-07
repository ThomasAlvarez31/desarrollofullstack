package com.perfulandia.boletaservice.controller;

import com.perfulandia.boletaservice.assembler.BoletaModelAssembler;
import com.perfulandia.boletaservice.model.*;
import com.perfulandia.boletaservice.service.BoletaService;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.hateoas.EntityModel;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(BoletaController.class)
public class BoletaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoletaService service;

    @MockBean
    private BoletaModelAssembler assembler;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testListar() throws Exception{
        Boleta boleta = new Boleta();
        boleta.setId(1L);

        when(service.listar()).thenReturn(List.of(boleta));
        mockMvc.perform(get("/api/boletas"))
                .andExpect(status().isOk());
    }
    @Test
    void testListarHateoas() throws Exception{
        Boleta boleta = new Boleta();
        boleta.setId(1L);

        EntityModel<Boleta> boletaModel = EntityModel.of(boleta);

        when(service.listar()).thenReturn(List.of(boleta));
        when(assembler.toModel(any(Boleta.class))).thenReturn(boletaModel);
        mockMvc.perform(get("/api/boletas/hateoas"))
                .andExpect(status().isOk());
    }
    @Test
    void testEmitirBoleta() throws Exception{
        Boleta boleta = new Boleta();
        boleta.setId(1L);

        when(service.emitirBoleta(1L,1L)).thenReturn(boleta);
        mockMvc.perform(post("/api/boletas/emitir")
                .param("usuarioId", "1")
                .param("productoId", "1"))
            .andExpect(status().isOk());
    }
}
