package com.ifsp.ProjetoFDP;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha, gerente) VALUES (:nome, :email, :senha, :gerente)";
        Query query = em.createNativeQuery(sql);
        query.setParameter("nome", usuario.getNome());
        query.setParameter("email", usuario.getEmail());
        query.setParameter("senha", usuario.getSenha());
        query.setParameter("gerente", usuario.isGerente());
        query.executeUpdate();
    }

    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuario";
        Query query = em.createNativeQuery(sql, Usuario.class);
        return query.getResultList();
    }

    public Usuario findById(int id) {
        String sql = "SELECT * FROM usuario WHERE id = :id";
        Query query = em.createNativeQuery(sql, Usuario.class);
        query.setParameter("id", id);
        return (Usuario) query.getSingleResult();
    }

    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = :email";
        Query query = em.createNativeQuery(sql, Usuario.class);
        query.setParameter("email", email);
        List<Usuario> usuarios = query.getResultList();
        if (usuarios.isEmpty()) {
            return null;
        }
        return usuarios.get(0);
    }

    @Transactional
    public void update(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = :nome, email = :email, senha = :senha, gerente = :gerente WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", usuario.getId());
        query.setParameter("nome", usuario.getNome());
        query.setParameter("email", usuario.getEmail());
        query.setParameter("senha", usuario.getSenha());
        query.setParameter("gerente", usuario.isGerente());
        query.executeUpdate();
    }

    @Transactional
    public void delete(int id) {
        String sql = "DELETE FROM usuario WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}