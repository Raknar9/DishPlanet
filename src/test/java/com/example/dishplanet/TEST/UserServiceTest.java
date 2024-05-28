package com.example.dishplanet.TEST;

import com.example.dishplanet.entidades.Usuario;
import com.example.dishplanet.repositorios.UsuarioRepository;
import com.example.dishplanet.servicios.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUsername() {
        // Datos de prueba
        String username = "testUser";
        Usuario usuario = new Usuario();
        usuario.setUsername(username);

        // configura el comportamiento del repositorio
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(usuario));

        // llama al método a probar
        Usuario result = userService.findByUsername(username);

        // se verifica que se llamó al método del repositorio y que devuelve el usuario correcto
        verify(userRepository).findByUsername(username);
        assertEquals(usuario, result);
    }

    @Test
    public void testGetAllUsuarios() {
        // Datos de prueba
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario());
        usuarios.add(new Usuario());

        // Configura del comportamiento del repositorio
        when(userRepository.findAll()).thenReturn(usuarios);

        // Ejecuta el método a probar
        List<Usuario> result = userService.getAllUsuarios();

        // verifica que se llamó al método del repositorio y que devuelve la lista de usuarios correcta
        verify(userRepository).findAll();
        assertEquals(usuarios, result);
    }

    @Test
    public void testObtenerNombreUsuario_Autenticado() {
        // Configuración del contexto de seguridad
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("Invitado");

        SecurityContextHolder.setContext(securityContext);

        // Ejecuta el método a probar
        String result = userService.obtenerNombreUsuario();

        // Verifica que devuelve el nombre de usuario autenticado
        assertEquals("Invitado", result);
    }

    @Test
    public void testObtenerNombreUsuario_NoAutenticado() {
        // Configuración del contexto de seguridad
        when(securityContext.getAuthentication()).thenReturn(null);

        SecurityContextHolder.setContext(securityContext);

        // Ejecuta el método a probar
        String result = userService.obtenerNombreUsuario();

        // Verifica que devuelve "Invitado" para usuario no autenticado
        assertEquals("Invitado", result);
    }



    @Test
    public void testGenerateVerificationCode() {
        // Ejecuta el método a probar
        String result = userService.generateVerificationCode();

        // Verifica que devuelve un código de verificación de 6 dígitos
        assertNotNull(result);
        assertTrue(result.matches("\\d{6}"));
    }
}

