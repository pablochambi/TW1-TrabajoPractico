package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.servicios.ServicioAdministrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorAdmin {

    ServicioAdministrador servicioAdministrador;

    @Autowired
    public ControladorAdmin(ServicioAdministrador servicioAdministrador){
        this.servicioAdministrador = servicioAdministrador;
    }

    @RequestMapping(path = "/homeAdmin")
    public ModelAndView irAlHomeDelAdmin(){
        List<Usuario> usuariosEncontrados = servicioAdministrador.obtenerUsuariosClientes();
        ModelMap model = new ModelMap();
        model.put("usuarios", usuariosEncontrados);
        return new ModelAndView("homeAdmin", model);
    }

    @RequestMapping(path = "/pedidosUsuario")
    public ModelAndView irAlHistorialDePedidosPorUsuario(@RequestParam("id") Long id){
        List<Pedido> pedidosPorUsuarioEncontrados = servicioAdministrador.obtenerPedidosDelUsuario(id);
        ModelMap model = new ModelMap();
        model.put("pedidos", pedidosPorUsuarioEncontrados);
        return new ModelAndView("pedidosUsuario", model);
    }

    @RequestMapping(path = "/pedidoSeleccionado")
    public ModelAndView irAlPedidoSeleccionado(@RequestParam("id") Long id){
        Pedido pedidoEncontrado = servicioAdministrador.obtenerPedidoPorID(id);
        List<Archivo> archivosDelPedido = servicioAdministrador.obtenerArchivos(pedidoEncontrado);
        ModelMap model = new ModelMap();
        model.put("pedidos", pedidoEncontrado);
        model.put("archivos", archivosDelPedido);
        return new ModelAndView("pedidoSeleccionado", model);
    }

    /*
    @RequestMapping(path = "/calcularServicio")
    @ResponseBody
    public String calcularCostoDelServicio(@RequestParam("metros") int metros){
        int precioPorMetro = 1500;
        int precioTotal = metros * precioPorMetro;

        return String.valueOf(precioTotal);
    }*/

    @RequestMapping(value = "/simuladorDeImpresion", method = RequestMethod.POST)
    public ModelAndView simularImpresion(@RequestParam("archivoId") Long archivoId, @RequestParam("metros") int metros) {
        Archivo archivo = servicioAdministrador.buscarArchivo(archivoId);
        ModelMap model = new ModelMap();
        model.put("archivo", archivo);
        model.put("metros", metros);
        return new ModelAndView("simulador", model);
    }
}
