package com.ifsp.ProjetoFDP;

public class ItemCarrinho {

    private Produto produto;
    private Double preco;
    private int quantidade;
    private Double total;

    public Double getTotal() {
        return preco * quantidade;
    }

    public ItemCarrinho(Produto produto, Double preco, int quantidade) {
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public ItemCarrinho() {
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}