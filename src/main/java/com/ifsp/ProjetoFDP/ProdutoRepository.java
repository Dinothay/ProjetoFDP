package com.ifsp.ProjetoFDP;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class ProdutoRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Produto produto){
        String sql = "INSERT INTO produto (nome,descricao,preco) VALUES (:nome, :descricao, :preco)";
        Query query = em.createNativeQuery(sql);
        query.setParameter("nome", produto.getNome());
        query.setParameter("descricao", produto.getDescricao());
        query.setParameter("preco", produto.getPreco());
        query.executeUpdate();
    }

    public List<Produto> findAll() {
        String sql = "SELECT * FROM produto";
        Query q = em.createNativeQuery(sql, Produto.class);
        List<Produto> produtos = q.getResultList();
        return produtos;
    }
    public Produto findById(int id) {
        String sql = "SELECT * FROM produto WHERE id = :id";
        Query q = em.createNativeQuery(sql, Produto.class);
        q.setParameter("id", id);
        Produto produto = (Produto) q.getSingleResult();
        return produto;
    }
    @Transactional
    public void update(Produto produto){
        String sql = "UPDATE produto SET nome = :nome, descricao = :descricao, preco = :preco WHERE id = :id";
        Query query =em.createNativeQuery(sql);
        query.setParameter("id", produto.getId());
        query.setParameter("nome", produto.getNome());
        query.setParameter("descricao",produto.getDescricao());
        query.setParameter("preco", produto.getPreco());
        query.executeUpdate();
    }
    @Transactional
    public void delete(int id){
        String sql = "DELETE FROM produto WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
