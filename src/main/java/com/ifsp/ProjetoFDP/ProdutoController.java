package com.ifsp.ProjetoFDP;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProdutoController {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    FileStorageService fileStorageService;

    @GetMapping("/produto")
    public String formularioProduto() {
        return "formularioProduto.html";
    }

    @PostMapping("/criarProduto")
    public String criarProduto(@RequestParam String nome, @RequestParam String descricao, @RequestParam double preco,
            @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
        Produto produto = new Produto(nome, descricao, preco);
        if (!file.isEmpty()) {
            String imageName = fileStorageService.store(file);
            produto.setImage(imageName);
        }
        produtoRepository.save(produto);
        return "redirect:/produto";
    }

    @GetMapping("/listaProduto")
    public String listarProdutos(Model model, HttpSession session, @RequestParam(defaultValue = "1") int pagina,
            @RequestParam(required = false) String busca, @RequestParam(required = false) String compra) {
        int tamanho = 5;
        List<Produto> produtos;
        int totalProdutos;
        if ("sucesso".equals(compra)) {
            model.addAttribute("mensagem", "Compra realizada com sucesso!");
        }
        if (busca != null && !busca.isBlank()) {
            produtos = produtoRepository.findByNomePage(busca, pagina, tamanho);
            totalProdutos = produtoRepository.countByNome(busca);
            model.addAttribute("busca", busca);
        } else {
            produtos = produtoRepository.findPage(pagina, tamanho);
            totalProdutos = produtoRepository.count();
        }
        int totalPaginas = Math.max(1, (int) Math.ceil((double) totalProdutos / tamanho));
        if (pagina < 1) {
            pagina = 1;
        }
        if (pagina > totalPaginas && totalPaginas > 0) {
            pagina = totalPaginas;
        }
        model.addAttribute("produtos", produtos);
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", totalPaginas);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        return "listaProdutos";
    }

    @GetMapping("/produto/{id}")
    public String show(@PathVariable int id, Model model, HttpSession session) {
        Produto produto = produtoRepository.findById(id);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("produto", produto);
        model.addAttribute("usuario", usuario);
        return "detalhesProduto";
    }

    @GetMapping("/produto/{id}/editar")
    public String editaString(@PathVariable int id, Model model) {
        Produto produto = produtoRepository.findById(id);
        model.addAttribute("produto", produto);
        return "formularioEditarProduto.html";
    }

    @PostMapping("/produto/{id}/atualizar")
    public String atualizarProduto(@PathVariable int id, @RequestParam String nome, @RequestParam String descricao,
            @RequestParam Double preco) {
        Produto produto = produtoRepository.findById(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produtoRepository.update(produto);
        return "redirect:/listaProduto";
    }

    @PostMapping("/produto/{id}/deletar")
    public String excluirProduto(@PathVariable int id) {
        produtoRepository.delete(id);
        return "redirect:/listaProduto";
    }

    @PostMapping("/buscar")
    public String buscarProduto(@RequestParam String busca, Model model, HttpSession session) {
        List<Produto> produtos = produtoRepository.findByNome(busca);
        model.addAttribute("produtos", produtos);
        model.addAttribute("paginaAtual", 1);
        model.addAttribute("temProxima", false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        return "listaProdutos";
    }

    @PostMapping("/comprarProduto")
    public String comprarAgora(@RequestParam int id, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        Produto produto = produtoRepository.findById(id);

        return "redirect:/listaProduto?compra=sucesso";
    }
}
