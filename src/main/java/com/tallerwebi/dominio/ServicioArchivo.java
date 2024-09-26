package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioArchivo {

    void registrar(Archivo archivo);

    List<Archivo> buscarArchivosPorIdDeUsuario(Long idUsuario);
}
