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

//@RestController
@Controller
public class ControladorCliente {

    private ServicioUsuario servicioUsuario;
    private ServicioArchivo servicioArchivo;
    private ServicioMensajeria servicioMensajeria;

    @Autowired
    public ControladorCliente(ServicioUsuario servicioUsuario,ServicioArchivo servicioArchivo,ServicioMensajeria servicioMensajeria) {
        this.servicioUsuario = servicioUsuario;
        this.servicioArchivo = servicioArchivo;
        this.servicioMensajeria = servicioMensajeria;
    }

    @RequestMapping(path = "/archivos")
    public ModelAndView irAMisArchivos(HttpServletRequest request, ModelMap model) {

        Usuario usuario = obtenerUsuarioPorRequest(request);
        if(usuario == null) {return new ModelAndView("redirect:/milogin");}

        model.put("nombre", usuario.getNombre());
        model.put("apellido", usuario.getApellido());
        model.put("email", usuario.getEmail());

        List<Archivo> archivosSubidos = servicioArchivo.buscarArchivosPorIdDeUsuario(usuario.getId());

        model.put("archivos", archivosSubidos);

        return new ModelAndView("archivos",model);
    }

    @RequestMapping(path = "/subirArchivo", method = RequestMethod.GET)
    public ModelAndView irAsubirArchivo() {
        return new ModelAndView("subirArchivo");
    }

    @PostMapping("/subirArchivo/subir")
    public ModelAndView subirArchivo(@RequestParam("archivo") MultipartFile file, HttpServletRequest request) throws IOException {

        Long usuario_id = obtenerIdUsuarioPorRequest(request);
        ModelMap model = new ModelMap();

        if(usuario_id == null) {return new ModelAndView("redirect:/milogin");}

        if (file.isEmpty()) {
            model.put("error", "Debe seleccionar un archivo.");
            return new ModelAndView("subirArchivo",model);
        }

        if(servicioArchivo.noEsExtencionValida(file)){
            model.put("error", "Solo se permiten archivos con extensión .jpg o .pdf");
            return new ModelAndView("subirArchivo",model);
        }

        servicioArchivo.guardar(file,usuario_id);

        model.put("mensaje", "Archivo subido exitosamente.");
        return new ModelAndView("subirArchivo",model);
    }

    @RequestMapping(path = "/archivos/eliminar", method = RequestMethod.GET)
    public ModelAndView eliminarUnArchivo(@RequestParam("archivo_id") Long archivo_id, HttpServletRequest request) throws IOException {

        Long usuario_id = obtenerIdUsuarioPorRequest(request);
        if (usuario_id == null) {return new ModelAndView("redirect:/milogin");}

        servicioArchivo.eliminarPorId(archivo_id,usuario_id);

        return new ModelAndView("redirect:/archivos");
    }

    @PostMapping("/enviarMensaje")
    @ResponseBody
    public MensajeDTO enviarMensaje(@RequestParam("mensaje") String mensaje,HttpServletRequest request) {

        Long adminId = 1L;
        Long emisorId = (request != null) ? obtenerIdUsuarioPorRequest(request) : 2L; // Usa un valor por defecto si request es null
        try {
        // Obtener el emisor (en este caso, un cliente) desde la base de datos
        Usuario emisor = servicioUsuario.buscarUsuarioPorId(emisorId);

        // Obtener el receptor (administrador) desde la base de datos
        Usuario receptor = servicioUsuario.buscarUsuarioPorId(adminId);

        // Crear el nuevo mensaje
        Mensaje nuevoMensaje = new Mensaje();
        nuevoMensaje.setContenido(mensaje);
        nuevoMensaje.setHora(new Timestamp(System.currentTimeMillis()));
        nuevoMensaje.setVisto(false);
        nuevoMensaje.setEmisor(emisor);
        nuevoMensaje.setReceptor(receptor);



        Mensaje msj = servicioMensajeria.guardar(nuevoMensaje);

        MensajeDTO msjDTO= new MensajeDTO();

        msjDTO.setId(msj.getId());
        msjDTO.setContenido(msj.getContenido());
        msjDTO.setHora(msj.getHora());
        msjDTO.setVisto(msj.isVisto());
        msjDTO.setEmisorId(msj.getEmisor().getId());
        msjDTO.setReceptorId(msj.getReceptor().getId());

        System.out.println(msjDTO);

        return msjDTO;

        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error en los logs del servidor
            throw new RuntimeException("Error al enviar el mensaje: " + e.getMessage());
        }
    }


    private Long obtenerIdUsuario(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("idUsuario");
    }

    public Usuario obtenerUsuarioPorRequest(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if(session == null) {return null;}

        Long usuario_id =  (Long) request.getSession().getAttribute("idUsuario");
        return servicioUsuario.buscarUsuarioPorId(usuario_id);
    }

    public Long obtenerIdUsuarioPorRequest(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session == null) {return null;}
        return (Long) request.getSession().getAttribute("idUsuario");
    }

}
