package com.huertohogar.huerto_api.service.productos;

import com.huertohogar.huerto_api.model.Productos.Producto;
import com.huertohogar.huerto_api.repository.Productos.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;

    public ProductoServiceImpl(ProductoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Producto> listar() {
        return repo.findAll();
    }

    @Override
    public Producto buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        return repo.findByCodigo(codigo);
    }

    @Override
    public Producto crear(Producto p) {
        return repo.save(p);
    }

    @Override
    public Producto actualizar(Long id, Producto p) {
        Producto base = repo.findById(id).orElse(null);
        if (base == null) return null;

        base.setCodigo(p.getCodigo());
        base.setNombre(p.getNombre());
        base.setPrecio(p.getPrecio());
        base.setStock(p.getStock());
        base.setDescripcionCorta(p.getDescripcionCorta());
        base.setDescripcionLarga(p.getDescripcionLarga());
        base.setImagen(p.getImagen());
        base.setCategoria(p.getCategoria());
        base.setOrigen(p.getOrigen());
        base.setPracticasSostenibles(p.getPracticasSostenibles());
        base.setRecetas(p.getRecetas());

        return repo.save(base);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
