package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.servicios.ServicioUsuario;
import com.tallerwebi.dominio.excepciones.ContrasenasDistintas;
import com.tallerwebi.dominio.excepciones.NombreDeUsuarioRepetido;
import com.tallerwebi.dominio.excepciones.PasswordLongitudIncorrecta;
import com.tallerwebi.dominio.excepciones.UsuarioExistente;
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
        String errorMessage = null;

        try {
            servicioUsuario.registrar(datosUsuarioRegistro);
        } catch (UsuarioExistente e) {
            errorMessage = "El usuario ya existe";
        } catch (NombreDeUsuarioRepetido e) {
            errorMessage = "El nombre de usuario ya existe";
        } catch (ContrasenasDistintas e) {
            errorMessage = "Las contraseñas no son iguales";
        } catch (PasswordLongitudIncorrecta e) {
            errorMessage = "La contraseña tiene que ser mayor a 5";
        } catch (Exception e) {
            errorMessage = "Error al registrar el nuevo usuario";
        }

        if (errorMessage != null) {
            model.put("error", errorMessage);
            return new ModelAndView("miRegistro", model);
        }

        return new ModelAndView("redirect:/milogin");
    }


}
