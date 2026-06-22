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
    @Autowired
    CarrinhoRepository carrinhoRepository;

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
        if (usuario != null && usuario.getSenha().equals(senha)) {
            int carrinhoId = usuarioRepository.findCarrinhoIdByEmail(email);
            Carrinho carrinho = carrinhoRepository.findById(carrinhoId);
            usuario.setCarrinho(carrinho);
            session.setAttribute("usuario", usuario);
            return "redirect:/listaProduto";
        }
        return "redirect:/login";
    }

    @PostMapping("/criarUsuario")
    public String criarUsuario(@RequestParam String nome, @RequestParam String email, @RequestParam String senha) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setGerente(false);
        int carrinhoId = carrinhoRepository.save();
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId);
        usuario.setCarrinho(carrinho);
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
