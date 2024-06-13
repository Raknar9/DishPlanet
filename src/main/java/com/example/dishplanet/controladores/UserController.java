package com.example.dishplanet.controladores;

import com.example.dishplanet.entidades.Usuario;
import com.example.dishplanet.servicios.EmailService;
import com.example.dishplanet.servicios.UserService;
import com.example.dishplanet.servicios.VerificationCodeServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador UserController para manejar las solicitudes relacionadas con el usuario.
 * Este controlador proporciona funcionalidades para iniciar sesión, registrarse, y recuperar contraseñas.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private final UserService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeServiceImpl verificationCodeService;

    /**
     * Maneja las solicitudes GET a la URL "/usuario/login".
     * Muestra el formulario de inicio de sesión.
     *
     * @param error el mensaje de error si las credenciales son inválidas
     * @param model el modelo al cual se añaden los atributos
     * @return el nombre de la vista de inicio de sesión
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas. Por favor, intente nuevamente.");
        }
        return "login/login";
    }

    /**
     * Maneja las solicitudes POST a la URL "/usuario/login".
     * Redirige al usuario a la vista de platos principales después de iniciar sesión.
     *
     * @return redirige a la vista de platos principales
     */
    @PostMapping("/login")
    public String loginp() {
        return "redirect:/principales";
    }

    /**
     * Maneja las solicitudes GET a la URL "/usuario/logout".
     * Invalida la sesión actual y redirige a la página principal.
     *
     * @param request la solicitud HTTP
     * @return redirige a la página principal
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

    /**
     * Maneja las solicitudes GET a la URL "/usuario/signup".
     * Muestra el formulario de registro de usuario.
     *
     * @return el nombre de la vista de registro de usuario
     */
    @GetMapping("/signup")
    public String mostrarFormularioRegistro() {
        return "login/signup";
    }

    /**
     * Maneja las solicitudes POST a la URL "/usuario/signup".
     * Registra un nuevo usuario con los datos proporcionados.
     *
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     * @param email el correo electrónico del usuario
     * @param redirectAttributes los atributos de redirección
     * @return redirige a la vista de inicio de sesión después del registro
     */
    @PostMapping("/signup")
    public String registrarUsuario(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("email") String email,
                                   RedirectAttributes redirectAttributes) {
        String contraseñaEncriptada = passwordEncoder.encode(password);
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(contraseñaEncriptada);
        usuario.setEmail(email);
        usuarioService.guardarUsuario(usuario);
        return "redirect:/usuario/login";
    }

    /**
     * Maneja las solicitudes GET a la URL "/usuario/forgotPassword".
     * Muestra el formulario para recuperar la contraseña.
     *
     * @return el nombre de la vista de recuperación de contraseña
     */
    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "login/forgotPassword";
    }

    /**
     * Maneja las solicitudes POST a la URL "/usuario/forgotPassword".
     * Procesa la solicitud de recuperación de contraseña y envía un código de verificación al correo del usuario.
     *
     * @param username el nombre de usuario
     * @param model el modelo al cual se añaden los atributos
     * @return redirige a la vista de verificación de código
     */
    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("username") String username, Model model) {
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario != null) {
            String code = usuarioService.generateVerificationCode();
            verificationCodeService.saveVerificationCode(username, code);
            emailService.sendEmail(usuario.getEmail(), "Código de verificación", "Tu código de verificación es: " + code);
            return "redirect:/usuario/verifyCode";
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "login/forgotPassword";
        }
    }

    /**
     * Maneja las solicitudes GET a la URL "/usuario/verifyCode".
     * Muestra el formulario para ingresar el código de verificación.
     *
     * @return el nombre de la vista de verificación de código
     */
    @GetMapping("/verifyCode")
    public String showVerifyCodeForm() {
        return "login/verifyCode";
    }

    /**
     * Maneja las solicitudes POST a la URL "/usuario/verifyCode".
     * Procesa la verificación del código y redirige a la vista de restablecimiento de contraseña si es válido.
     *
     * @param username el nombre de usuario
     * @param code el código de verificación
     * @param model el modelo al cual se añaden los atributos
     * @return redirige a la vista de restablecimiento de contraseña si el código es válido, o muestra un error
     */
    @PostMapping("/verifyCode")
    public String processVerifyCode(@RequestParam("username") String username,
                                    @RequestParam("code") String code,
                                    Model model) {
        if (verificationCodeService.isCodeValid(username, code)) {
            return "redirect:/usuario/resetPassword";
        } else {
            model.addAttribute("error", "Código de verificación inválido");
            return "login/verifyCode";
        }
    }

    /**
     * Maneja las solicitudes GET a la URL "/usuario/resetPassword".
     * Muestra el formulario para restablecer la contraseña.
     *
     * @return el nombre de la vista de restablecimiento de contraseña
     */
    @GetMapping("/resetPassword")
    public String showResetPasswordForm() {
        return "login/resetPassword";
    }

    /**
     * Maneja las solicitudes POST a la URL "/usuario/resetPassword".
     * Procesa el restablecimiento de la contraseña para el usuario.
     *
     * @param username el nombre de usuario
     * @param newPassword la nueva contraseña del usuario
     * @param model el modelo al cual se añaden los atributos
     * @return redirige a la vista de inicio de sesión después de restablecer la contraseña
     */
    @PostMapping("/resetPassword")
    public String processResetPassword(@RequestParam("username") String username,
                                       @RequestParam("newPassword") String newPassword,
                                       Model model) {
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario != null) {
            usuario.setPassword(passwordEncoder.encode(newPassword));
            usuarioService.saveUser(usuario);
            return "redirect:/usuario/login";
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "login/resetPassword";
        }
    }
}


