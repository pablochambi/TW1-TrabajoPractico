package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoNotificacion;
import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioNotificacion;
import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import com.tallerwebi.dominio.servicios.ServicioPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ServicioNotificacionImpl implements ServicioNotificacion {

    private final RepositorioUsuarioImpl repositorioUsuario;
    ServicioPedido servicioPedido;
    RepositorioNotificacion repositorioNotificacion;

    @Autowired
    public ServicioNotificacionImpl(ServicioPedido servicioPedido, RepositorioNotificacion repositorioNotificacion, RepositorioUsuarioImpl repositorioUsuario) {
        this.servicioPedido = servicioPedido;
        this.repositorioNotificacion = repositorioNotificacion;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    @Scheduled(fixedRate = 10000)  // Cada 10 segundos
    public void verificarPedidosVencidos() {
        List<Pedido> pedidosVencidos = servicioPedido.obtenerPedidosVencidos();
        for (Pedido pedido : pedidosVencidos){
            if(!repositorioNotificacion.existeNotificacionPedido(pedido)){
                Notificacion notificacion = new Notificacion();
                notificacion.setDescripcionNotificacion("El pedido "+pedido.getId()+" tiene una demora");
                notificacion.setFechaNotificacion(new Date());
                notificacion.setEstado(EstadoNotificacion.NUEVA);
                notificacion.setTituloNotificacion("Nueva notificacion");
                notificacion.setPedido(pedido);
                repositorioNotificacion.guardar(notificacion);
            }else{
                System.out.println("Ya hay una notificacion para este pedido");
            }
        }
    }

    @Override
    public void enviarNotificacionUsuario(Usuario usuario) {
    }

    @Override
    public List<Notificacion> obtenerNotificacionesPedido(Pedido pedido) {
        List<Notificacion> notificaciones = repositorioNotificacion.obtenerNotificacionesPedido(pedido);
        return notificaciones;
    }
}
