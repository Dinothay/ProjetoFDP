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

    @Autowired
    CarrinhoRepository carrinhoRepository;

    @Autowired
    ItemCarrinhoRepository itemCarrinhoRepository;

    @GetMapping("/carrinho")
    public String carrinho(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        Carrinho carrinho = carrinhoRepository.findById(usuario.getCarrinho().getId());
        usuario.setCarrinho(carrinho);
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", carrinho.getTotal());
        return "carrinho.html";
    }

    @PostMapping("/adicionar")
    public String adicionarProduto(@RequestParam int id, HttpSession session) {
        Produto produto = produtoRepository.findById(id);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        Carrinho carrinho = carrinhoRepository.findById(usuario.getCarrinho().getId());
        usuario.setCarrinho(carrinho);
        boolean encontrou = false;
        for (ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto().getId() == id) {
                item.setQuantidade(item.getQuantidade() + 1);
                itemCarrinhoRepository.update(item);
                encontrou = true;
                break;
            }
        }
        if (!encontrou) {
            ItemCarrinho novoItem = new ItemCarrinho();
            novoItem.setProduto(produto);
            novoItem.setCarrinho(carrinho);
            novoItem.setQuantidade(1);
            itemCarrinhoRepository.save(novoItem);
            carrinho.getItens().add(novoItem);
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/mais")
    public String maisQuantidade(@RequestParam int id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        Carrinho carrinho = carrinhoRepository.findById(usuario.getCarrinho().getId());
        usuario.setCarrinho(carrinho);
        for (ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto().getId() == id) {
                item.setQuantidade(item.getQuantidade() + 1);
                itemCarrinhoRepository.update(item);
                break;
            }
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/menos")
    public String menosQuantidade(@RequestParam int id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        Carrinho carrinho = carrinhoRepository.findById(usuario.getCarrinho().getId());
        usuario.setCarrinho(carrinho);
        ItemCarrinho itemRemover = null;
        for (ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto().getId() == id) {
                item.setQuantidade(item.getQuantidade() - 1);
                if (item.getQuantidade() <= 0) {
                    itemCarrinhoRepository.delete(item.getId());
                    itemRemover = item;
                } else {
                    itemCarrinhoRepository.update(item);
                }
                break;
            }
        }
        if (itemRemover != null) {
            carrinho.getItens().remove(itemRemover);
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/remover")
    public String removerProduto(@RequestParam int id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        Carrinho carrinho = carrinhoRepository.findById(usuario.getCarrinho().getId());
        usuario.setCarrinho(carrinho);
        ItemCarrinho itemRemover = null;
        for (ItemCarrinho item : carrinho.getItens()) {

            if (item.getProduto().getId() == id) {
                
                itemCarrinhoRepository.delete(item.getId());
                itemRemover = item;

                break;
            }
        }
        if (itemRemover != null) {
            carrinho.getItens().remove(itemRemover);
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/limpar")
    public String limparCarrinho(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        Carrinho carrinho = carrinhoRepository.findById(usuario.getCarrinho().getId());
        usuario.setCarrinho(carrinho);
        for (ItemCarrinho item : carrinho.getItens()) {
            itemCarrinhoRepository.delete(item.getId());
        }
        carrinho.getItens().clear();
        return "redirect:/carrinho";
    }

    @PostMapping("/comprar")
    public String comprar(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }
        Carrinho carrinho = carrinhoRepository.findById(usuario.getCarrinho().getId());
        usuario.setCarrinho(carrinho);
        for (ItemCarrinho item : carrinho.getItens()) {
            itemCarrinhoRepository.delete(item.getId());
        }
        carrinho.getItens().clear();
        return "redirect:/listaProduto?compra=sucesso";
    }
}