package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Mensaje;
import com.tallerwebi.dominio.Usuario;

import com.tallerwebi.dominio.servicios.ServicioArchivo;
import com.tallerwebi.dominio.servicios.ServicioMensajeria;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import com.tallerwebi.infraestructura.ServicioArchivoImpl;
import com.tallerwebi.infraestructura.ServicioMensajeriaImpl;
import com.tallerwebi.infraestructura.ServicioUsuarioImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorClienteTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    private ServicioUsuario servicioUsuarioMock;
    private ServicioMensajeria servicioMensajeriaMock;
    private ServicioArchivo servicioArchivoMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    private ControladorCliente controladorCliente;

    private Usuario emisorMock;
    private Usuario receptorMock;
    private Mensaje mensajeMock;
    private MensajeDTO mensajeDTOMock;

    @BeforeEach
    public void preparacion() {
        // Inyecciones
        servicioUsuarioMock = mock(ServicioUsuarioImpl.class);
        servicioMensajeriaMock = mock(ServicioMensajeriaImpl.class);
        servicioArchivoMock = mock(ServicioArchivoImpl.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);

        // Configuración del mock de la sesión
        when(requestMock.getSession(false)).thenReturn(sessionMock);// Usar getSession(false) para no crear una nueva sesión
        when(sessionMock.getAttribute("idUsuario")).thenReturn(2L);

        controladorCliente = new ControladorCliente(servicioUsuarioMock, servicioArchivoMock, servicioMensajeriaMock);

        // Datos del mensaje
        emisorMock = new Usuario();
        emisorMock.setId(2L);
        receptorMock = new Usuario();
        receptorMock.setId(1L);

        mensajeMock = new Mensaje();
        mensajeMock.setContenido("Hola");
        mensajeMock.setHora(new Timestamp(System.currentTimeMillis()));
        mensajeMock.setVisto(false);
        mensajeMock.setEmisor(emisorMock);
        mensajeMock.setReceptor(receptorMock);

        mensajeDTOMock = new MensajeDTO();
        mensajeDTOMock.setContenido(mensajeMock.getContenido());
        mensajeDTOMock.setHora(mensajeMock.getHora());
        mensajeDTOMock.setVisto(mensajeMock.isVisto());
        mensajeDTOMock.setEmisorId(mensajeMock.getEmisor().getId());
        mensajeDTOMock.setReceptorId(mensajeMock.getReceptor().getId());

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void cuandoElMensajeEsEnviado_DeberiaGuardarYDevolverMensaje() {
        // Preparación
        when(servicioUsuarioMock.buscarUsuarioPorId(2L)).thenReturn(emisorMock);
        when(servicioUsuarioMock.buscarUsuarioPorId(1L)).thenReturn(receptorMock);
        when(servicioMensajeriaMock.guardar(any(Mensaje.class))).thenReturn(mensajeMock);

        // Ejecución
        MensajeDTO resultado = controladorCliente.enviarMensaje("Hola", requestMock);

        // Validación
        assertThat(resultado,notNullValue());
        thenElMensajeEsEnviado(resultado);
    }

    private void thenElMensajeEsEnviado(MensajeDTO mensaje) {
        assertThat(mensaje.getContenido(), equalToIgnoringCase("Hola"));
        assertThat(mensaje.getEmisorId(), equalTo(2L));
        assertThat(mensaje.getReceptorId(), equalTo(1L));
        verify(servicioUsuarioMock, times(1)).buscarUsuarioPorId(2L);
        verify(servicioUsuarioMock, times(1)).buscarUsuarioPorId(1L);
        verify(servicioMensajeriaMock, times(1)).guardar(any(Mensaje.class));
    }

}