package com.attinae.academicoapi.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;


import com.attinae.academicoapi.domain.Persona;
import com.attinae.academicoapi.domain.seguridad.*;
import com.attinae.academicoapi.exception.ListaVaciaException;
import com.attinae.academicoapi.exception.domain.*;
import com.attinae.academicoapi.repository.UsuarioRepository;
import com.attinae.academicoapi.service.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import static com.attinae.academicoapi.constant.FileConstant.*;
import static com.attinae.academicoapi.constant.UserImplConstant.*;
import static java.nio.file.StandardCopyOption.*;


@Service
@Transactional
@Qualifier("UserDetailsService")
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {



    private final UsuarioRepository usuarioRepository;
    private final RolUsuarioService rolUsuarioService;
    private final RolAuthoritiesService rolAuthoritiesService;
    private final AuthorityService authorityService;
    private final LoginAttemptService loginAttemptService;
    private final EmailService emailService;
    private final PersonaService personaService;


    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolUsuarioService rolUsuarioService, RolAuthoritiesService rolAuthoritiesService, AuthorityService authorityService, LoginAttemptService loginAttemptService, EmailService emailService, PersonaService personaService, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolUsuarioService = rolUsuarioService;
        this.rolAuthoritiesService = rolAuthoritiesService;
        this.authorityService = authorityService;
        this.loginAttemptService = loginAttemptService;
        this.emailService = emailService;
        this.personaService = personaService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * admin3
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findUsuarioByLogin(username);
        if(!usuarioOptional.isPresent()) {
            LOGGER.error(REGISTRO_NO_ENCONTRADO_PARA_EL_USUARIO + username);
             throw new UsernameNotFoundException(REGISTRO_NO_ENCONTRADO_PARA_EL_USUARIO + username);
         } else {
            Usuario usuario = usuarioOptional.get();
            validateLoginAttempt(usuario);
            usuario.setLastLoginDateDisplay(usuario.getFechaUltimoIngreso());
            usuario.setFechaUltimoIngreso(new Date());
            usuarioRepository.save(usuario);
            UserPrincipal userPrincipal = new UserPrincipal(usuario, this.findAuthorities(usuario.getUsuarioId()));

            LOGGER.info(REGISTRO_ENCONTRADO_POR_NOMBRE_DE_USUARIO +username);
            return userPrincipal;
         }

    }



    private void validateLoginAttempt(Usuario user)  {
        if(user.isNotLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getLogin())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getLogin());
        }
    }

    private String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }


    @Override
    public Usuario register(Usuario usuario) throws UsuarioNotFoundException, UsernameExistsException, MessagingException {


       String encodedPassword= this.encodedPassword(usuario.getPassword());
       String password= usuario.getPassword();
       this.validateNewUsername(StringUtils.EMPTY, usuario.getLogin());
       usuario.setPassword(encodedPassword);
       usuario.setCreado(new Date());
       usuario.setProfileImageUrl(getTemporaryProfileImageUrl(usuario.getLogin()));

        Usuario usuarioSaved = this.usuarioRepository.save(usuario);
        Rolusuario rolusuarioNew = crearRegistroRolUsuario(usuarioSaved.getUsuarioId());
        Rolusuario rolusuarioSaved = this.rolUsuarioService.guardar(rolusuarioNew);
        LOGGER.info("El password del usuario es: "+ password+ " Su nuevo rol es :" + rolusuarioSaved.getRolusuarioId());
        Optional<Persona> personaOptional= personaService.findPersonaByPersonaId(usuario.getPersonaId());
        this.emailService.sendNewPasswordEmail(personaOptional.get().getNombre1(),password,personaOptional.get().getEmailpersonal());
       return usuarioSaved;

    }

    @Override
    public Usuario addNewUser(Usuario usuario, MultipartFile profileImage) throws UsuarioNotFoundException, UsernameExistsException, IOException {
        this.validateNewUsername(StringUtils.EMPTY, usuario.getLogin());
        String password= usuario.getPassword();
        String encodedPassword= this.encodedPassword(password);
        usuario.setPassword(encodedPassword);
        usuario.setCreado(new Date());

        usuario.setProfileImageUrl(getTemporaryProfileImageUrl(usuario.getLogin()));

        Usuario usuarioSaved = this.usuarioRepository.save(usuario);

        Rolusuario rolusuarioNew = crearRegistroRolUsuario(usuarioSaved.getUsuarioId());
        Rolusuario rolusuarioSaved = this.rolUsuarioService.guardar(rolusuarioNew);
        LOGGER.info("El password del usuario es: "+ password+ " Su nuevo rol es :" + rolusuarioSaved.getRolusuarioId());


        saveProfileImage(usuarioSaved,profileImage);

        return usuarioSaved;

    }

    @Override
    public Usuario updateUser(Integer usuarioId, Usuario usuario, MultipartFile profileImage) throws UsuarioNotFoundException, IOException, UsernameExistsException {
        Optional<Usuario> usuarioRepositoryById = this.usuarioRepository.findById(usuarioId);
        Optional<Usuario> usuarioByLogin = this.usuarioRepository.findUsuarioByLogin(usuario.getLogin());

        if(!usuarioRepositoryById.isPresent()) {
            throw  new UsuarioNotFoundException("El usuario con el id: " + usuarioId+ " no existe en el sistema");
        }

        if(usuarioByLogin.isPresent() &&  !usuarioByLogin.get().getUsuarioId().equals(usuarioRepositoryById.get().getUsuarioId()) )
        {
            throw new UsernameExistsException("El Username: " + usuario.getLogin()+ " ya esta tomado por otra persona");
        }

        String password= usuario.getPassword();
        String encodedPassword= this.encodedPassword(password);
       // usuario.setCreado(usuarioByLogin.get().getCreado());
       // usuario.setCreadopor(usuarioByLogin.get().getCreadopor());
        usuario.setPassword(encodedPassword);
        usuario.setActualizado(new Date());
        usuario.setProfileImageUrl(getTemporaryProfileImageUrl(usuario.getLogin()));

        Usuario usuarioTarget = usuarioRepositoryById.get();

        BeanUtils.copyProperties(usuario,usuarioTarget,"usuarioId","creadopor","creado");
        Usuario usuarioSaved = this.usuarioRepository.save(usuarioTarget);


       // TODO: Para actualizar un rol de un usuario ya existente se debe de crear en su correspondiente servicio ese metodo
        saveProfileImage(usuarioSaved,profileImage);

        return usuarioSaved;


    }

    @Override
    public void deleteUser(Integer id) {
        this.usuarioRepository.deleteById(id);
    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException, UsuarioNotFoundException, MessagingException {
        Optional<Persona> personaByEmailpersonal = this.personaService.findPersonaByEmailpersonal(email);
        if(personaByEmailpersonal.isPresent()){

            Optional<Usuario> byPersonaId = this.usuarioRepository.findByPersonaId(personaByEmailpersonal.get().getPersonaId());
            if(byPersonaId.isPresent()){
                Usuario usuario = byPersonaId.get();
                String password = generatePassword();
                usuario.setPassword(encodedPassword(generatePassword()));
                this.usuarioRepository.save(usuario);
                emailService.sendNewPasswordEmail(personaByEmailpersonal.get().getNombre1(), password, personaByEmailpersonal.get().getEmailpersonal());

            }
            else {
                throw new UsuarioNotFoundException("No se encontro ningun usuario con la siguiente documentacion: " +personaByEmailpersonal.get().getDocumentoidentificacion() );
            }
        }
        else{
            throw new EmailNotFoundException("No existe una persona con este email: "+ email);
        }
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    @Override
    public Usuario updateProfileImage(String username, MultipartFile profileImage) throws IOException, UsuarioNotFoundException {
        Usuario user = validateUserName(username);
        saveProfileImage(user, profileImage);
        return user;
    }

    @Override
    public List<Usuario> getUsers() throws ListaVaciaException {
        List<?> usuariosList= new ArrayList<>();
        usuariosList=this.usuarioRepository.findAll();

       if (usuariosList.size()>0) {
           return (List<Usuario>) usuariosList;
       }
       else {
           throw  new ListaVaciaException("No hay registros");
       }
    }

    private Usuario validateUserName(String username) throws UsuarioNotFoundException {
        Optional<Usuario> usuarioByLogin = this.usuarioRepository.findUsuarioByLogin(username);
        if(!usuarioByLogin.isPresent()){
            throw new UsuarioNotFoundException("El username: "+ username +" no se encuentra");
         } else{
           return usuarioByLogin.get();
        }
    }

    private void saveProfileImage(Usuario user, MultipartFile profileImage) throws IOException {
        if (profileImage != null) {
            Path userFolder = Paths.get(USER_FOLDER + user.getLogin()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }

            Files.deleteIfExists(Paths.get(userFolder + user.getLogin() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getLogin() + DOT + JPG_EXTENSION ), REPLACE_EXISTING);
            user.setProfileImageUrl(setProfileImageUrl(user.getLogin()));
            this.usuarioRepository.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename()) ;
        }

    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path( USER_IMAGE_PATH + username + FORWARD_SLASH+ username + DOT + JPG_EXTENSION)
                .toUriString();
    }

    private Rolusuario crearRegistroRolUsuario(Integer usuarioId) {
      return  Rolusuario.builder()
                .activo('S')
                .rolId(1)
                .usuarioId(usuarioId)
                .estadoId(1)
                .creadopor(1)
                .creado(new Date())
                .build();

    }

    private String getTemporaryProfileImageUrl(String username) {
          return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    @Override
    public Optional<Usuario> findUsuarioByLogin(String login) throws UsuarioNotFoundException {

        Optional<Usuario> usuarioByLogin = this.usuarioRepository.findUsuarioByLogin(login);
        if(!usuarioByLogin.isPresent()){
            throw new UsuarioNotFoundException("El usuario ingresado no se encuentra en el sistema");
        }

        return this.usuarioRepository.findUsuarioByLogin(login);
    }

    @Override
    public String[] findAuthorities(Integer usuarioId) {
        List<Rolusuario> allByUsuarioId = this.rolUsuarioService.findAllByUsuarioId(usuarioId);

        List<Integer> idRolByUsuarioList = allByUsuarioId.stream().map(Rolusuario::getRolId).collect(Collectors.toList());

        List<Integer> fkAuthoritiesId= new ArrayList<>();

        idRolByUsuarioList.forEach(idRol -> fkAuthoritiesId.addAll( this.rolAuthoritiesService.findAllByFkRolId(idRol).stream().map(RolAuthorities::getFkAuthority).collect(Collectors.toList()) ));

        String[] salida = fkAuthoritiesId.stream().map(ra -> this.authorityService.findById(ra).isPresent() ? this.authorityService.findById(ra).get().getAuthorities() : null).collect(Collectors.toList()).toArray(String[]::new);


        return salida;
    }




    private Usuario validateNewUsername(String currentUsername,String newUsername) throws  UsernameExistsException, UsuarioNotFoundException {

        Optional<Usuario> userByNewUsername = this.usuarioRepository.findUsuarioByLogin(newUsername);

        if(StringUtils.isNotBlank(currentUsername)) {

            Optional<Usuario> currentUser = this.usuarioRepository.findUsuarioByLogin(currentUsername);

            if(!currentUser.isPresent()) {
                throw new UsuarioNotFoundException(NINGUN_USUARIO_SE_ENCONTRO_CON_EL_USERNAME + currentUsername);
            }

            if(userByNewUsername.isPresent()  && !currentUser.get().getUsuarioId().equals(userByNewUsername.get().getUsuarioId())) {
                throw new UsernameExistsException(USERNAME_ALREADY_EXISTS);
            }

            return currentUser.get();
        } else {

            if(userByNewUsername.isPresent()) {
                throw new UsernameExistsException(USERNAME_ALREADY_EXISTS);
            }
            return null;
        }


    }
}