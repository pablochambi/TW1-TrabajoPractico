package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Mensaje;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.servicios.ServicioArchivo;
import com.tallerwebi.dominio.servicios.ServicioMensajeria;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class ControladorAdmin {

    private ServicioUsuario servicioUsuario;
    private ServicioArchivo servicioArchivo;
    private ServicioMensajeria servicioMensajeria;

    @Autowired
    public ControladorAdmin(ServicioUsuario servicioUsuario, ServicioArchivo servicioArchivo, ServicioMensajeria servicioMensajeria) {
        this.servicioUsuario = servicioUsuario;
        this.servicioArchivo = servicioArchivo;
        this.servicioMensajeria = servicioMensajeria;
    }

    @RequestMapping(path = "/admin/catalogo", method = RequestMethod.GET)
    public ModelAndView irACatalogo() {
        return new ModelAndView("catalogo");
    }
    @RequestMapping(path = "/admin/usuarios", method = RequestMethod.GET)
    public ModelAndView irAUsuarios() {
        return new ModelAndView("usuarios");
    }
    @RequestMapping(path = "/admin/telas", method = RequestMethod.GET)
    public ModelAndView irAGestionDeTelas() {
        return new ModelAndView("gestionTelas");
    }
    @RequestMapping(path = "/admin/notificaciones", method = RequestMethod.GET)
    public ModelAndView irANotificaciones() {
        return new ModelAndView("notificaciones");
    }

    @PostMapping("/obtenerUsuariosClientes")
    @ResponseBody
    public UsuarioDTO obtenerUsuariosClientes() {

        //Long adminId =  obtenerIdUsuarioPorRequest(request);

//        List<Usuario> usuarios = servicioUsuario.obtenerUsuariosClientes();
//
//        List<UsuarioDTO> mensajesDTO = crearListaDeUsuariosDTO(usuarios);
//
        return null;
    }

    @GetMapping("/admim/obtenerMensajes")
    @ResponseBody
    public List<MensajeDTO> obtenerMensajes(HttpSession session) {
        Long clienteId = (Long) session.getAttribute("idUsuario");
        Long adminId = 1L;
        List<Mensaje> mensajes =  servicioMensajeria.obtenerMensajes(adminId, clienteId);
        List<MensajeDTO> mensajesDTO = crearListaDeMensajesDTO(mensajes);
        return mensajesDTO;
    }

    private List<MensajeDTO> crearListaDeMensajesDTO(List<Mensaje> mensajes) {
        return mensajes.stream()
                .map(this::crearMensajeDTO)
                .collect(Collectors.toList());
    }


    private  MensajeDTO crearMensajeDTO(Mensaje msj) {
        MensajeDTO msjDTO= new MensajeDTO();
        msjDTO.setId(msj.getId());
        msjDTO.setContenido(msj.getContenido());
        msjDTO.setHora(msj.getHora());
        msjDTO.setVisto(msj.isVisto());
        msjDTO.setEmisorId(msj.getEmisor().getId());
        msjDTO.setReceptorId(msj.getReceptor().getId());
        return msjDTO;
    }

    private Mensaje crearYEnviarMensaje(String mensaje, Usuario emisor, Usuario receptor) {
        Mensaje nuevoMensaje = new Mensaje();
        nuevoMensaje.setContenido(mensaje);
        nuevoMensaje.setHora(new Timestamp(System.currentTimeMillis()));
        nuevoMensaje.setVisto(false);
        nuevoMensaje.setEmisor(emisor);
        nuevoMensaje.setReceptor(receptor);
        return servicioMensajeria.guardar(nuevoMensaje);
    }


    private Long obtenerIdUsuario(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("idUsuario");
    }

    public Usuario obtenerUsuarioPorRequest(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if(session == null) {return null;}

        Long usuario_id =  (Long) session.getAttribute("idUsuario");
        return servicioUsuario.buscarUsuarioPorId(usuario_id);
    }

    public Long obtenerIdUsuarioPorRequest(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session == null) {return null;}
        return (Long) session.getAttribute("idUsuario");
    }

}
