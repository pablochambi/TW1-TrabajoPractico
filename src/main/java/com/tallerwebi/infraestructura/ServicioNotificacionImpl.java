package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service("servicioNotificacion")
public class ServicioNotificacionImpl implements ServicioNotificacion {

    @Override
    @Scheduled(fixedRate = 10000)  // Cada 10 segundos
    public void verificarPedidosVencidos() {
        System.out.println("Verifica si hay pedidos vencidos");
    }

    @Override
    public void enviarNotificacionUsuario(Usuario usuario) {
    }
}
