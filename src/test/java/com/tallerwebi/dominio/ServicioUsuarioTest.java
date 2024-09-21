package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.infraestructura.ServicioUsuarioImpl;
import com.tallerwebi.presentacion.ControladorRegistro;
import com.tallerwebi.presentacion.DatosUsuarioRegistro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioUsuarioTest {

    RepositorioUsuario repositorioUsuario = mock(RepositorioUsuarioImpl.class);

    private ServicioUsuario servicioUsuario = new ServicioUsuarioImpl(repositorioUsuario);

    DatosUsuarioRegistro datosUsuarioRegistroMock = mock(DatosUsuarioRegistro.class);

    Usuario usuarioMock = mock(Usuario.class);


    @BeforeEach
    public void preparacion(){
        //Datos del registro
        when(datosUsuarioRegistroMock.getEmail()).thenReturn("leo@gmail.com");
        when(datosUsuarioRegistroMock.getPassword()).thenReturn("12345");
        when(datosUsuarioRegistroMock.getC_password()).thenReturn("12345");

        when(usuarioMock.getEmail()).thenReturn("leo@gmail.com");
        when(usuarioMock.getPassword()).thenReturn("12345");
    }

    @Test
    public void queCuandoSeRegistraUnUsuario_EsteTengaUnRolUSER() {
        Usuario usuario = whenRegistroUsuario(datosUsuarioRegistroMock);
        thenElRegistroEsExitoso(usuario);
    }

    private Usuario whenRegistroUsuario(DatosUsuarioRegistro datosUsuarioRegistroMock){
        Usuario usuario = servicioUsuario.registrar(datosUsuarioRegistroMock);
        return usuario;
    }

    private void thenElRegistroEsExitoso(Usuario usuarioCreado) {
        assertThat(usuarioCreado, notNullValue());
        verify(repositorioUsuario, times(1)).guardar(usuarioCreado);
        assertThat(usuarioCreado.getRol(),equalTo("USER"));
    }



//    @Test
//    public void siLaPasswordTieneMenosDeCincoCaracteresElRegistroFalla() {
//        assertThrows(PasswordLongitudIncorrecta.class, ()->  whenRegistroUsuario("pablo@gmail.com", "123"));
//
//    }


//    @Test
//    public void siExisteUsuarioConMismoEmailElRegistroFalla() throws PasswordLongitudIncorrecta, UsuarioExistente {
//        //given
//        givenExisteUnUsuario("pablo@gmail.com", "12345");
//        when(repositorioUsuario.buscarPorEmail("pablo@gmail.com")).thenReturn(new Usuario());
//
//        //when
//        Usuario usuarioCreado = whenRegistroUsuario("pablo@gmail.com","98989");
//
//        thenElRegistroFalla(usuarioCreado);
//
//    }
//
//    private void givenExisteUnUsuario(String email, String password)  {
//        whenRegistroUsuario(email, password);
//    }
//
//    private void thenElRegistroFalla(Usuario usuarioCreado) {
//        assertThat(usuarioCreado, nullValue());
//    }


}
