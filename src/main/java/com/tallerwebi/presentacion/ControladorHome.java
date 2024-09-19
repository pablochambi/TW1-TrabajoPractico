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

@Controller
public class ControladorHome {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorHome(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }


    @RequestMapping(path = "home")
    public ModelAndView irAlHome(){
        return new ModelAndView("miHome");
    }

    @RequestMapping(path = "perfil",method = RequestMethod.GET)
    public ModelAndView irAMiPerfil(@ModelAttribute("email") String email){

        Usuario usuario = servicioUsuario.buscarUsuarioPorEmail(email);
        ModelMap modelo = new ModelMap();
        modelo.addAttribute("usuario", usuario);

        return new ModelAndView("miPerfil",modelo);
    }
}
