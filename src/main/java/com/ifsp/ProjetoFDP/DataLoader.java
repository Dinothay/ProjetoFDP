package com.ifsp.ProjetoFDP;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarrinhoRepository carrinhoRepository;


    public DataLoader(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository, CarrinhoRepository carrinhoRepository) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.carrinhoRepository = carrinhoRepository;
    }

    @Override
    public void run(String... args) {

        if (produtoRepository.count() == 0) {
            Produto p1 = new Produto(
                "Ração Dog",
                "Ração para cães",
                300.0
            );
            p1.setImage("9a61d6f9-3152-48ac-b12f-45bcfcbf8f7f.jpg");

            Produto p2 = new Produto(
                "Ração para cachorro",
                "Ração para cachorro adulto",
                80.0
            );
            p2.setImage("9a61d6f9-3152-48ac-b12f-45bcfcbf8f7f.jpg");

            Produto p3 = new Produto(
                "Ração Cat",
                "Ração para gatos",
                250.0
            );
            p3.setImage("aaf94247-5391-4fb9-9321-5468ad78b5ba.jpg");

            Produto p4 = new Produto(
                "Arranhador para gato",
                "Arranhador divertido",
                150.0
            );
            p4.setImage("0aa6c8de-9f7a-43a3-9c06-6818a7b30ba5.webp");

            Produto p5 = new Produto(
                "Bolinha",
                "Bolinha para brincar com seu pet",
                50.0
            );
            p5.setImage("837f895e-284d-4bf1-a270-ee34b51ee784.jpg");

            Produto p6 = new Produto(
                "Coleira peitoral",
                "Coleira para seu pet",
                80.0
            );
            p6.setImage("46f1efda-3101-422d-9b16-b5a1a61e5eaf.webp");

            Produto p7 = new Produto(
                "Catnip",
                "Para deixar seu gato mais feliz",
                100.0
            );
            p7.setImage("9d0ec322-2fdc-408e-b566-e6e5fd485344.jpg");

            Produto p8 = new Produto(
                "Roupinha de cachorro",
                "Roupinha de flores para seu cachorro",
                120.0
            );
            p8.setImage("15f17e49-3752-4e2f-9f7c-d3ad0b62a6a5.jpg");

            Produto p9 = new Produto(
                "Roupinha de Cachorro",
                "Roupinha de rena para seu cachorro",
                120.0
            );
            p9.setImage("a8ee038c-4cd7-4d12-92f5-3c0710a2ab34.jpg");

            Produto p10 = new Produto(
                "Corda",
                "Para brincar com seu pet",
                60.0
            );
            p10.setImage("81dd508a-cb6f-4ec8-9e17-7e13011df2eb.webp");

            produtoRepository.save(p1);
            produtoRepository.save(p2);
            produtoRepository.save(p3);
            produtoRepository.save(p4);
            produtoRepository.save(p5);
            produtoRepository.save(p6);
            produtoRepository.save(p7);
            produtoRepository.save(p8);
            produtoRepository.save(p9);
            produtoRepository.save(p10);
        }
        if (usuarioRepository.findByEmail("admin@email.com") == null) {
            int carrinhoId = carrinhoRepository.save();
            Carrinho carrinho = carrinhoRepository.findById(carrinhoId);
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@email.com");
            admin.setSenha("123");
            admin.setGerente(true);
            admin.setCarrinho(carrinho);

            usuarioRepository.save(admin);
        }
    }
}