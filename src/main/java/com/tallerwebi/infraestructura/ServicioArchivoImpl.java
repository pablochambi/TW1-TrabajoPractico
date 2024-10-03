package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepciones.ErrorAlTranferirArchivo;
import com.tallerwebi.dominio.repositorios.RepositorioArchivo;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioArchivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ServicioArchivoImpl implements ServicioArchivo {

    private RepositorioArchivo repositorioArchivo;
    private RepositorioUsuario repositorioUsuario;
    private static final String RUTA_ARCHIVOS = "src/main/webapp/resources/core/archivos/";

    @Autowired
    ServicioArchivoImpl(RepositorioArchivo repositorioArchivo, RepositorioUsuario repositorioUsuario) {
        this.repositorioArchivo = repositorioArchivo;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void registrar(Archivo archivo) {
        if (archivo!=null) {
            repositorioArchivo.guardar(archivo);
        }else{
            throw new IllegalArgumentException("El archivo no puede ser nulo");
        }
    }

    @Override
    public List<Archivo> buscarArchivosPorIdDeUsuario(Long idUsuario) {
        Usuario usuario = repositorioUsuario.buscarPorId(idUsuario);
        if(idUsuario==null || idUsuario<=0 ){
            throw new IllegalArgumentException("El id del usuario no puede ser nulo");
        }
        return this.repositorioArchivo.buscarPorIdDeUsuario(usuario);
    }
/*
    @Override
    public void eliminarPorId(Long archivoId) throws IOException {
        if(archivoId==null || archivoId<=0 ){
            throw new IllegalArgumentException("El id del archivo no puede ser nulo o menor a cero");
        }
        Archivo archivo =  this.repositorioArchivo.buscarPorId(archivoId);
        this.repositorioArchivo.eliminar(archivo);

        Path rutaArchivo = Paths.get(RUTA_ARCHIVOS + archivo.getNombre());

        // Verificar si el archivo existe antes de intentar eliminarlo
        if (Files.exists(rutaArchivo)) {
            Files.delete(rutaArchivo); // Eliminar el archivo
            System.out.println("Archivo eliminado: " + rutaArchivo);
        } else {
            System.out.println("El archivo no existe: " + rutaArchivo);
        }
    }*/


    @Override
    public void eliminarPorId(Long archivoId, Long usuarioId) throws IOException {
        if (archivoId == null || archivoId <= 0) {
            throw new IllegalArgumentException("El id del archivo no puede ser nulo o menor a cero");
        }

        // Buscar el archivo en el repositorio
        Archivo archivo = repositorioArchivo.buscarPorId(archivoId);
        if (archivo == null) {
            throw new IllegalArgumentException("No se encontró el archivo con el id proporcionado");
        }

        repositorioArchivo.eliminar(archivo);

        // Construir la ruta del archivo en la carpeta específica del usuario
        Path rutaArchivo = Paths.get(RUTA_ARCHIVOS + "Cliente_0" + usuarioId + "/" + archivo.getNombre());

        // Verificar si el archivo existe antes de intentar eliminarlo
        if (Files.exists(rutaArchivo)) {
            Files.delete(rutaArchivo); // Eliminar el archivo
            System.out.println("Archivo eliminado: " + rutaArchivo);
        } else {
            System.out.println("El archivo no existe: " + rutaArchivo);
        }
    }


    @Override
    public String getNombreArchivoPorID(Long archivoId) {
        if(archivoId==null || archivoId<=0 ){throw new IllegalArgumentException("El id del archivo no puede ser nulo o menor a cero");}
        return repositorioArchivo.getNombrePorID(archivoId);
    }

    @Override
    public void guardarEnCarpeta(MultipartFile file, Long usuario_id) {

        // Crear la carpeta si no existe
        File directorio = new File(RUTA_ARCHIVOS + "Cliente_0" + usuario_id);
        if (!directorio.exists()) {
            directorio.mkdirs(); // Crea el directorio y cualquier directorio padre necesario
        }

        // Guardar el archivo en la carpeta especificada
        File archivo = new File(directorio, file.getOriginalFilename());
        try {
            file.transferTo(archivo); // Guarda el archivo
            // Vista de éxito
        } catch (IOException e) {
            throw new ErrorAlTranferirArchivo();
        }

    }



    @Override
    public Archivo guardar(MultipartFile file, Long usuario_id) throws IOException {

        if (file.isEmpty()){return null;}//EXCEPTION

        Double tamanioEnMB = file.getSize() / 1048576.0;

        //Guardar el archivo
        Archivo archivo = new Archivo();
        //arch.setUsuario(usuario);
        archivo.setNombre(file.getOriginalFilename());
        archivo.setTipo(extraerExtencion(file.getOriginalFilename()));
        archivo.setPeso(tamanioEnMB);
        archivo.setDireccion(RUTA_ARCHIVOS + file.getOriginalFilename());

        guardarEnCarpeta(file,usuario_id);

        return repositorioArchivo.guardar(archivo, usuario_id);
    }



    @Override
    public boolean noEsExtencionValida(MultipartFile file) {
        String extencion = extraerExtencion(file.getOriginalFilename());
        return !extencion.equalsIgnoreCase("pdf") && !extencion.equalsIgnoreCase("jpg");
    }

    @Override
    public List<Archivo> buscarArchivosPorPedido(Pedido pedido) {
        return repositorioArchivo.buscarPorPedido(pedido);
    }

    private String extraerExtencion(String nombreArchivo) {
        return Objects.requireNonNull(nombreArchivo).substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();
    }



}
