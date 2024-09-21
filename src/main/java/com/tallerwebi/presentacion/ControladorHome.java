package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
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
public class ControladorHome {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorHome(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }


    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAlHome(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        ModelMap model = new ModelMap();

        if (session != null) {

            Long idUsuario = (Long) session.getAttribute("idUsuario");
            String rol = (String) session.getAttribute("ROL");

            Usuario usuario = servicioUsuario.buscarUsuarioPorId(idUsuario);

            model.put("idUsuario", idUsuario);
            model.put("email", usuario.getEmail());
            model.put("nombre", usuario.getNombre());
            model.put("username", usuario.getUsername());

            if (rol.equals("CLIENTE")) {
                return new ModelAndView("homeCliente", model);
            }else if(rol.equals("ADMIN")){
                return new ModelAndView("homeAdmin", model);
            }
        }

        return new ModelAndView("redirect:/milogin");
    }

    @RequestMapping(path = "/perfil",method = RequestMethod.GET)
    public ModelAndView irAMiPerfil(@ModelAttribute("email") String email){

        Usuario usuario = servicioUsuario.buscarUsuarioPorEmail(email);
        ModelMap modelo = new ModelMap();
        modelo.addAttribute("usuario", usuario);

        return new ModelAndView("miPerfil",modelo);
    }


}
