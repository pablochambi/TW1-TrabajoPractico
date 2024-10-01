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

    /*private static final String RUTA_ARCHIVOS = "src/main/webapp/resources/core/archivos/";

    @Autowired
    public ControladorCliente(ServicioUsuario servicioUsuario,ServicioArchivo servicioArchivo) {
        this.servicioUsuario = servicioUsuario;
        this.servicioArchivo = servicioArchivo;
    }

    @RequestMapping(path = "/archivos")
    public ModelAndView irAMisArchivos(HttpServletRequest request, ModelMap model) {

        HttpSession session = request.getSession(false);

        //ModelMap model = new ModelMap();

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
    public ModelAndView subirArchivo(@RequestParam("archivo") MultipartFile archivo, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        HttpSession session = request.getSession(false);
        if(session == null){return new ModelAndView("redirect:/milogin");}

        String nombreArchivo = archivo.getOriginalFilename();
        String extension = extraerExtencion(nombreArchivo);
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(idUsuario);

        if (archivo.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar un archivo.");
            return new ModelAndView("redirect:/archivos");
        }

        if (!extension.equals("jpg") && !extension.equals("pdf")) {
            redirectAttributes.addFlashAttribute("error", "Solo se permiten archivos con extensión .jpg o .pdf");
            return new ModelAndView("redirect:/archivos");
        }

        guardarArchivoEnLaCarpetaArchivos(archivo, nombreArchivo, usuario);

        redirectAttributes.addFlashAttribute("mensaje", "Archivo subido exitosamente.");
        return new ModelAndView("redirect:/archivos");
    }

    @GetMapping("/archivos/lista")
    @ResponseBody//<----- Convierte una lista de archivos a JSON
    public List<Archivo> obtenerListaArchivos(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {return new ArrayList<>();}

        Long idUsuario = (Long) session.getAttribute("idUsuario");
        return servicioArchivo.buscarArchivosPorIdDeUsuario(idUsuario);
    }

    @RequestMapping(path = "/archivos/eliminar", method = RequestMethod.GET)
    public ModelAndView eliminarUnArchivo(@RequestParam("archivo_id") Long archivo_id, HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {return new ModelAndView("redirect:/milogin");}

        Long idUsuario = (Long) session.getAttribute("idUsuario");

        String nombreArchivo  = servicioArchivo.getNombreArchivoPorID(archivo_id);

        servicioArchivo.eliminarPorId(archivo_id);

        Path rutaArchivo = Paths.get(RUTA_ARCHIVOS + nombreArchivo);

        // Verificar si el archivo existe antes de intentar eliminarlo
        if (Files.exists(rutaArchivo)) {
            Files.delete(rutaArchivo); // Eliminar el archivo
            System.out.println("Archivo eliminado: " + rutaArchivo);
        } else {
            System.out.println("El archivo no existe: " + rutaArchivo);
        }

        return new ModelAndView("redirect:/archivos");
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
        //arch.setDireccion(RUTA_ARCHIVOS + nombreArchivo);

        servicioArchivo.registrar(arch);

    }

    private static String extraerExtencion(String nombreArchivo) {
        return Objects.requireNonNull(nombreArchivo).substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();
    }*/

    /***************** NUEVA ACTION PARA EL HISTORIAL DE ARCHIVOS *********************/

    @RequestMapping(path = "/historialArchivos")
    public ModelAndView historial(HttpServletRequest request) {
        Long idUsuario = this.obtenerIdUsuario(request);
        if (idUsuario == null){
            return new ModelAndView("redirect:/milogin");
        }

        List<Archivo> archivosEncontrados = servicioArchivo.buscarArchivosPorIdDeUsuario(idUsuario);
        ModelMap model = new ModelMap();
        model.put("archivos", archivosEncontrados);
        return new ModelAndView("archivos", model);
    }

    private Long obtenerIdUsuario(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("idUsuario");
    }
}
