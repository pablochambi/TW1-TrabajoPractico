package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Estado;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioPedido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPedidoImpl implements RepositorioPedido {

    SessionFactory sessionFactory;

    public RepositorioPedidoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Pedido pedido) {
        Session session = sessionFactory.getCurrentSession();
        session.save(pedido);
    }

    @Override
    public void eliminar(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Pedido pedidoBuscado = session.get(Pedido.class, id);
        if (pedidoBuscado != null){
            session.delete(pedidoBuscado);
        }
    }

    @Override
    public Pedido buscar(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Pedido.class, id);
    }

    @Override
    public List<Pedido> buscarPorEstado(Estado estado) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Pedido.class)
                      .add(Restrictions.eq("estado", estado))
                      .list();
    }

    @Override
    public List<Pedido> buscarPorUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Pedido.class)
                .add(Restrictions.eq("usuario", usuario))
                .list();
    }

    @Override
    public void modificarEstado(Long id, Estado estado) {
        Session session = sessionFactory.getCurrentSession();
        Pedido pedidoBuscado = session.get(Pedido.class, id);
        if(pedidoBuscado != null){
            pedidoBuscado.setEstado(estado);
            //Actualiza el estado del pedido buscado
            session.update(pedidoBuscado);
        }
    }
}
