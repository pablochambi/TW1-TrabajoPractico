package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.servicios.ServicioNotificacion;
import com.tallerwebi.dominio.servicios.ServicioPedido;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorHome {

    private ServicioUsuario servicioUsuario;
    private ServicioNotificacion servicioNotificacion;
    private ServicioPedido servicioPedido;

    @Autowired
    public ControladorHome(ServicioUsuario servicioUsuario, ServicioNotificacion servicioNotificacion, ServicioPedido servicioPedido) {
        this.servicioUsuario = servicioUsuario;
        this.servicioNotificacion = servicioNotificacion;
        this.servicioPedido = servicioPedido;
    }


    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAlHome(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        ModelMap model = new ModelMap();

        if (session != null) {

            Long idUsuario = (Long) session.getAttribute("idUsuario");
            String rol = (String) session.getAttribute("ROL");

            Usuario usuario = servicioUsuario.buscarUsuarioPorId(idUsuario);
            List<Pedido> pedidosVencidos = servicioPedido.obtenerPedidosVencidosPorUsuario(usuario);
            List<Notificacion> notificaciones = new ArrayList<>();
            for (Pedido pedido : pedidosVencidos) {
                notificaciones.addAll(servicioNotificacion.obtenerNotificacionesPedido(pedido));
            }

            model.put("idUsuario", idUsuario);
            model.put("email", usuario.getEmail());
            model.put("nombre", usuario.getNombre());
            model.put("username", usuario.getUsername());
            model.put("notificaciones", notificaciones);
            System.out.println(notificaciones);

            if (rol.equals("CLIENTE")) {
                return new ModelAndView("homeCliente", model);
            }else if(rol.equals("ADMIN")){
                return new ModelAndView("homeAdmin", model);
            }
        }

        return new ModelAndView("redirect:/milogin");
    }

    @RequestMapping(path = "/perfil")
    public ModelAndView irAMiPerfil(HttpServletRequest request){

        Usuario usuario = obtenerUsuarioPorRequest(request);
        ModelMap modelo = new ModelMap();
        modelo.addAttribute("usuario", usuario);

        return new ModelAndView("miPerfil",modelo);
    }

    private Usuario obtenerUsuarioPorRequest(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if(session == null) {return null;}

        Long usuario_id =  (Long) request.getSession().getAttribute("idUsuario");
        return servicioUsuario.buscarUsuarioPorId(usuario_id);
    }

    @RequestMapping(path = "/editarPerfil", method = RequestMethod.POST)
    public ModelAndView editarPerfil(@RequestParam("nombre") String nombre,
                                     @RequestParam("apellido") String apellido,
                                     @RequestParam("username") String username,
                                     HttpServletRequest request) {

        Usuario usuario = obtenerUsuarioPorRequest(request);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setUsername(username);
        servicioUsuario.modificarUsuario(usuario);

        return new ModelAndView("redirect:/home");
    }

}
