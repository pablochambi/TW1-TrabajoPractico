package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PasswordLongitudIncorrecta;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.infraestructura.RepositorioUsuario1Impl;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioUsuario1Test {

    RepositorioUsuario1 repositorioUsuario1 = mock(RepositorioUsuario1Impl.class);

    private ServicioUsuario1 servicioUsuario1 = new ServicioUsuario1Impl(repositorioUsuario1);

    @Test
    public void siExisteEmailYPasswordElRegistroEsExitoso() throws PasswordLongitudIncorrecta, UsuarioExistente {
        Usuario usuarioCreado = whenRegistroUsuario("pablo@gmail.com", "12345");
        thenElRegistroEsExitoso(usuarioCreado);
    }

    private Usuario whenRegistroUsuario(String email, String password){
        return servicioUsuario1.registrar(email,password);
    }

    private void thenElRegistroEsExitoso(Usuario usuarioCreado) {
        assertThat(usuarioCreado, notNullValue());
        verify(repositorioUsuario1, times(1)).guardar(usuarioCreado);
    }

    @Test
    public void siLaPasswordTieneMenosDeCincoCaracteresElRegistroFalla() {
        assertThrows(PasswordLongitudIncorrecta.class, ()->  whenRegistroUsuario("pablo@gmail.com", "123"));
    }


    @Test
    public void siExisteUsuarioConMismoEmailElRegistroFalla() throws PasswordLongitudIncorrecta, UsuarioExistente {
        //given
        givenExisteUnUsuario("pablo@gmail.com", "12345");
        when(repositorioUsuario1.buscar("pablo@gmail.com")).thenReturn(new Usuario());

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
