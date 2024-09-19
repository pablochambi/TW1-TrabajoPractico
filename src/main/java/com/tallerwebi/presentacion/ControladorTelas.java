package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ControladorTelas {

    @GetMapping("/telas")
    public String mostrarTelas(Model model) {
        List<Tela> telas = Arrays.asList(
                new Tela("Tela Microfibra", "Suave al tacto y fácil de mantener.", 3000, "https://via.placeholder.com/400x300?text=Tela+1"),
                new Tela("Tela Poliéster", "Ligera y resistente.", 2500, "https://via.placeholder.com/400x300?text=Tela+2"),
                new Tela("Tela Algodón", "Transpirable y cómoda.", 4000, "https://via.placeholder.com/400x300?text=Tela+3"),
                new Tela("Tela Stretch", "Flexible y elástica.", 3500, "https://via.placeholder.com/400x300?text=Tela+4")
        );

        model.addAttribute("telas", telas);

        return "telas";
    }


    public static class Tela {
        private String nombre;
        private String descripcion;
        private int precio;
        private String imagenUrl;

        public Tela(String nombre, String descripcion, int precio, String imagenUrl) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.precio = precio;
            this.imagenUrl = imagenUrl;
        }

        // Getters y setters
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public int getPrecio() {
            return precio;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
        }

        public String getImagenUrl() {
            return imagenUrl;
        }

        public void setImagenUrl(String imagenUrl) {
            this.imagenUrl = imagenUrl;
        }
    }
}


