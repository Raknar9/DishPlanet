package com.example.dishplanet.TEST;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.example.dishplanet.entidades.Inventario;
import com.example.dishplanet.repositorios.InventarioRepository;
import com.example.dishplanet.servicios.EmailService;
import com.example.dishplanet.servicios.InventarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    public void testRevisarYActualizarInventario_AlertaEnviada() {
        // Datos de prueba
        Inventario item1 = new Inventario();
        item1.setNombre("Item 1");
        item1.setCategoria("Categoría 1");
        item1.setCantidad(15);
        item1.setUnidadMedida("unidad");
        item1.setPrecioUnitario(10.0);

        Inventario item2 = new Inventario();
        item1.setNombre("Item 2");
        item1.setCategoria("Categoría 1");
        item1.setCantidad(25);
        item1.setUnidadMedida("unidad");
        item1.setPrecioUnitario(12.0);

        List<Inventario> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        // Configuración del comportamiento del repositorio
        when(inventarioRepository.findAll()).thenReturn(items);

        // Ejecutar el método a probar
        inventarioService.revisarYActualizarInventario();

        // Verificar que el inventario se actualizó correctamente
        verify(inventarioRepository).saveAll(items);

        // Verificar que se envió un correo electrónico de alerta
        verify(emailService).sendEmail(eq("alejanbenitez.002@gmail.com"), eq("Alerta de Inventario"), anyString());
    }

    @Test
    public void testRevisarYActualizarInventario_AlertaNoEnviada() {
        // Datos de prueba
        Inventario item1 = new Inventario();
        item1.setNombre("Item 1");
        item1.setCategoria("Categoría 1");
        item1.setCantidad(25);
        item1.setUnidadMedida("unidad");
        item1.setPrecioUnitario(10.0);

        Inventario item2 = new Inventario();
        item2.setNombre("Item 2");
        item2.setCategoria("Categoría 1");
        item2.setCantidad(30);
        item2.setUnidadMedida("unidad");
        item2.setPrecioUnitario(12.0);

        List<Inventario> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        // se configura el comportamiento del repositorio
        when(inventarioRepository.findAll()).thenReturn(items);

        // llama al servicio que se quiere probar
        inventarioService.revisarYActualizarInventario();

        // verifica que el inventario no se actualizo
        verify(inventarioRepository, never()).saveAll(items);

        // y verifica que el email se envio correctamente
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

}
