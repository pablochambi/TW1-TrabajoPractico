package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.servicios.ServicioArchivo;
import com.tallerwebi.dominio.servicios.ServicioPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class ControladorPedido {

    private ServicioPedido servicioPedido;
    private ServicioArchivo servicioArchivo;

    @Autowired
    public ControladorPedido(ServicioPedido servicioPedido,ServicioArchivo servicioArchivo) {
        this.servicioPedido = servicioPedido;
        this.servicioArchivo = servicioArchivo;
    }

    @RequestMapping(path = "/formPedido")
    public ModelAndView realizarPedido(HttpServletRequest request){
        Long idUsuario = obtenerIdUsuario(request);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/milogin");
        }

        return new ModelAndView("formularioPedido");
    }

    /*@RequestMapping(path = "/pedidoRealizado",method = RequestMethod.POST)
    public ModelAndView realizarPedido(@RequestParam("file") MultipartFile file,HttpServletRequest request){
       Long idUsuario = obtenerIdUsuario(request);

        Archivo archivo = servicioPedido.validarArchivo(file);
        Pedido pedido = servicioPedido.guardar(archivo);
        ModelMap model = new ModelMap();
        model.put("pedido", pedido);
        model.put("user", idUsuario);

        return new ModelAndView("mostrarPedido", model);
    }*/

    @RequestMapping(path = "/pedidoRealizado", method = RequestMethod.POST)
    public ModelAndView realizarPedido(@RequestParam("nombre") String nombre,
                                       @RequestParam("tipoPedido") String tipoPedido,
                                       @RequestParam("file1") MultipartFile file1,
                                       @RequestParam("file2") MultipartFile file2,
                                       @RequestParam("file3") MultipartFile file3,
                                       HttpServletRequest request) throws IOException {

        Long idUsuario = obtenerIdUsuario(request);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/milogin");
        }

        Archivo archivoA = servicioArchivo.guardar(file1, idUsuario);
        Archivo archivoB = servicioArchivo.guardar(file2, idUsuario);
        Archivo archivoC = servicioArchivo.guardar(file3, idUsuario);

        Pedido pedido = servicioPedido.realizarPedido(nombre, tipoPedido, archivoA, archivoB, archivoC, idUsuario);
        List<Archivo> archivosDelPedido = servicioArchivo.buscarArchivosPorPedido(pedido);
        ModelMap model = new ModelMap();
        model.put("pedido",pedido);
        model.put("archivos", archivosDelPedido);
        return new ModelAndView("mostrarPedido", model);
    }

    private Long obtenerIdUsuario(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("idUsuario");
    }
}
