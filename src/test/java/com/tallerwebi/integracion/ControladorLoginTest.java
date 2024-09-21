package com.tallerwebi.integracion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorLoginTest {

    private Usuario usuarioMock;
    private ServicioLogin servicioLogin;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void debeRetornarLaPaginaLoginCuandoSeNavegaALaRaiz() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/"))
                /*.andDo(print())*/
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat("redirect:/milogin", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
        assertThat(true, is(modelAndView.getModel().isEmpty()));
    }

    @Test
    public void debeRetornarLaPaginaLoginCuandoSeNavegaALLogin() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/milogin"))
                .andExpect(status().isOk())
                .andReturn();


        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("milogin"));
        assertThat(modelAndView.getModel().get("datosLogin").toString(),  containsString("com.tallerwebi.presentacion.DatosLogin"));
    }


    @Test
    public void debeRetornarALaPaginaRegistroCuandoSeNavegaALRegistro() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/registro"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("miRegistro"));
        assertThat(modelAndView.getModel().get("datosUsuarioRegistro").toString(),  containsString("com.tallerwebi.presentacion.DatosUsuarioRegistro"));
    }

    @Test
    public void debeRedirigirAHomeAdmin_CuandoElUsuarioEsAdmin() throws Exception {
        // Mockear el usuario para que tenga el rol de admin
        when(usuarioMock.getRol()).thenReturn("ADMIN");

        MvcResult result = this.mockMvc.perform(post("/milogin")
                        .param("email", "admin@empresa.com")
                        .param("password", "admin123")
                        .sessionAttr("usuario", usuarioMock)) // Simula que el usuario está en sesión
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("miHome"));
    }

    @Test
    public void debeRedirigirAHomeUser_CuandoElUsuarioEsUSER() throws Exception {
        // Mockear el usuario para que tenga el rol de USER
        when(usuarioMock.getRol()).thenReturn("USER");

        MvcResult result = this.mockMvc.perform(post("/milogin")
                        .param("email", "admin@empresa.com")
                        .param("password", "admin123")
                        .sessionAttr("usuario", usuarioMock)) // Simula que el usuario está en sesión
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/homeAdmin"));
    }

    @Test
    public void debeRedirigirAHomeAdminCuandoElUsuarioEsAdmin() throws Exception {
        // Simular un usuario con el rol de ADMIN
        Usuario usuarioAdminMock = mock(Usuario.class);
        when(usuarioAdminMock.getEmail()).thenReturn("admin@unlam.com");
        when(usuarioAdminMock.getRol()).thenReturn("ADMIN");

        // Simular que el servicio de login retorna el usuario ADMIN
        when(servicioLogin.consultarUsuario("admin@unlam.com", "admin123"))
                .thenReturn(usuarioAdminMock);

        // Realizar la solicitud POST con las credenciales del usuario ADMIN
        MvcResult result = this.mockMvc.perform(post("/milogin")
                        .param("email", "admin@unlam.com")
                        .param("password", "admin123"))
                .andExpect(status().isOk())  // Verificar que la respuesta sea 200 OK
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;

        // Verificar que la vista retornada es "homeAdmin"
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("miHome"));

    }




//    @Test
//    public queSePuedaIrALaVistaRegistroCuandoSepresionaRegistro(){
//
//    }




}

