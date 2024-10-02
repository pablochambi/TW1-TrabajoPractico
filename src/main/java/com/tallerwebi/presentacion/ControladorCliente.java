package com.tallerwebi.presentacion;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.mysql.cj.jdbc.jmx.LoadBalanceConnectionGroupManager;
import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.servicios.ServicioArchivo;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class ControladorCliente {

    private ServicioUsuario servicioUsuario;
    private ServicioArchivo servicioArchivo;

    @Autowired
    public ControladorCliente(ServicioUsuario servicioUsuario,ServicioArchivo servicioArchivo) {
        this.servicioUsuario = servicioUsuario;
        this.servicioArchivo = servicioArchivo;
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

        HttpSession session = request.getSession(false);
        Long usuario_id = obtenerIdUsuarioPorRequest(request);
        if (session == null) {return new ModelAndView("redirect:/milogin");}


        servicioArchivo.eliminarPorId(archivo_id,usuario_id);

        return new ModelAndView("redirect:/archivos");
    }


    private Long obtenerIdUsuario(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("idUsuario");
    }

    private Usuario obtenerUsuarioPorRequest(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if(session == null) {return null;}

        Long usuario_id =  (Long) request.getSession().getAttribute("idUsuario");
        return servicioUsuario.buscarUsuarioPorId(usuario_id);
    }

    private Long obtenerIdUsuarioPorRequest(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session == null) {return null;}
        return (Long) request.getSession().getAttribute("idUsuario");
    }

}
