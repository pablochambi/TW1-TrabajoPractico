package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
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
import java.time.LocalDate;

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

     /*@Override
    public Pedido guardar(Archivo archivo) {
       Pedido pedido = new Pedido();
        pedido.getArchivos().add(archivo);
        pedido.setEstado(Estado.EN_ESPERA);
        pedido.setConCalandrado(true);
        repositorioPedido.guardar(pedido);
        archivo.setPedido(pedido);
        repositorioArchivo.guardar(archivo);
      return null;
    }*/

    @Override
    public Archivo validarArchivo(MultipartFile file, Long idUsuario) {
        if (file.isEmpty()){
            return null;//EXCEPTION
        }
        Archivo archivo = new Archivo();
        archivo.setNombre(file.getOriginalFilename());
        archivo.setTipo(file.getContentType());
        archivo.setPeso((double) file.getSize());

        /*
        * FALTA ALMACENAR EL CONTENIDO DEL ARCHIVO Y LA DIRECCION DONDE ESTARA GUARDADA
        * */

        //OBTENEMOS EL USUARIO DE LA SESSION
        Usuario usuarioBuscado = this.obtenerUsuarioPorId(idUsuario);
        archivo.setUsuario(usuarioBuscado);
        return archivo;
    }

    @Override
    public Pedido realizarPedido(String nombre, String tipoPedido, Archivo archivoA, Archivo archivoB, Archivo archivoC, Long idUsuario) {
        Pedido pedido = new Pedido();
        if (tipoPedido.equals("calandrado")){
            pedido.setConCalandrado(true);
        }
        String fechaHoy = this.obtenerFechaHoy();
        pedido.setFechaCreacion(fechaHoy);
        pedido.setNombre(nombre);
        /*
        * TIEMPO DE ENTREGA SE BASA EN LA MAQUINA
        * */

        //AGREGAMOS ARCHIVOS A LA LISTA
        pedido.getArchivos().add(archivoA);
        pedido.getArchivos().add(archivoB);
        pedido.getArchivos().add(archivoC);

        //OBTENEMOS EL USUARIO DE LA SESSION
        Usuario usuarioBuscado = this.obtenerUsuarioPorId(idUsuario);
        pedido.setUsuario(usuarioBuscado);

        //GUARDAMOS LOS ARCHIVOS YA VALIDADOS
        this.guardarArchivosEnLaBaseDeDatos(archivoA, pedido);
        this.guardarArchivosEnLaBaseDeDatos(archivoB, pedido);
        this.guardarArchivosEnLaBaseDeDatos(archivoC, pedido);

        //GUARDAMOS EL PEDIDO
        repositorioPedido.guardar(pedido);

        return pedido;
    }

    private void guardarArchivosEnLaBaseDeDatos(Archivo archivo, Pedido pedido) {
        archivo.setPedido(pedido);
        repositorioArchivo.guardar(archivo);
    }

    private Usuario obtenerUsuarioPorId(Long idUsuario) {
        return repositorioUsuario.buscarPorId(idUsuario);
    }

    private String obtenerFechaHoy() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }
}

