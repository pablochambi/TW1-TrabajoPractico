package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContrasenasDistintas;
import com.tallerwebi.dominio.excepcion.NombreDeUsuarioRepetido;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

    ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorRegistro(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }



    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("datosUsuarioRegistro") DatosUsuarioRegistro datosUsuarioRegistro) {
        ModelMap model = new ModelMap();
        try {
            servicioUsuario.registrar(datosUsuarioRegistro);
        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
            return new ModelAndView("miRegistro", model);
        } catch (NombreDeUsuarioRepetido e) {
            model.put("error", "Error, nombre de usuario repetido");
            return new ModelAndView("miRegistro", model);
        }catch (ContrasenasDistintas e) {
            model.put("error", "Las contraseñas no son iguales");
            return new ModelAndView("miRegistro", model);
        }catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("miRegistro", model);
        }

        return new ModelAndView("redirect:/milogin");
    }


}
