package com.ifsp.ProjetoFDP;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class CarrinhoRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public int save() {
        String sql = "INSERT INTO carrinho () VALUES ()";
        Query query = em.createNativeQuery(sql);
        query.executeUpdate();

        Query q = em.createNativeQuery("SELECT MAX(id) FROM carrinho");
        return ((Number) q.getSingleResult()).intValue();
    }

    public List<Carrinho> findAll() {
        String sql = "SELECT * FROM carrinho";
        Query q = em.createNativeQuery(sql, Carrinho.class);
        return q.getResultList();
    }

    public Carrinho findById(int id) {
        String sql = "SELECT * FROM carrinho WHERE id = :id";
        Query q = em.createNativeQuery(sql, Carrinho.class);
        q.setParameter("id", id);
        return (Carrinho) q.getSingleResult();
    }

    @Transactional
    public void delete(int id) {
        String sql = "DELETE FROM carrinho WHERE id = :id";
        Query q = em.createNativeQuery(sql);
        q.setParameter("id", id);
        q.executeUpdate();
    }
}