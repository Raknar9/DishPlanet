package com.example.dishplanet.TEST;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.dishplanet.entidades.Inventario;
import com.example.dishplanet.repositorios.InventarioRepository;
import com.example.dishplanet.servicios.EmailService;
import com.example.dishplanet.servicios.InventarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.verify;

public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;
    @Mock
    private SimpMessagingTemplate messagingTemplate;
    @Mock
    private EmailService emailService;

    public InventarioServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGuardarInventario() {
        Inventario inventario = new Inventario();

        inventarioService.guardarInventario(inventario);

        verify(inventarioRepository).save(inventario);
    }
    @Test
    public void testActualizarCantidad() {
        Long id = 1L;
        int nuevaCantidad = 10;

        Inventario inventario = new Inventario();
        when(inventarioRepository.findById(id)).thenReturn(Optional.of(inventario));

        inventarioService.actualizarCantidad(id, nuevaCantidad);

        verify(inventarioRepository).save(inventario);
        assert(inventario.getCantidad() == nuevaCantidad);
    }

    @Test
    public void testActualizarCantidad_InventarioNoEncontrado() {
        Long id = 1L;
        int nuevaCantidad = 10;

        when(inventarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> inventarioService.actualizarCantidad(id, nuevaCantidad));
    }
    @Test
    public void testUpdateInventario() {
        String nombreIngrediente = "NombreIngrediente";

        Inventario inventario = new Inventario();
        inventario.setCantidad(5); // Establecer una cantidad inicial

        when(inventarioRepository.findByNombre(nombreIngrediente)).thenReturn(Optional.of(inventario));

        inventarioService.updateInventario(nombreIngrediente);

        verify(inventarioRepository).save(inventario);
        verify(messagingTemplate).convertAndSend("/topic/inventario", inventario);
    }
    @Test
    public void testDeleteByNombre() {
        String nombre = "NombreInventario";

        Inventario inventario = new Inventario();
        when(inventarioRepository.findByNombre(nombre)).thenReturn(Optional.of(inventario));

        inventarioService.deleteByNombre(nombre);

        verify(inventarioRepository).delete(inventario);
        verify(messagingTemplate).convertAndSend("/topic/inventario", inventario);
    }
    @Test
    public void testRevisarYActualizarInventario() {
        Inventario item1 = new Inventario();
        item1.setCantidad(15); // Esta cantidad debe activar la reposición
        item1.setPrecioUnitario(10.0); // Establecer un precio unitario para calcular el costo total

        Inventario item2 = new Inventario();
        item2.setCantidad(25); // Esta cantidad no activa la reposición

        List<Inventario> inventarios = new ArrayList<>();
        inventarios.add(item1);
        inventarios.add(item2);

        when(inventarioRepository.findAll()).thenReturn(inventarios);

        inventarioService.revisarYActualizarInventario();

        verify(inventarioRepository).saveAll(inventarios);

        // Verificar que se envíe un correo electrónico
        verify(emailService).sendEmail(anyString(), anyString(), anyString());

        // Verificar que se envíe un mensaje a través de SimpMessagingTemplate para item1
        verify(messagingTemplate).convertAndSend(eq("/topic/inventario"), eq(item1));

        // Verificar que NO se envíe un mensaje para item2, ya que no se ha modificado su cantidad
        verify(messagingTemplate, never()).convertAndSend(eq("/topic/inventario"), eq(item2));
    }
}

