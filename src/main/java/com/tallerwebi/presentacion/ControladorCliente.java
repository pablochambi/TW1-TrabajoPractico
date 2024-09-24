package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.infraestructura.ServicioUsuarioImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorCliente {

    ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorCliente(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping(path = "/archivos")
    public ModelAndView irAMisArchivos(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        ModelMap model = new ModelMap();

        if (session != null) {

            Long idUsuario = (Long) session.getAttribute("idUsuario");

            Usuario usuario = servicioUsuario.buscarUsuarioPorId(idUsuario);

            model.put("nombre", usuario.getNombre());
            model.put("apellido", usuario.getApellido());

        }else {
            model.put("nombre", "Usuario no encontrado");
        }
        return new ModelAndView("archivos",model);
    }




}
