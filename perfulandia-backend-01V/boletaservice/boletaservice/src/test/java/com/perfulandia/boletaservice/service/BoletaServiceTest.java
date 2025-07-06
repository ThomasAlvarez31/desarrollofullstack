package com.perfulandia.boletaservice.service;

import com.perfulandia.boletaservice.model.Boleta;
import com.perfulandia.boletaservice.model.Producto;
import com.perfulandia.boletaservice.model.Usuario;
import com.perfulandia.boletaservice.repository.BoletaRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BoletaServiceTest {

    @Mock
    BoletaRepository repo;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    BoletaService service;

    public BoletaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEmitirBoleta() {
        Usuario user = new Usuario(1L, "Carlos", "carlos@mail.com", "Cliente");
        Producto prod = new Producto(1L, "Perfume X", 25000, 5);

        when(restTemplate.getForObject("http://localhost:8081/api/usuarios/1", Usuario.class)).thenReturn(user);
        when(restTemplate.getForObject("http://localhost:8082/api/productos/1", Producto.class)).thenReturn(prod);

        Boleta mockBoleta = Boleta.builder()
                .nombreUsuario("Carlos")
                .correoUsuario("carlos@mail.com")
                .nombreProducto("Perfume X")
                .precioProducto(25000)
                .fechaEmision(LocalDateTime.now())
                .build();

        when(repo.save(any())).thenReturn(mockBoleta);

        Boleta resultado = service.emitirBoleta(1L, 1L);

        assertEquals("Carlos", resultado.getNombreUsuario());
        verify(repo).save(any());
    }
}
