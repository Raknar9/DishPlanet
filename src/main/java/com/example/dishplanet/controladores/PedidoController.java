package com.example.dishplanet.controladores;

import com.example.dishplanet.entidades.DetallePedido;
import com.example.dishplanet.entidades.IngredienteUsado;
import com.example.dishplanet.entidades.Pedido;
import com.example.dishplanet.entidades.Plato;
import com.example.dishplanet.servicios.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Controller
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private DetallePedidoService detallePedidoService;
    @Autowired
    private IngredienteUsadoService ingredienteUsadoService;
    @Autowired
    private UserService userService;
    @Autowired
    private PlatoService platoService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private InventarioService inventarioService;


    @PostMapping("/agregarPrincipal")
    public String agregarPrincipales(@RequestParam Long idPlato, @RequestParam String nombrePlato,
                                     @RequestParam double precioPlato, HttpServletResponse response) {
        Pedido pedido = new Pedido();
        pedido.setIdPlato(idPlato);
        pedido.setNombrePlato(nombrePlato);
        pedido.setPrecio(precioPlato);
        pedidoService.guardarPedido(pedido);
        Optional<Plato> plato=platoService.buscarPorId(idPlato);
        Plato plato1=plato.get();
        ingredienteUsadoService.saveIngredientesUsados(plato1);
        inventarioService.revisarYActualizarInventario();

        /////
        try {
            // Codificar el valor de la cookie
            String cookieValue = idPlato + "|" + nombrePlato + "|" + precioPlato;
            String encodedCookieValue = URLEncoder.encode(cookieValue, "UTF-8");
            Cookie cookie = new Cookie("pedidoPrincipal_" + idPlato, encodedCookieValue);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 días
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Manejar el error de codificación
        }

        return "redirect:/plato/principales";
    }
    @GetMapping("/verPedidos")//cookies
    public String verPedidos(HttpServletRequest request, Model model) {
        List<Pedido> pedidos = new ArrayList<>();
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().startsWith("pedidoPrincipal_")) {
                try {
                    // Decodificar el valor de la cookie
                    String decodedCookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    String[] data = decodedCookieValue.split("\\|");
                    Pedido pedido = new Pedido();
                    pedido.setIdPlato(Long.parseLong(data[0]));
                    pedido.setNombrePlato(data[1]);
                    pedido.setPrecio(Double.parseDouble(data[2]));
                    pedidos.add(pedido);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    // Manejar el error de decodificación
                }
            }
        }
        model.addAttribute("pedidos", pedidos);
        return "pedidos/verPedidos"; // Nombre de la vista HTML
    }

    @PostMapping("/agregarEntrante")
    public String agregarEntrantes(@RequestParam Long idPlato, @RequestParam String nombrePlato,
                                       @RequestParam double precioPlato, HttpServletResponse response) {

        Pedido pedido= new Pedido();
        pedido.setIdPlato(idPlato);
        pedido.setNombrePlato(nombrePlato);
        pedido.setPrecio(precioPlato);
        pedidoService.guardarPedido(pedido);
        Optional<Plato> plato=platoService.buscarPorId(idPlato);
        Plato plato1=plato.get();
        ingredienteUsadoService.saveIngredientesUsados(plato1);
        inventarioService.revisarYActualizarInventario();

        ///
        try {
            // Codificar el valor de la cookie
            String cookieValue = idPlato + "|" + nombrePlato + "|" + precioPlato;
            String encodedCookieValue = URLEncoder.encode(cookieValue, "UTF-8");
            Cookie cookie = new Cookie("pedidoPrincipal_" + idPlato, encodedCookieValue);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 días
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Manejar el error de codificación
        }
        return "redirect:/plato/entrantes";
    }

    @PostMapping("/agregarBebida")
    public String agregarBebidas(@RequestParam Long idPlato, @RequestParam String nombrePlato,
                                   @RequestParam double precioPlato, HttpServletResponse response) {

        Pedido pedido= new Pedido();
        pedido.setIdPlato(idPlato);
        pedido.setNombrePlato(nombrePlato);
        pedido.setPrecio(precioPlato);
        pedidoService.guardarPedido(pedido);
        Optional<Plato> plato=platoService.buscarPorId(idPlato);
        Plato plato1=plato.get();
        ingredienteUsadoService.saveIngredientesUsados(plato1);
        inventarioService.revisarYActualizarInventario();

        //
        try {
            // Codificar el valor de la cookie
            String cookieValue = idPlato + "|" + nombrePlato + "|" + precioPlato;
            String encodedCookieValue = URLEncoder.encode(cookieValue, "UTF-8");
            Cookie cookie = new Cookie("pedidoPrincipal_" + idPlato, encodedCookieValue);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 días
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Manejar el error de codificación
        }
        return "redirect:/plato/bebidas";
    }

    @PostMapping("/agregarPostre")
    public String agregarPostres(@RequestParam Long idPlato, @RequestParam String nombrePlato,
                                 @RequestParam double precioPlato, HttpServletResponse response) {

        Pedido pedido= new Pedido();
        pedido.setIdPlato(idPlato);
        pedido.setNombrePlato(nombrePlato);
        pedido.setPrecio(precioPlato);
        pedidoService.guardarPedido(pedido);
        Optional<Plato> plato=platoService.buscarPorId(idPlato);
        Plato plato1=plato.get();
        ingredienteUsadoService.saveIngredientesUsados(plato1);
        inventarioService.revisarYActualizarInventario();

        //

        try {
            // Codificar el valor de la cookie
            String cookieValue = idPlato + "|" + nombrePlato + "|" + precioPlato;
            String encodedCookieValue = URLEncoder.encode(cookieValue, "UTF-8");
            Cookie cookie = new Cookie("pedidoPrincipal_" + idPlato, encodedCookieValue);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 días
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Manejar el error de codificación
        }
        return "redirect:/plato/postres";
    }
    @GetMapping("/pedidos")
    public String getAllPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.getAllPedidos();


        double subtotal = pedidoService.calcularSubtotal(pedidos);
        double iva = pedidoService.calcularIva(subtotal);
        double total = pedidoService.calcularTotal(subtotal, iva);

        // Convertir subtotal, iva y total a String con el formato adecuado
        String subtotalStr = String.format("%.2f", subtotal).replace(",", ".");//por el tipo de variable se reemplaza la coma por el punto
        String ivaStr = String.format("%.2f", iva).replace(",", ".");
        String totalStr = String.format("%.2f", total).replace(",", ".");

        // Agregar los pedidos y el total al modelo
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("iva", ivaStr);
        model.addAttribute("subtotal", subtotalStr);
        model.addAttribute("total", totalStr);

        return "pedidos/lista";
    }

    @PostMapping("/finalizarPedido")
    public String finalizarPedido(Model model) {
        // Obtener el nombre del usuario actual
        String nombreUsuario = userService.obtenerNombreUsuario();
        Long idUsuario= userService.obtenerIDUsuarioPorNombre(nombreUsuario);
        // Obtener todos los pedidos
        List<Pedido> pedidos = pedidoService.getAllPedidos();
    log.info("sd "+idUsuario);
        // Calcular el precio total y obtener los nombres de los platos
        double precioTotal = 0;
        StringBuilder nombresPlatos = new StringBuilder();
        for (Pedido pedido : pedidos) {
            precioTotal += pedido.getPrecio();
            nombresPlatos.append(pedido.getNombrePlato()).append("\n"); // Separador de línea
        }

        // Guardar los detalles del pedido en la tabla Detalle_Pedido
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setNombres_platos(nombresPlatos.toString());
        detallePedido.setPrecio_total(precioTotal);
        detallePedido.setNombreUsuario(nombreUsuario);
        detallePedido.setId_usuario(idUsuario);// Guardar el nombre del usuario
        detallePedidoService.guardarDetallePedido(detallePedido);

        // Borrar los datos de la tabla Pedido
        pedidoService.borrarTodosLosPedidos();

        return "redirect:/pedido/fin";
    }

    @GetMapping("/fin")
    public String finPedidos(Model model) {
        return "pedidos/finPedido";
    }

    @PostMapping("/enviarReciboEmail")
    public String enviarReciboEmail(@RequestParam("email") String email, Model model) {
        List<Pedido> pedidos = pedidoService.getAllPedidos();

        double subtotal = pedidoService.calcularSubtotal(pedidos);
        double iva = pedidoService.calcularIva(subtotal);
        double total = pedidoService.calcularTotal(subtotal, iva);

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Recibo de su pedido:\n\n");

        for (Pedido pedido : pedidos) {
            emailContent.append("Plato: ").append(pedido.getNombrePlato()).append("\n");
            emailContent.append("Precio: € ").append(String.format("%.2f", pedido.getPrecio())).append("\n\n");
        }

        emailContent.append("Subtotal: € ").append(String.format("%.2f", subtotal)).append("\n");
        emailContent.append("IVA (10%): € ").append(String.format("%.2f", iva)).append("\n");
        emailContent.append("Total: € ").append(String.format("%.2f", total)).append("\n");

        emailService.sendEmail(email, "Recibo de Pedido", emailContent.toString());
        pedidoService.borrarTodosLosPedidos();
        model.addAttribute("message", "El recibo ha sido enviado a " + email);
        return  "redirect:/pedido/fin";
    }

}
