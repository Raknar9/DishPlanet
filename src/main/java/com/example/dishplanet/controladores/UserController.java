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


    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {

        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas. Por favor, intente nuevamente.");
        }
        return "login/login";
    }

    @PostMapping("/login")
    public String loginp(){
        return "redirect:/principales";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Invalidar la sesión actual
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Establecer el nombre de usuario como "Invitado" en una nueva sesión
        SecurityContextHolder.clearContext();

        return "redirect:/"; // Redirigir a la página de inicio u otra página después del logout
    }
    @GetMapping("/signup")
    public String mostrarFormularioRegistro() {
        return "login/signup";
    }
    @PostMapping("/signup")
    public String registrarUsuario(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("email") String email,
                                   RedirectAttributes redirectAttributes) {
        // Encriptar la contraseña antes de guardarla en la base de datos
        String contraseñaEncriptada = passwordEncoder.encode(password);

        // Guardar el usuario en la base de datos
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(contraseñaEncriptada);
        usuario.setEmail(email);
        usuarioService.guardarUsuario(usuario);

        // Redirigir a la página de inicio de sesión con un mensaje de éxito
        redirectAttributes.addFlashAttribute("mensaje", "¡Registro exitoso! Por favor, inicia sesión.");
        return "redirect:/usuario/login";
    }


    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "login/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("username") String username, Model model) {
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario != null) {
            String code = usuarioService.generateVerificationCode();
            // Guarda el código de verificación en la base de datos
            verificationCodeService.saveVerificationCode(username, code);
            emailService.sendEmail(usuario.getEmail(), "Código de verificación", "Tu código de verificación es: " + code);
            // Aquí se redirige al usuario a la página donde ingresa el código de verificación
            return "redirect:/usuario/verifyCode";
        } else {
            model.addAttribute("error", "Usuario no encontrado");
            return "login/forgotPassword";
        }
    }

    @GetMapping("/verifyCode")
    public String showVerifyCodeForm() {
        return "login/verifyCode";
    }

    @PostMapping("/verifyCode")
    public String processVerifyCode(@RequestParam("username") String username,
                                    @RequestParam("code") String code,
                                    Model model) {
        // Verifica si el código ingresado por el usuario es válido
        if (verificationCodeService.isCodeValid(username, code)) {
            return "redirect:/usuario/resetPassword";
        } else {
            model.addAttribute("error", "Código de verificación inválido");
            return "login/verifyCode";
        }
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm() {
        return "login/resetPassword";
    }

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



