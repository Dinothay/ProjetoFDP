package com.ifsp.ProjetoFDP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarrinhoController {
    @Autowired
    ProdutoRepository produtoRepository;
    Carrinho carrinho = new Carrinho();

    @GetMapping("/carrinho")
    public String carrinho(Model model) {
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", carrinho.getTotal());
        return "carrinho.html";
    }

    @PostMapping("/adicionar")
    public String adicionarProduto(@RequestParam int id) {
        Produto produto = produtoRepository.findById(id);
        boolean r = false;
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
    public String maisQuantidade(@RequestParam int id) {
        for (ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto().getId() == id) {
                item.setQuantidade(item.getQuantidade() + 1);
                break;
            }
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/menos")
    public String menosQuantidade(@RequestParam int id) {
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
    public String removerProduto(@RequestParam int id) {
        carrinho.getItens().removeIf(item -> item.getProduto().getId() == id);
        return "redirect:/carrinho";
    }

    @PostMapping("/limpar")
    public String limparCarrinho() {
        carrinho.getItens().clear();
        return "redirect:/carrinho";
    }

    
}