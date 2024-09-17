package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.infraestructura.ServicioUsuarioImpl;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioUsuarioTest {

    RepositorioUsuario repositorioUsuario = mock(RepositorioUsuarioImpl.class);

    private ServicioUsuario servicioUsuario = new ServicioUsuarioImpl(repositorioUsuario);

    @Test
    public void siExisteEmailYPasswordElRegistroEsExitoso() throws PasswordLongitudIncorrecta, UsuarioExistente {
        Usuario usuarioCreado = whenRegistroUsuario("pablo@gmail.com", "12345");
        thenElRegistroEsExitoso(usuarioCreado);
    }

    private Usuario whenRegistroUsuario(String email, String password){
        return servicioUsuario.registrar(email,password);
    }

    private void thenElRegistroEsExitoso(Usuario usuarioCreado) {
        assertThat(usuarioCreado, notNullValue());
        verify(repositorioUsuario, times(1)).guardar(usuarioCreado);
    }

    @Test
    public void siLaPasswordTieneMenosDeCincoCaracteresElRegistroFalla() {
        assertThrows(PasswordLongitudIncorrecta.class, ()->  whenRegistroUsuario("pablo@gmail.com", "123"));
    }


    @Test
    public void siExisteUsuarioConMismoEmailElRegistroFalla() throws PasswordLongitudIncorrecta, UsuarioExistente {
        //given
        givenExisteUnUsuario("pablo@gmail.com", "12345");
        when(repositorioUsuario.buscarUsuarioPorEmail("pablo@gmail.com")).thenReturn(new Usuario());

        //when
        Usuario usuarioCreado = whenRegistroUsuario("pablo@gmail.com","98989");

        thenElRegistroFalla(usuarioCreado);

    }

    private void givenExisteUnUsuario(String email, String password)  {
        whenRegistroUsuario(email, password);
    }

    private void thenElRegistroFalla(Usuario usuarioCreado) {
        assertThat(usuarioCreado, nullValue());
    }


}
