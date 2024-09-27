package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.RepositorioArchivo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioArchivoImpl implements RepositorioArchivo {

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
        return session.createCriteria(Archivo.class)
                .add(Restrictions.eq("usuario.id", idUsuario)).list();
    }

}
