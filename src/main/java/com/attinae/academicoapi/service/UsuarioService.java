package com.attinae.academicoapi.service;

import com.attinae.academicoapi.domain.Persona;
import com.attinae.academicoapi.domain.seguridad.Usuario;
import com.attinae.academicoapi.exception.ListaVaciaException;
import com.attinae.academicoapi.exception.domain.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario register(Usuario usuario) throws PersonaNotFoundException, PersonaExistsException, EmailExistException, UsuarioNotFoundException, UsernameExistsException, MessagingException;

    Optional<Usuario> findUsuarioByLogin(String login) throws UsuarioNotFoundException;

    String[] findAuthorities(Integer usuarioId);

    Usuario addNewUser(Usuario usuario, MultipartFile profileImage) throws UsuarioNotFoundException, UsernameExistsException, IOException;

    Usuario updateUser(Integer usuarioId, Usuario usuario, MultipartFile profileImage) throws UsuarioNotFoundException, IOException, UsernameExistsException;


    void deleteUser(Integer id);

    void resetPassword(String email) throws PersonaNotFoundException, EmailNotFoundException, UsuarioNotFoundException, MessagingException;


    Usuario updateProfileImage(String username, MultipartFile profileImage) throws IOException, UsuarioNotFoundException;


    List<Usuario> getUsers() throws ListaVaciaException;
}