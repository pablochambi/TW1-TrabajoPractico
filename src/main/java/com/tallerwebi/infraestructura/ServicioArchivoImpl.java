package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Archivo;
import com.tallerwebi.dominio.RepositorioArchivo;
import com.tallerwebi.dominio.ServicioArchivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioArchivoImpl implements ServicioArchivo {

    private RepositorioArchivo repositorioArchivo;

    @Autowired
    ServicioArchivoImpl(RepositorioArchivo repositorioArchivo) {
        this.repositorioArchivo = repositorioArchivo;
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
        if(idUsuario==null || idUsuario<=0 ){
            throw new IllegalArgumentException("El id del usuario no puede ser nulo");
        }
        return this.repositorioArchivo.buscarPorIdDeUsuario(idUsuario);
    }

}
