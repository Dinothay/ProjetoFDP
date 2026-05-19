package com.ifsp.ProjetoFDP;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProdutoController {
    @Autowired ProdutoRepository produtoRepository;


    @GetMapping("/produto")
    public String formularioProduto(){
        return "formularioProduto.html";
    }

    @PostMapping("/criarProduto")
    public String criarProduto(@RequestParam String nome, @RequestParam String descricao,@RequestParam double preco){
        Produto produto= new Produto(nome, descricao, preco);
        produtoRepository.save(produto);
        return "redirect:/produto";
    }

    @GetMapping("/listaProduto")
    public String listarProdutos(Model model){
        List<Produto> produtos = produtoRepository.findAll();
        model.addAttribute("produtos",produtos);
        return "listaProdutos";
    }
    @GetMapping("/produto/{id}")
        public String show(@PathVariable int id, Model model) {
        Produto produto = produtoRepository.findById(id);
        model.addAttribute("produto", produto);
        return "detalhesProduto.html";
    }
    @GetMapping("/produto/{id}/editar")
        public String editaString(@PathVariable int id, Model model) {
        Produto produto = produtoRepository.findById(id);
        model.addAttribute("produto", produto);
        return "formularioEditarProduto.html";
    }
    @PostMapping("/produto/{id}/atualizar")
    public String atualizarProduto(@PathVariable int id, @RequestParam String nome, @RequestParam String descricao, @RequestParam Double preco){
        Produto produto = produtoRepository.findById(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produtoRepository.update(produto);
        return "redirect:/listaProduto";
    }
    @PostMapping("/produto/{id}/deletar")
    public String excluirProduto(@PathVariable int id){
        produtoRepository.delete(id);
        return "redirect:/listaProduto";
    }
}
