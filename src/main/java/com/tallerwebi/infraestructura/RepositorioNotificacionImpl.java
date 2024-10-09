package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioNotificacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioNotificacion")
public class RepositorioNotificacionImpl implements RepositorioNotificacion {

    SessionFactory sessionFactory;

    @Autowired
    public RepositorioNotificacionImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    @Override
    public void guardar(Notificacion notificacion) {
        Session session = sessionFactory.getCurrentSession();
        session.save(notificacion);
    }

    @Override
    public boolean existeNotificacionPedido(Pedido pedido) {
        Session session = sessionFactory.getCurrentSession();
        Object resultado = session.createCriteria(Notificacion.class)
                .add(Restrictions.eq("pedido", pedido))
                .setMaxResults(1)
                .uniqueResult();

        return resultado != null;
    }

    @Override
    public List<Notificacion> obtenerNotificacionesPedido(Pedido pedido) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Notificacion.class)
                .add(Restrictions.eq("pedido", pedido))
                .list();
    }
}
