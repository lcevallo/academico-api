# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.attinae.academico-api' is invalid and this project uses 'com.attinae.academicoapi' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

https://www.stackextend.com/java/generate-pdf-document-using-jasperreports-and-spring-boot/
https://www.baeldung.com/java-microsoft-excel

Para Aplicar al proyecto
https://www.youtube.com/watch?v=ela-oNuFlzM

cd /var/lib/pgsql/11/data
pg_hba.conf
host    all		all		181.199.39.1/24  	trust
systemctl reload postgresql-11.service

server:
  servlet:
    context-path: /api/cem

url: jdbc:postgresql://academico.cem.edu.ec:5432/attinae_db

Pasos para respaldar desde una base ya existente y con data
PRIMERO CREAR EL USUARIO QUE USARA LA BASE DE DATOS
Ej: CREATE USER fusion SUPERUSER PASSWORD 'foobar'

BUSCAR LA PRIMER PALABRA COPY
COPIAR TODO desde el inicio hasta el primer COPY
Luego Buscar el primer 'ADD CONSTRAINT' del archivo dump
Y copiar todo lo que hay desde ese primer add constraint hasta el final
OJO no copiar los pg_catalog.setval
FINAL pg_catalog.setval
RECORDAR QUE 
https://stackoverflow.com/questions/49208448/error-in-creating-sequences-when-restoring-the-postgresql-database
AS integer
    START WITH 1
con las sequencias cambian su forma de creacion en las versiones de las bases de datos
 
SELECT pg_catalog.setval('academico.pk_pai
COPY seguridad.esta
SELECT pg_catalog.setval('seguridad.pk_esta

SELECT pg_catalog.setval('academico.pk_provincia_id_seq', (Select max(provincia_id) from academico.provincia)+1, true);
Select max(provincia_id) from academico.provincia



Pasos para colocar la base desde cero 
Primero hacer el batch de restaurarbase_remoto.bat
1.- Elegir attinae_sin_data
2.- Luego volver a ejecutar el mismo bat pero elegir estado -> Luego setearle el setVal
3.- Volver a ejecutar el mismo bat pero elegir pais -> Luego setearle el setVal
3.- Volver a ejecutar el mismo bat pero elegir provincia -> Luego setearle el setVal
4.- Volver a ejecutar el mismo bat pero elegir ciudad -> Luego setearle el setVal
SELECT pg_catalog.setval('academico.pk_ciudad_id_seq', (Select max(ciudad_id) from academico.ciudad)+1, true);
5.- Volver a ejecutar el mismo bat pero elegir etnia -> Luego setearle el setVal
     SELECT pg_catalog.setval('academico.pk_etnia_id_seq', (Select max(etnia_id) from academico.etnia)+1, true);
6.- Volver a ejecutar el mismo bat pero elegir idioma -> Luego setearle el setVal        
SELECT pg_catalog.setval('academico.pk_idioma_id_seq', (Select max(idioma_id) from academico.idioma)+1, true);
7.- Volver a ejecutar el mismo bat pero elegir estadocivil -> Luego setearle el setVal    
SELECT pg_catalog.setval('academico.pk_estadocivil_id_seq', (Select max(estadocivil_id) from academico.estadocivil)+1, true);
8.- Volver a ejecutar el mismo bat pero elegir identidadgenero -> Luego setearle el setVal    
SELECT pg_catalog.setval('academico.pk_identidadgenero_id_seq', (Select max(identidadgenero_id) from academico.identidadgenero)+1, true);
9.- Volver a ejecutar el mismo bat pero elegir parroquia -> Luego setearle el setVal
SELECT pg_catalog.setval('academico.pk_parroquia_id_seq', (Select max(parroquia_id) from academico.parroquia)+1, true);
10.- Volver a ejecutar el mismo bat pero elegir tipodocumento -> Luego setearle el setVal
SELECT pg_catalog.setval('academico.pk_tipodocumento_id_seq', (Select max(tipodocumento_id) from academico.tipodocumento)+1, true);
11.- Volver a ejecutar el mismo bat pero elegir sexo -> Luego setearle el setVal 'Este ya tiene adentro el setval'
12.- Volver a ejecutar el mismo bat pero elegir operadora -> Luego setearle el setVal 'Este ya tiene adentro el setval'
13.- Volver a ejecutar el mismo bat pero elegir tipodiscapacidad -> Luego setearle el setVal 'Este ya tiene adentro el setval'



http://localhost:8081/swagger-ui/index.html
http://localhost:8081/v3/api-docs