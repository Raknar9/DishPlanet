package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Pedido;
import com.example.dishplanet.entidades.Plato;
import com.example.dishplanet.repositorios.PedidoRepository;
import com.example.dishplanet.repositorios.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;



    public void guardarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }
    public void borrarTodosLosPedidos() {
        pedidoRepository.deleteAll();
    }
    public double calcularSubtotal(List<Pedido> pedidos) {
        return pedidos.stream().mapToDouble(Pedido::getPrecio).sum();
    }

    public double calcularIva(double subtotal) {
        return subtotal * 0.10;
    }

    public double calcularTotal(double subtotal, double iva) {
        return subtotal + iva;
    }
}


