package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioArchivo;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioArchivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioArchivoImpl implements ServicioArchivo {

    private RepositorioArchivo repositorioArchivo;
    private RepositorioUsuario repositorioUsuario;

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
    public void eliminarPorId(Long archivoId) {
        if(archivoId==null || archivoId<=0 ){
            throw new IllegalArgumentException("El id del archivo no puede ser nulo o menor a cero");
        }
        Archivo archivo =  this.repositorioArchivo.buscarPorId(archivoId);
        this.repositorioArchivo.eliminar(archivo);
    }

    @Override
    public String getNombreArchivoPorID(Long archivoId) {
        if(archivoId==null || archivoId<=0 ){throw new IllegalArgumentException("El id del archivo no puede ser nulo o menor a cero");}
        return repositorioArchivo.getNombrePorID(archivoId);
    }

}
