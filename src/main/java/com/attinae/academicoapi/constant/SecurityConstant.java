package com.attinae.academicoapi.constant;

public class SecurityConstant {
    //public static final long EXPIRATION_TIME = 180_000; //1 hora expresada en milisegundos (180000= 3 min)
    public static final long EXPIRATION_TIME = 360_000_0; //1 hora expresada en milisegundos (180000= 3 min)
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "El token no puede ser verificado";
    public static final String ATTINAE_LLC= "Attiane, LC";
    public static final String ATTINAE_ADMINISTRATION= "Administracion Attinae";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "Debes iniciar sesión para acceder a esta página";
    public static final String ACCESS_DENIED_MESSAGE = "Usted no tiene permiso para acceder a esta página";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = { "/usuario/login", "/usuario/register","/swagger-ui/**","/v3/api-docs","/usuario/image/**" };
    //public static final String[] PUBLIC_URLS = { "**" };
}
