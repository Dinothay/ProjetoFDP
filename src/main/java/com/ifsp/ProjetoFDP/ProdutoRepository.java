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
    public void save(Produto produto) {
        String sql = "INSERT INTO produto (nome, descricao, preco, image)VALUES (:nome, :descricao, :preco, :image)";

        Query query = em.createNativeQuery(sql);

        query.setParameter("nome", produto.getNome());
        query.setParameter("descricao", produto.getDescricao());
        query.setParameter("preco", produto.getPreco());
        query.setParameter("image", produto.getImage());

        query.executeUpdate();
    }

    public List<Produto> findAll() {
        String sql = "SELECT * FROM produto";
        Query q = em.createNativeQuery(sql, Produto.class);
        return q.getResultList();
    }

    public Produto findById(int id) {
        String sql = "SELECT * FROM produto WHERE id = :id";

        Query q = em.createNativeQuery(sql, Produto.class);
        q.setParameter("id", id);

        return (Produto) q.getSingleResult();
    }

    @Transactional
    public void update(Produto produto) {
        String sql = "UPDATE produto SET nome = :nome, descricao = :descricao, preco = :preco, image = :image WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", produto.getId());
        query.setParameter("nome", produto.getNome());
        query.setParameter("descricao", produto.getDescricao());
        query.setParameter("preco", produto.getPreco());
        query.setParameter("image", produto.getImage());
        query.executeUpdate();
    }

    @Transactional
    public void delete(int id) {
        String sql = "DELETE FROM produto WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<Produto> findByNome(String nome) {
        String sql = "SELECT * FROM produto WHERE nome LIKE :nome";
        Query query = em.createNativeQuery(sql, Produto.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    public List<Produto> findPage(int pagina, int tamanho) {
        int inicio = (pagina - 1) * tamanho;
        String sql = "SELECT *FROM produto LIMIT :tamanho OFFSET :inicio";
        Query query = em.createNativeQuery(sql, Produto.class);
        query.setParameter("tamanho", tamanho);
        query.setParameter("inicio", inicio);
        return query.getResultList();
    }

    public List<Produto> findByNomePage(String nome, int pagina, int tamanho) {

        int inicio = (pagina - 1) * tamanho;
        String sql = "SELECT * FROM produto WHERE nome LIKE :nome LIMIT :tamanho OFFSET :inicio ";
        Query query = em.createNativeQuery(sql, Produto.class);
        query.setParameter("nome", "%" + nome + "%");
        query.setParameter("tamanho", tamanho);
        query.setParameter("inicio", inicio);
        return query.getResultList();
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM produto";
        Query query = em.createNativeQuery(sql);
        Number resultado = (Number) query.getSingleResult();
        return resultado.intValue();
    }

    public int countByNome(String nome) {
        String sql = "SELECT COUNT(*) FROM produto WHERE nome LIKE :nome";
        Query query = em.createNativeQuery(sql);
        query.setParameter("nome", "%" + nome + "%");
        Number resultado = (Number) query.getSingleResult();
        return resultado.intValue();
    }
}