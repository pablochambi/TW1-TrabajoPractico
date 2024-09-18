package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
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

    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void preparacion(){
        //Inyecciones
        servicioUsuarioMock = mock(ServicioUsuarioImpl.class);
        controladorRegistro = new ControladorRegistro(servicioUsuarioMock);
        //Datos del registro
        datosUsuarioRegistroMock = new DatosUsuarioRegistro("leo@gmail.com","LeoMessi","Leonel","Messi","12345","12345");
        //Para la simulacion de solicitudes http
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);

    }

    @Test
    public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente{

        // ejecucion
        ModelAndView modelAndView = controladorRegistro.registrarme(datosUsuarioRegistroMock);

        // validacion
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/milogin"));
        verify(servicioUsuarioMock, times(1)).registrar(datosUsuarioRegistroMock);
    }

/*
    private ModelAndView whenRegistroUsuario(String email, String password1, String password2) {
        ModelAndView mav = controladorRegistro.registrar(email,password1,password2);
        return mav;
    }

    private void thenElRegistroEsExitoso(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void siElEmailEstaVacioElRegistroFalla()  {
        givenNoExisteUsuario();
        String emailVacio = "";
        String password1= "12345";
        String password2= "12345";
        ModelAndView mav = whenRegistroUsuario(emailVacio,password1,password2);
        String mensajeDeError = "El email es obligatorio";
        thenElRegistroFalla(mav,mensajeDeError);
    }
    //siLasPasswordEstaVaciaElRegistroFalla

    private void thenElRegistroFalla(ModelAndView mav,String mensajeDeError) {
        assertThat(mav.getViewName(),equalToIgnoringCase("registro"));
        assertThat(mav.getModel().get("error").toString(),equalToIgnoringCase(mensajeDeError));
    }


    //Hacer  -  Completar
    @Test
    public void siLasPasswordSonDistintasElRegistroFalla()  {
        givenNoExisteUsuario();
        String email= "pablo@gmail.com";
        String password1= "12345";
        String password2= "123456";

        ModelAndView mav = whenRegistroUsuario(email,password1,password2);
        String mensajeDeError = "Las contraseñas tienen que coincidir";
        thenElRegistroFalla(mav,mensajeDeError);
    }


    @Test
    public void siExisteUsuarioConEmailDelRegistroElRegistroFalla()  {

        //La línea de código está simulando que cada vez que se llame
        //al metodo registrar ("pablo", "1234", Mockito lanzara la excepcion UsuarioExistente
        when(servicioUsuario.registrar("pablo","1234")).thenThrow(UsuarioExistente.class);

        ModelAndView mav = whenRegistroUsuario("pablo","1234","1234");

        thenElRegistroFalla(mav,"El usuario ya existe");
    }

*/


}
