package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.RepositorioPedido;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RepositorioPedidoImpl implements RepositorioPedido {

    SessionFactory sessionFactory;

    public RepositorioPedidoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Pedido pedido) {
        Session session =sessionFactory.getCurrentSession();
        session.save(pedido);
    }
}
