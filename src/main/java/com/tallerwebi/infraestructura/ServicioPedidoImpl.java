package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Estado;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioArchivo;
import com.tallerwebi.dominio.repositorios.RepositorioPedido;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioPedidoImpl implements ServicioPedido {

    RepositorioPedido repositorioPedido;
    RepositorioArchivo repositorioArchivo;
    RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido repositorioPedido, RepositorioArchivo repositorioArchivo, RepositorioUsuario repositorioUsuario){
        this.repositorioPedido = repositorioPedido;
        this.repositorioArchivo = repositorioArchivo;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void guardar(Pedido pedidoNuevo) {
        repositorioPedido.guardar(pedidoNuevo);
    }

    @Override
    public Pedido guardar(Archivo archivo) {
        Pedido pedido = new Pedido();
        pedido.getArchivos().add(archivo);
        pedido.setEstado(Estado.EN_ESPERA);
        pedido.setConCalandrado(true);
        repositorioPedido.guardar(pedido);
        archivo.setPedido(pedido);
        repositorioArchivo.guardar(archivo);
      return pedido;
    }

    @Override
    public Archivo validarArchivo(MultipartFile file, Long idUsuario) {
        if (file.isEmpty()){
            return null;//EXCEPTION
        }
        Archivo archivo = new Archivo();
        archivo.setNombre(file.getOriginalFilename());
        archivo.setPeso((double) file.getSize());
        Usuario usuarioBuscado = repositorioUsuario.buscarPorId(idUsuario);
        archivo.setUsuario(usuarioBuscado);
        return archivo;
    }

    @Override
    public Pedido realizarPedido(String nombre, String tipoPedido, Archivo archivoA, Archivo archivoB, Archivo archivoC, Long idUsuario) {
        Pedido pedido = new Pedido();
        pedido.getArchivos().add(archivoA);
        pedido.getArchivos().add(archivoB);
        pedido.getArchivos().add(archivoC);
        if (tipoPedido.equals("calandrado")){
            pedido.setConCalandrado(true);
        }
        Usuario usuarioBuscado = repositorioUsuario.buscarPorId(idUsuario);
        pedido.setUsuario(usuarioBuscado);
        repositorioPedido.guardar(pedido);
        return pedido;
    }
}

