package com.perfulandia.productservice.repository;

import com.perfulandia.productservice.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
