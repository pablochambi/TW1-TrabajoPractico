package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioArchivoImpl implements RepositorioArchivo{

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioArchivoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Archivo archivo) {
        sessionFactory.getCurrentSession().save(archivo);
    }

    @Override
    public List<Archivo> buscarPorIdDeUsuario(Long idUsuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Archivo a WHERE a.usuario.id = :idUsuario" , Archivo.class)
                .setParameter("idUsuario", idUsuario)
                .getResultList();
    }

}
