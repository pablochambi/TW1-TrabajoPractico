package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioArchivo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RepositorioArchivoImpl implements RepositorioArchivo {

    private final RepositorioUsuarioImpl repositorioUsuario;
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioArchivoImpl(SessionFactory sessionFactory, RepositorioUsuarioImpl repositorioUsuario) {
        this.sessionFactory = sessionFactory;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    @Transactional
    public void guardar(Archivo archivo, Usuario usuario) {
        // Recargar el usuario desde la base de datos
        Usuario usuarioPersistente = repositorioUsuario.buscarPorId(usuario.getId());
        archivo.setUsuario(usuarioPersistente);
        usuarioPersistente.getArchivos().add(archivo);
        sessionFactory.getCurrentSession().save(archivo);
    }

    @Override
    public void guardar(Archivo archivo) {
        sessionFactory.getCurrentSession().save(archivo);
    }

    @Override
    public List<Archivo> buscarPorIdDeUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Archivo.class)
                      .add(Restrictions.eq("usuario", usuario))
                      .list();
    }

    @Override
    public void eliminar(Archivo archivo) {
        sessionFactory.getCurrentSession().delete(archivo);
    }

    @Override
    public Archivo buscarPorId(Long archivoId) {
        Session session = sessionFactory.getCurrentSession();
        return (Archivo) session.createCriteria(Archivo.class)
                .add(Restrictions.eq("id",archivoId))
                .uniqueResult();
    }

    @Override
    public String getNombrePorID(Long archivoId) {
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createCriteria(Archivo.class)
                .add(Restrictions.eq("id",archivoId))
                .setProjection(Projections.property("nombre")) // Seleccionar solo el campo "nombre"
                .uniqueResult();

    }

}
