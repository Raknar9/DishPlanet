package com.example.dishplanet.servicios;

import com.example.dishplanet.entidades.DetallePedido;
import com.example.dishplanet.repositorios.DetallePedidoRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Servicio para gestionar operaciones relacionadas con los detalles de los pedidos.
 */
@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Guarda un detalle de pedido en la base de datos.
     *
     * @param detallePedido El detalle del pedido a guardar.
     */
    public void guardarDetallePedido(DetallePedido detallePedido) {
        detallePedidoRepository.save(detallePedido);
    }

    /**
     * Genera un informe en formato PDF de todos los pedidos y lo envía por correo electrónico como adjunto.
     *
     * @param response El objeto HttpServletResponse para escribir el archivo PDF y descargarlo.
     * @throws IOException Si ocurre algún error de entrada/salida al escribir el archivo PDF.
     */
    public void generarInformePedidos(HttpServletResponse response) throws IOException {
        List<DetallePedido> pedidos = detallePedidoRepository.findAll();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph("Informe de Pedidos\n\n_____________________________________________________________________________\n"));
            for (DetallePedido pedido : pedidos) {
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                PdfPCell cell = new PdfPCell(new Paragraph("Pedido número: " + pedido.getId_detalle()));
                cell.setColspan(2);
                table.addCell(cell);
                table.addCell("Cliente:");
                table.addCell(pedido.getNombreUsuario());
                table.addCell("Platos:");
                String[] nombresPlatos = pedido.getNombres_platos().split("\\n");
                StringBuilder nombresPlatosFormateados = new StringBuilder();
                for (int i = 0; i < nombresPlatos.length; i++) {
                    if (i > 0) {
                        nombresPlatosFormateados.append(" - ");
                    }
                    nombresPlatosFormateados.append((i + 1)).append(". ").append(nombresPlatos[i]);
                }
                table.addCell(nombresPlatosFormateados.toString());
                table.addCell("Precio total:");
                table.addCell(String.valueOf(pedido.getPrecio_total()));
                document.add(table);
                document.add(new Paragraph("\n_____________________________________________________________________________\n"));
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
        emailService.sendEmailWithAttachment("alejanben.990@gmail.com", "Informe de Pedidos", "Adjunto encontrarás el informe de pedidos.", baos);
    }
}