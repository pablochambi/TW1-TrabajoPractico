package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
@Controller
public class ControladorLogin {


    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    //Redireccion de vista por defecto (http://localhost:8080/spring/)
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/milogin");
    }

    @RequestMapping("/milogin")
    public ModelAndView irALogin() {
        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("miLogin", modelo);
    }

    @RequestMapping(path = "/validar-miLogin", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());

        if (usuarioBuscado != null) {
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            request.getSession().setAttribute("idUsuario", usuarioBuscado.getId());

            return new ModelAndView("redirect:/home",model);   //Hace una nueva solicitud

        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("miLogin", model);
    }

//    @RequestMapping(path = "/miHome", method = RequestMethod.GET)
//    public ModelAndView irAHome(@ModelAttribute("email") String email) {
//        ModelMap modelo = new ModelMap();
//        modelo.addAttribute("email", email);
//        return new ModelAndView("miHome", modelo);
//    }

    @RequestMapping(path = "/registro", method = RequestMethod.GET)
    public ModelAndView irALaVistaRegistroConDatosUsuarioRegistroVacio() {
        ModelMap model = new ModelMap();
        model.put("datosUsuarioRegistro", new DatosUsuarioRegistro());
        return new ModelAndView("miRegistro", model);
    }


}
