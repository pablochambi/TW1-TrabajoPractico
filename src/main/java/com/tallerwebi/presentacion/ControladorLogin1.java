package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin1;
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
public class ControladorLogin1 {


    private ServicioLogin1 servicioLogin1;

    @Autowired
    public ControladorLogin1(ServicioLogin1 servicioLogin1) {
        this.servicioLogin1 = servicioLogin1;
    }

    //Redireccion de vista por defecto (http://localhost:8080/spring/)
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {
        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("miLogin", modelo);
    }

    @RequestMapping(path = "/validar-miLogin", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin1.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            return new ModelAndView("redirect:/miHome");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("miLogin", model);
    }

    //Metodos de registro

    @RequestMapping(path = "/registro", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("miRegistro", model);
    }

    @RequestMapping(path = "/registrarme1", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try{
            servicioLogin1.registrar(usuario);
        } catch (UsuarioExistente e){
            model.put("error", "El usuario ya existe");
            return new ModelAndView("miRegistro", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("miRegistro", model);
        }
        return new ModelAndView("redirect:/milogin");
    }

    @RequestMapping(path = "/miHome", method = RequestMethod.GET)
    public ModelAndView irAHome() {
        return new ModelAndView("miHome");
    }



}
