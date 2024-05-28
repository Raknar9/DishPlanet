package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.DetallePedido;
import com.example.dishplanet.repositorios.DetallePedidoRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class DetallePedidoService {
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private EmailService emailService;

    public void guardarDetallePedido(DetallePedido detallePedido) {
        detallePedidoRepository.save(detallePedido);
    }

    public void generarInformePedidos(HttpServletResponse response) throws IOException {
        List<DetallePedido> pedidos = detallePedidoRepository.findAll();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph("Informe de Pedidos\n\n_____________________________________________________________________________\n"));

            for (DetallePedido pedido : pedidos) {
                document.add(new Paragraph("Pedido numero: " + pedido.getId_detalle()));

                // Dividir los nombres de los platos y formatearlos
                String[] nombresPlatos = pedido.getNombres_platos().split("\\n");
                StringBuilder nombresPlatosFormateados = new StringBuilder();
                for (int i = 0; i < nombresPlatos.length; i++) {
                    if (i > 0) {
                        nombresPlatosFormateados.append(" - ");
                    }
                    nombresPlatosFormateados.append((i + 1)).append(". ").append(nombresPlatos[i]);
                }

                document.add(new Paragraph("Nombre de los platos: " + nombresPlatosFormateados.toString()));
                document.add(new Paragraph("Precio total: " + pedido.getPrecio_total()));
                document.add(new Paragraph("Nombre de usuario: " + pedido.getNombreUsuario()
                        + "\n_____________________________________________________________________________\n"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        // Descargar el PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=informe_pedidos.pdf");
        response.getOutputStream().write(baos.toByteArray());

        // Enviar el PDF como adjunto en el correo electrónico
        emailService.sendEmailWithAttachment("alejanbenitez.002@gmail.com", "Informe de Pedidos", "Adjunto encontrarás el informe de pedidos.", baos);
    }


}
