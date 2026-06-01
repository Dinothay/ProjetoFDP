package com.ifsp.ProjetoFDP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro.html";
    }

    @PostMapping("/login")
    public String verificarLogin(@RequestParam String email, @RequestParam String senha, HttpSession session) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null &&
                usuario.getSenha().equals(senha)) {
            session.setAttribute("usuario", usuario);
            return "redirect:/listaProduto";
        }
        return "redirect:/login";
    }

    @PostMapping("/criarUsuario")
    public String criarUsuario(@RequestParam String nome, @RequestParam String email, @RequestParam String senha,
            @RequestParam boolean gerente) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setGerente(gerente);
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}
