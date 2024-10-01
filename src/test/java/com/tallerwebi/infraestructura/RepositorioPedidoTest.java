package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Estado;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.repositorios.RepositorioPedido;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioPedidoTest {

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    RepositorioPedido repositorioPedido;

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnPedido(){
        Pedido pedido =new Pedido();
        repositorioPedido.guardar(pedido);
        assertThat(pedido.getId(), notNullValue());
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaEliminarUnPedido(){
        Pedido pedido =new Pedido();
        Pedido otroPedido =new Pedido();
        sessionFactory.getCurrentSession().save(pedido);
        sessionFactory.getCurrentSession().save(otroPedido);

        repositorioPedido.eliminar(otroPedido.getId());
        Pedido pedidoBuscado = sessionFactory.getCurrentSession().get(Pedido.class, otroPedido.getId());

        assertThat(pedidoBuscado, nullValue());
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaModificarElEstadoDeUnPedido(){
        Pedido pedido =new Pedido();
        pedido.setEstado(Estado.IMPRESO);
        sessionFactory.getCurrentSession().save(pedido);
        repositorioPedido.modificarEstado(pedido.getId(), Estado.A_RETIRAR);

        assertEquals(Estado.A_RETIRAR,pedido.getEstado());
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaBuscarUnPedidoPorId(){
        Pedido pedido =new Pedido();
        Pedido otroPedido =new Pedido();

        pedido.setEstado(Estado.IMPRESO);
        otroPedido.setEstado(Estado.CALANDRADO);

        sessionFactory.getCurrentSession().save(pedido);
        sessionFactory.getCurrentSession().save(otroPedido);

        Pedido pedidoBuscado = repositorioPedido.buscar(pedido.getId());

        assertThat(pedidoBuscado, notNullValue());
        assertEquals(Estado.IMPRESO, pedidoBuscado.getEstado());
    }

    @Test
    @Transactional
    @Rollback
    public void queSePuedaObtenerUnaListaDePedidosPorEstado(){
        Pedido pedido1 =new Pedido();
        Pedido pedido2 =new Pedido();
        Pedido pedido3 =new Pedido();
        Pedido pedido4 =new Pedido();

        pedido1.setEstado(Estado.IMPRESO);
        pedido2.setEstado(Estado.EN_ESPERA);
        pedido3.setEstado(Estado.IMPRESO);
        pedido4.setEstado(Estado.IMPRESO);

        sessionFactory.getCurrentSession().save(pedido1);
        sessionFactory.getCurrentSession().save(pedido2);
        sessionFactory.getCurrentSession().save(pedido3);
        sessionFactory.getCurrentSession().save(pedido4);

        List<Pedido> pedidosObtenidos = repositorioPedido.buscarPorEstado(Estado.IMPRESO);

        assertEquals(3, pedidosObtenidos.size());
    }
}
