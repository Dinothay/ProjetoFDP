package com.ifsp.ProjetoFDP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class CarrinhoController {
    @Autowired
    ProdutoRepository produtoRepository;
    Carrinho carrinho = new Carrinho();

    @GetMapping("/carrinho")
    public String carrinho(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Carrinho carrinho = usuario.getCarrinho();
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", carrinho.getTotal());
        return "carrinho.html";
    }

    @PostMapping("/adicionar")
    public String adicionarProduto(@RequestParam int id, HttpSession session) {
        Produto produto = produtoRepository.findById(id);
        boolean r = false;
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Carrinho carrinho = usuario.getCarrinho();
        for (ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto().getId() == id) {
                item.setQuantidade(item.getQuantidade() + 1);
                r = true;
                break;
            }
        }
        if (!r) {
            ItemCarrinho novoItem = new ItemCarrinho(produto, produto.getPreco(), 1);
            carrinho.getItens().add(novoItem);
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/mais")
    public String maisQuantidade(@RequestParam int id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Carrinho carrinho = usuario.getCarrinho();
        for (ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto().getId() == id) {
                item.setQuantidade(item.getQuantidade() + 1);
                break;
            }
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/menos")
    public String menosQuantidade(@RequestParam int id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Carrinho carrinho = usuario.getCarrinho();
        for (ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto().getId() == id) {
                item.setQuantidade(item.getQuantidade() - 1);
                if (item.getQuantidade() <= 0) {
                    carrinho.getItens().remove(item);
                }
                break;
            }
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/remover")
    public String removerProduto(@RequestParam int id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Carrinho carrinho = usuario.getCarrinho();
        carrinho.getItens().removeIf(item -> item.getProduto().getId() == id);
        return "redirect:/carrinho";
    }

    @PostMapping("/limpar")
    public String limparCarrinho(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Carrinho carrinho = usuario.getCarrinho();
        carrinho.getItens().clear();
        return "redirect:/carrinho";
    }

}