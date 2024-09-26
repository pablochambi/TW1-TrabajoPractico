package com.tallerwebi.presentacion;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


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


    private static final String RUTA_ARCHIVOS = "src/main/webapp/resources/core/archivos/";

    @Autowired
    public ControladorCliente(ServicioUsuario servicioUsuario,ServicioArchivo servicioArchivo) {
        this.servicioUsuario = servicioUsuario;
        this.servicioArchivo = servicioArchivo;
    }

    @RequestMapping(path = "/archivos")
    public ModelAndView irAMisArchivos(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        ModelMap model = new ModelMap();

        if (session != null) {
            Long idUsuario = (Long) session.getAttribute("idUsuario");
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(idUsuario);

            model.put("nombre", usuario.getNombre());
            model.put("apellido", usuario.getApellido());
            model.put("email", usuario.getEmail());

            List<Archivo> archivosSubidos;
            archivosSubidos = servicioArchivo.buscarArchivosPorIdDeUsuario(idUsuario);

            model.put("archivosSubidos", archivosSubidos);

        }else {
            return new ModelAndView("redirect:/milogin");
        }
        return new ModelAndView("archivos",model);

    }

    @PostMapping("/subirArchivo")
    public ModelAndView subirArchivo(@RequestParam("archivo") MultipartFile archivo,HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession(false);
        if(session == null){return new ModelAndView("redirect:/milogin");}

        String nombreArchivo = archivo.getOriginalFilename();
        String extension = extraerExtencion(nombreArchivo);
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(idUsuario);

        ModelMap model = new ModelMap();

        model.put("nombre", usuario.getNombre());
        model.put("apellido", usuario.getApellido());
        model.put("email", usuario.getEmail());

        if (archivo.isEmpty()) {
            model.put("error", "Debe seleccionar un archivo.");
            return new ModelAndView("archivos",model);
        }

        if (!extension.equals("jpg") && !extension.equals("pdf")) {
            model.put("error", "Solo se permiten archivos con extensión .jpg o .pdf");
            return new ModelAndView("archivos",model);
        }

        guardarArchivoEnLaCarpetaArchivos(archivo, nombreArchivo, usuario);

        model.put("mensaje", "Archivo subido exitosamente.");
        return new ModelAndView("archivos",model);
    }

    @GetMapping("/archivos/lista")
    @ResponseBody//<----- Convierte una lista de archivos a JSON
    public List<Archivo> obtenerListaArchivos(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            Long idUsuario = (Long) session.getAttribute("idUsuario");
            List<Archivo> archivos = servicioArchivo.buscarArchivosPorIdDeUsuario(idUsuario);
            return archivos;
        }
        return new ArrayList<>();
    }


    private void guardarArchivoEnLaCarpetaArchivos(MultipartFile archivo, String nombreArchivo,Usuario usuario) throws IOException {
        byte[] bytes = archivo.getBytes();
        Path ruta = Paths.get(RUTA_ARCHIVOS + nombreArchivo);
        Files.write(ruta, bytes);

        // Obtener el tamaño del archivo en MB
        Double tamanioEnMb = archivo.getSize() / 1048576.0;
        String extencion = extraerExtencion(nombreArchivo);


        Archivo arch = new Archivo();
        arch.setUsuario(usuario);
        arch.setNombre(nombreArchivo);
        arch.setTipo(extencion);
        arch.setPeso(tamanioEnMb);
        arch.setPedido(null);
        arch.setDireccion(RUTA_ARCHIVOS + nombreArchivo);

        servicioArchivo.registrar(arch);

    }

    private static String extraerExtencion(String nombreArchivo) {
        return Objects.requireNonNull(nombreArchivo).substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();
    }


}
