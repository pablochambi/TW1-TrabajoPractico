package com.tallerwebi.dominio;

import com.tallerwebi.dominio.repositorios.RepositorioPedido;
import com.tallerwebi.infraestructura.RepositorioPedidoImpl;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class ServicioPedidoArchivoTest {
    //REPOSITORIO
    RepositorioPedido repositorioPedido = mock(RepositorioPedidoImpl.class);
    //SERVICIO
    //ServicioPedido servicioPedido = new ServicioPedidoImpl(repositorioPedido);
    @Test
    public void queLanzeUnaExcepcionSiElArchivoNoEsPDFoJPG(){
        //MultipartFile -> getContentType()
        //RuntimeException
    }
    @Test
    public void queSiElArchivoPesaMasDe1GBLanzeUnaExcepcion(){
        //MultipartFile -> getSize()
        //RuntimeException
    }
    @Test
    public void queSiElArchivoEstaVacioLanzeUnaExcepcion(){

        //MultipartFile -> isEmpty()
        //RuntimeException
    }
}
