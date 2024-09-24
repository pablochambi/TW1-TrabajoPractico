package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioPedidoImpl;
import com.tallerwebi.infraestructura.ServicioPedidoImpl;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class ServicioPedidoTest {
    //REPOSITORIO
    RepositorioPedido repositorioPedido = mock(RepositorioPedidoImpl.class);
    //SERVICIO
    ServicioPedido servicioPedido = new ServicioPedidoImpl(repositorioPedido);
    @Test
    public void queSePuedaSaberCantidadArchivos(){
        Pedido pedido = whenCreoPedido();
    }

    private Pedido whenCreoPedido() {
        return new Pedido();
    }
}
