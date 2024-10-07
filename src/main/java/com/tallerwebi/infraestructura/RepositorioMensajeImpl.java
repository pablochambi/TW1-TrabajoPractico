package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Mensaje;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioMensaje;
import com.tallerwebi.presentacion.MensajeDTO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioMensajeImpl implements RepositorioMensaje {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioMensajeImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardar(Mensaje nuevoMensaje) {
        Session session = sessionFactory.getCurrentSession();
        session.save(nuevoMensaje);
    }

    @Override
    public List<Mensaje> obtenerMensajes(Usuario admin, Usuario cliente) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Mensaje.class);

        criteria.add(Restrictions.or(
                Restrictions.and(
                        Restrictions.eq("emisor", admin),
                        Restrictions.eq("receptor", cliente)
                ),
                Restrictions.and(
                        Restrictions.eq("emisor", cliente),
                        Restrictions.eq("receptor", admin)
                )
        ));

        return criteria.list();
    }

}
