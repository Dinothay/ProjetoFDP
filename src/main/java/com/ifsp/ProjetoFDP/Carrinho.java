package com.ifsp.ProjetoFDP;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private Double valor;
    private int id;
    private List<ItemCarrinho> itens = new ArrayList<>();

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Carrinho(Double valor, List<ItemCarrinho> itens) {
        this.valor = valor;
        this.itens = itens;
    }

    public Carrinho() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

    public Double getTotal() {
        double total = 0;
        for (ItemCarrinho item : itens){
            total += item.getTotal();
        }
        return total;
    }
}