package com.ifsp.ProjetoFDP;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class ItemCarrinhoRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(ItemCarrinho item) {
        String sql = "INSERT INTO item_carrinho (produto_id, carrinho_id, quantidade) VALUES (:produto_id, :carrinho_id, :quantidade)";
        Query query = em.createNativeQuery(sql);
        query.setParameter("produto_id", item.getProduto().getId());
        query.setParameter("carrinho_id", item.getCarrinho().getId());
        query.setParameter("quantidade", item.getQuantidade());
        query.executeUpdate();
    }

    public List<ItemCarrinho> findAll() {
        String sql = "SELECT * FROM item_carrinho";
        Query query = em.createNativeQuery(sql, ItemCarrinho.class);
        return query.getResultList();
    }

    public List<ItemCarrinho> findByCarrinhoId(int carrinhoId) {
        String sql = "SELECT * FROM item_carrinho WHERE carrinho_id = :carrinho_id";
        Query query = em.createNativeQuery(sql, ItemCarrinho.class);
        query.setParameter("carrinho_id", carrinhoId);
        return query.getResultList();
    }

    @Transactional
    public void update(ItemCarrinho item) {
        String sql = "UPDATE item_carrinho SET quantidade = :quantidade WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", item.getId());
        query.setParameter("quantidade", item.getQuantidade());
        query.executeUpdate();
    }

    @Transactional
    public void delete(int id) {
        String sql = "DELETE FROM item_carrinho WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}