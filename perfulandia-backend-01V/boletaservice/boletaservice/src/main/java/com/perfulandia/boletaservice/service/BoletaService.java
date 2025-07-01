package com.perfulandia.boletaservice.service;

import com.perfulandia.boletaservice.model.Boleta;
import com.perfulandia.boletaservice.model.Producto;
import com.perfulandia.boletaservice.model.Usuario;
import com.perfulandia.boletaservice.repository.BoletaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoletaService {
    private final BoletaRepository repo;
    private final RestTemplate restTemplate;

    public BoletaService(BoletaRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    public List<Boleta> listar() {
        return repo.findAll();
    }

    public Boleta emitirBoleta(Long idUsuario, Long idProducto) {
        // URLS de los otros servicios (ajusta según tus necesidades)
        String usuarioServiceUrl = "http://localhost:8081/api/usuarios/" + idUsuario;
        String productoServiceUrl = "http://localhost:8082/api/productos/" + idProducto;

        // Obtener datos del usuario y producto
        Usuario usuario = restTemplate.getForObject(usuarioServiceUrl, Usuario.class);
        Producto producto = restTemplate.getForObject(productoServiceUrl, Producto.class);

        // Crear y guardar la boleta
        Boleta boleta = Boleta.builder()
                .nombreUsuario(usuario.getNombre())
                .correoUsuario(usuario.getCorreo())
                .nombreProducto(producto.getNombre())
                .precioProducto(producto.getPrecio())
                .fechaEmision(LocalDateTime.now())
                .build();

        return repo.save(boleta);
    }
}