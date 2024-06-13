package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.Pedido;
import com.example.dishplanet.repositorios.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de pedidos.
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Guarda un pedido en la base de datos.
     *
     * @param pedido Pedido a ser guardado.
     */
    public void guardarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    /**
     * Obtiene todos los pedidos almacenados en la base de datos.
     *
     * @return Lista de todos los pedidos.
     */
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    /**
     * Borra todos los pedidos de la base de datos.
     */
    public void borrarTodosLosPedidos() {
        pedidoRepository.deleteAll();
    }

    /**
     * Calcula el subtotal de una lista de pedidos.
     *
     * @param pedidos Lista de pedidos.
     * @return Subtotal calculado.
     */
    public double calcularSubtotal(List<Pedido> pedidos) {
        return pedidos.stream().mapToDouble(Pedido::getPrecio).sum();
    }

    /**
     * Calcula el IVA basado en el subtotal dado.
     *
     * @param subtotal Subtotal del pedido.
     * @return Valor del IVA calculado.
     */
    public double calcularIva(double subtotal) {
        return subtotal * 0.10; // Suponiendo un IVA del 10%
    }

    /**
     * Calcula el total sumando el subtotal y el IVA.
     *
     * @param subtotal Subtotal del pedido.
     * @param iva      Valor del IVA.
     * @return Total calculado.
     */
    public double calcularTotal(double subtotal, double iva) {
        return subtotal + iva;
    }
}


