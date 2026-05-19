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
    public void save(Carrinho carrinho){
        String sql = "INSERT INTO carrinho (valor) VALUES (:valor)";
        Query query = em.createNativeQuery(sql);
        query.setParameter("valor", carrinho.getValor());
        query.executeUpdate();
    }

    public List<Carrinho> findAll() {
        String sql = "SELECT * FROM carrinho";
        Query q = em.createNativeQuery(sql, Carrinho.class);
        List<Carrinho> carrinhos = q.getResultList();
        return carrinhos;
    }

    public Carrinho findById(int id) {
        String sql = "SELECT * FROM carrinho WHERE id = :id";
        Query q = em.createNativeQuery(sql, Carrinho.class);
        q.setParameter("id", id);
        Carrinho carrinho = (Carrinho) q.getSingleResult();
        return carrinho;
    }

    @Transactional
    public void update(Carrinho carrinho){
        String sql = "UPDATE carrinho SET valor = :valor WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", carrinho.getId());
        query.setParameter("valor", carrinho.getValor());
        query.executeUpdate();
    }

    @Transactional
    public void delete(int id){
        String sql = "DELETE FROM carrinho WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}