package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration( classes = {SpringWebTestConfig.class, HibernateTestConfig.class})

@Repository
public class RepositorioUsuarioTest {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    RepositorioUsuario repositorioUsuario;

    @Test
    @Transactional
    @Rollback
    public void queCuandoSeGuardeUnUsuario_SeGuardeConElRolDeUSER() {

        Usuario usuario = new Usuario();
        usuario.setEmail("leo@gmail.com");
        usuario.setPassword("1234");
        usuario.setNombre("Leo");
        usuario.setApellido("Messi");
        usuario.setUsername("leomesi");

        repositorioUsuario.guardar(usuario);

        assertThat(usuario.getId(),notNullValue());
        assertThat(usuario.getRol(),equalTo("USER"));
        assertThat(usuario.getActivo(),equalTo(true));
    }


}
