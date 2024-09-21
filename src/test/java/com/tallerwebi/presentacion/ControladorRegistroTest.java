package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContrasenasDistintas;
import com.tallerwebi.dominio.excepcion.NombreDeUsuarioRepetido;
import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.infraestructura.ServicioUsuarioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ControladorRegistroTest {

    private ServicioUsuario servicioUsuarioMock;
    private ControladorRegistro controladorRegistro;

    private Usuario usuarioMock;
    private DatosUsuarioRegistro datosUsuarioRegistroMock;

    @BeforeEach
    public void preparacion(){
        //Inyecciones
        servicioUsuarioMock = mock(ServicioUsuarioImpl.class);
        controladorRegistro = new ControladorRegistro(servicioUsuarioMock);

        //Datos del registro
        datosUsuarioRegistroMock = new DatosUsuarioRegistro("leo@gmail.com","LeoMessi","Leonel","Messi","12345","12345");
    }

    @Test
    public void cuandoElUsuarioNoExiste_DeberiaCrearUsuarioYRedirigirAlLogin() throws UsuarioExistente {
        ModelAndView mav = whenRegistroUsuario(datosUsuarioRegistroMock);
        thenElRegistroEsExitoso(mav);
    }


    @Test
    public void cuandoElEmailDelUsuarioYaExiste_DeberiaVolverAFormularioYMostrarError() throws UsuarioExistente{
        // preparacion
        doThrow(UsuarioExistente.class).when(servicioUsuarioMock).registrar(datosUsuarioRegistroMock);
        // ejecucion
        ModelAndView mav = whenRegistroUsuario(datosUsuarioRegistroMock);
        // validacion
        thenElRegistroFalla(mav,"miRegistro","El usuario ya existe");
    }

    @Test
    public void cuandoElNombreDeUsuarioYaExiste_DeberiaVolverAFormularioYMostrarError() throws UsuarioExistente{
        // preparacion
        doThrow(NombreDeUsuarioRepetido.class).when(servicioUsuarioMock).registrar(datosUsuarioRegistroMock);
        // ejecucion
        ModelAndView mav = whenRegistroUsuario(datosUsuarioRegistroMock);
        // validacion
        thenElRegistroFalla(mav,"miRegistro","El nombre de usuario ya existe");
    }

    @Test
    public void cuandoLasContrasenasNoCoinciden_DeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, NombreDeUsuarioRepetido {

        //preparacion
        doThrow(ContrasenasDistintas.class).when(servicioUsuarioMock).registrar(datosUsuarioRegistroMock);

        // ejecucion
        ModelAndView mav = whenRegistroUsuario(datosUsuarioRegistroMock);
        //Validacion
        thenElRegistroFalla(mav,"miRegistro","Las contraseñas no son iguales");
    }

    @Test
    public void cuandoLaContraseniaEsMenorACinco_DeberiaVolverAFormularioYMostrarError() throws UsuarioExistente, NombreDeUsuarioRepetido {

        //preparacion
        doThrow(PasswordLongitudIncorrecta.class).when(servicioUsuarioMock).registrar(datosUsuarioRegistroMock);

        // ejecucion
        ModelAndView mav = whenRegistroUsuario(datosUsuarioRegistroMock);
        //Validacion
        thenElRegistroFalla(mav,"miRegistro","La contraseña tiene que ser mayor a 5");
    }


    private ModelAndView whenRegistroUsuario(DatosUsuarioRegistro datosUsuarioRegistroMock) {
        ModelAndView mav = controladorRegistro.registrarme(datosUsuarioRegistroMock);
        return mav;
    }

    private void thenElRegistroEsExitoso(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/milogin"));
        verify(servicioUsuarioMock, times(1)).registrar(datosUsuarioRegistroMock);
    }

    private void thenElRegistroFalla(ModelAndView mav,String vista,String mensajeError) {
        assertThat(mav.getViewName(), equalToIgnoringCase(vista));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase(mensajeError));
    }



}
