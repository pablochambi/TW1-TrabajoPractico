package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioArchivo;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioArchivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
    }

    @Override
    public String getNombreArchivoPorID(Long archivoId) {
        if(archivoId==null || archivoId<=0 ){throw new IllegalArgumentException("El id del archivo no puede ser nulo o menor a cero");}
        return repositorioArchivo.getNombrePorID(archivoId);
    }

    @Override
    public void guardarEnCarpeta(MultipartFile file) {

        //CREAMOS EL ARCHIVO EN LA RUTA DE ARRIBA
        Path rutaArchivo = Paths.get(RUTA_ARCHIVOS + file.getOriginalFilename());
        //GUARDAMOS EL ARCHIVO EN LA RUTA ESPECIFICADA
        try {
            Files.write(rutaArchivo, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    * METODOS QUE FALTAN
    * que el archivo sea JPG o PDF
    * que se pueda eliminar el archivo de la carpeta y BD en el historial de archivos
    * que se pueda descargar el archivo el el historial de archivos
    * */

    @Override
    @Transactional
    public void guardar(MultipartFile file, Usuario usuario) throws IOException {

        //Se sube el archivo en la ruta especificada
        Path ruta = Paths.get(RUTA_ARCHIVOS + file.getOriginalFilename());
        Files.write(ruta, file.getBytes());

        // Obtener el tamaño del archivo en MB
        Double tamanioEnMb = file.getSize() / 1048576.0;

        //Guardar el archivo
        Archivo archivo = new Archivo();
        //arch.setUsuario(usuario);
        archivo.setNombre(file.getOriginalFilename());
        archivo.setTipo(extraerExtencion(file.getOriginalFilename()));
        archivo.setPeso(tamanioEnMb);
        archivo.setPedido(null);
        archivo.setDireccion(RUTA_ARCHIVOS + file.getOriginalFilename());

        //usuario.addArchivo(archivo);

        repositorioArchivo.guardar(archivo, usuario);
    }

    @Override
    public boolean noEsExtencionValida(MultipartFile file) {
        String extencion = extraerExtencion(file.getOriginalFilename());
        return !extencion.equalsIgnoreCase("pdf") && !extencion.equalsIgnoreCase("jpg");
    }

    private static String extraerExtencion(String nombreArchivo) {
        return Objects.requireNonNull(nombreArchivo).substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();
    }

}
