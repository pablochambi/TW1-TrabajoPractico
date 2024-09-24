package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.RepositorioPedido;
import com.tallerwebi.dominio.ServicioPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioPedidoImpl implements ServicioPedido {

    RepositorioPedido repositorioPedido;

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido repositorioPedido){
        this.repositorioPedido = repositorioPedido;
    }

    @Override
    public void guardar(Pedido pedidoNuevo) {

    }
}

