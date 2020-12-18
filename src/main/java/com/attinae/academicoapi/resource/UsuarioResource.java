package com.attinae.academicoapi.resource;


import com.attinae.academicoapi.domain.HttpResponse;
import com.attinae.academicoapi.domain.filter.Login;
import com.attinae.academicoapi.domain.seguridad.UserPrincipal;
import com.attinae.academicoapi.domain.seguridad.Usuario;
import com.attinae.academicoapi.exception.ExceptionHandling;
import com.attinae.academicoapi.exception.ListaVaciaException;
import com.attinae.academicoapi.exception.domain.*;
import com.attinae.academicoapi.service.UsuarioService;
import com.attinae.academicoapi.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.attinae.academicoapi.constant.FileConstant.*;
import static com.attinae.academicoapi.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping(path = {"/","/usuario"})
public class UsuarioResource extends ExceptionHandling {

    private static final String USER_DELETED_SUCCESSFULLY = "El usuario se ha borrado con exito!";
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    public static final String EMAIL_SENT = "Se ha enviado un email con su nueva contraseña: ";

    @Autowired
    public UsuarioResource(UsuarioService usuarioService, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody @Valid Login login) throws UsuarioNotFoundException {
        authenticate(login.getUsuario(),login.getPassword());

        Usuario loginuser = this.usuarioService.findUsuarioByLogin(login.getUsuario()).get();

        String[] authorities= this.usuarioService.findAuthorities(loginuser.getUsuarioId());

        UserPrincipal userPrincipal = new UserPrincipal(loginuser,authorities);

        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);

        return new ResponseEntity<Usuario>(loginuser,jwtHeader, OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Usuario> addNewUser(@RequestParam("login") String login,
                                              @RequestParam("personaId") Integer personaId,
                                              @RequestParam("estadoId") Integer estadoId,
                                              @RequestParam("creadopor") Integer creadopor,
                                              @RequestParam("activo") Character activo,
                                              @RequestParam("password") String password,
                                              @RequestParam("pregunta1") String pregunta1,
                                              @RequestParam("respuesta1") String respuesta1,
                                              @RequestParam("pregunta2") String pregunta2,
                                              @RequestParam("respuesta2") String respuesta2,
                                              @RequestParam("pregunta3") String pregunta3,
                                              @RequestParam("respuesta3") String respuesta3,
                                              @RequestParam("active") Boolean active,
                                              @RequestParam("notLocked") Boolean notLocked,
                                              @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
                                                )
            throws UsernameExistsException, IOException, UsuarioNotFoundException {

        Usuario usuario = Usuario.builder().
                login(login)
                .personaId(personaId)
                .estadoId(estadoId)
                .creadopor(creadopor)
                .activo(activo)
                .password(password)
                .pregunta1(pregunta1)
                .respuesta1(respuesta1)
                .pregunta2(pregunta2)
                .respuesta2(respuesta2)
                .pregunta3(pregunta3)
                .respuesta3(respuesta3)
                .isActive(active)
                .isNotLocked(notLocked)
                .build();

        Usuario addNewUser = this.usuarioService.addNewUser(usuario, profileImage);

        return new ResponseEntity<Usuario>(addNewUser,OK);

    }


    @PutMapping("/update/{usuarioId}")
    public ResponseEntity<Usuario> update(    @PathVariable Integer usuarioId,
                                              @RequestParam("login") String login,
                                              @RequestParam("personaId") Integer personaId,
                                              @RequestParam("estadoId") Integer estadoId,
                                              @RequestParam("creadopor") Integer creadopor,
                                              @RequestParam("activo") Character activo,
                                              @RequestParam("password") String password,
                                              @RequestParam("pregunta1") String pregunta1,
                                              @RequestParam("respuesta1") String respuesta1,
                                              @RequestParam("pregunta2") String pregunta2,
                                              @RequestParam("respuesta2") String respuesta2,
                                              @RequestParam("pregunta3") String pregunta3,
                                              @RequestParam("respuesta3") String respuesta3,
                                              @RequestParam("active") String active,
                                              @RequestParam("notLocked") String notLocked,
                                              @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    )
            throws UsernameExistsException, IOException, UsuarioNotFoundException {

        Usuario usuario = Usuario.builder().
                login(login)
                .personaId(personaId)
                .estadoId(estadoId)

                .creado(new Date())
                .activo(activo)
                .password(password)
                .pregunta1(pregunta1)
                .respuesta1(respuesta1)
                .pregunta2(pregunta2)
                .respuesta2(respuesta2)
                .pregunta3(pregunta3)
                .respuesta3(respuesta3)
                .isActive(Boolean.parseBoolean(active))
                .isNotLocked(Boolean.parseBoolean(notLocked))
                .build();

        Usuario updatedUser = this.usuarioService.updateUser(usuarioId,usuario, profileImage);

        return new ResponseEntity<Usuario>(updatedUser,OK);

    }

    @GetMapping("/find/{username}")
    public ResponseEntity<Usuario> getUser(@PathVariable String username) throws UsuarioNotFoundException {
        Optional<Usuario> usuarioByLogin = this.usuarioService.findUsuarioByLogin(username);

        return new ResponseEntity<Usuario>(usuarioByLogin.get(),OK);
    }


    @GetMapping("/list")
    public ResponseEntity<List<Usuario>> getAllUsers()  throws ListaVaciaException {

        List<Usuario> users = this.usuarioService.getUsers();

        return new ResponseEntity<List<Usuario>>(users,OK);
    }


    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws MessagingException, EmailNotFoundException, UsuarioNotFoundException, PersonaNotFoundException {

        this.usuarioService.resetPassword(email);

        return  this.response(OK, EMAIL_SENT + email);
    }

    @DeleteMapping("/delete/{usuarioId}")
    @PreAuthorize("hasAnyAuthority('usuario:delete')")
    public ResponseEntity<HttpResponse> deleteUsuario(@PathVariable Integer usuarioId){
        this.usuarioService.deleteUser(usuarioId);
        return this.response(NO_CONTENT, USER_DELETED_SUCCESSFULLY);

    }

    @PutMapping("/updateProfileImage")
    public ResponseEntity<Usuario> updateProfileImage(@RequestParam("username") String username,
                                                      @RequestParam(value = "profileImage") MultipartFile profileImage)
            throws IOException, UsuarioNotFoundException {
        Usuario usuario = this.usuarioService.updateProfileImage(username, profileImage);

        return new ResponseEntity<Usuario>(usuario, OK);

    }

    @GetMapping(path = "/image/{username}/{fileName}", produces = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE})
    public byte[] getProfileImage(@PathVariable("username") String username,
                                  @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER+ username + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/image/profile/{username}", produces = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE})
    public byte[] getTempProfileImage(@PathVariable("username") String username
                                 ) throws IOException {

        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while ((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }

        }

        return byteArrayOutputStream.toByteArray();
    }


    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody @Valid Usuario usuario) throws PersonaNotFoundException, EmailExistException, PersonaExistsException, UsuarioNotFoundException, UsernameExistsException, MessagingException {
         Usuario newUser=  this.usuarioService.register(usuario);
         return new ResponseEntity<Usuario>(newUser, OK);
    }



    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    private void authenticate(String login, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {

        return new ResponseEntity<>(new HttpResponse (httpStatus.value(),
                httpStatus,httpStatus.getReasonPhrase().toUpperCase(),message.toUpperCase(), new Date())
                ,httpStatus);
    }
}
