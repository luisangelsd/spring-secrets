# Documentacion:
Esta es una API permite poder guardar, editar, eliminar y listar secretos de personas desconocida. De igual manera cuenta con Oauth2 para la administración completa de los secretos.

+ **Nota:** El proyecto ya viene configurado, solamente tienes que descargar y ejecutarlo.

## Dependencias Destacadas:
+ spring-security-oauth2
+ spring-security-jwt
+ jaxb-runtime
+ spring-boot-starter-data-jpa
+ spring-boot-starter-validation
+ h2
+ springdoc-openapi-ui
+ springfox-swagger-ui

## Probar con Swagger:
La API cuenta con la dependencia de Swageger por lo cual podras probar los endpoinds directamente desde la misma API.
+ http://localhost:8080/swagger-ui/index.html#/
+ http://localhost:8080/v3/api-docs/

## Probar con Postman:
En la raíz del proyecto encontraras un archivo .json para importar dentro de Postman. Solamente tienes que ejecutar los Endpoinds ya que ya vienen configurados.
+ Algunos Endpoinds requieren de algún Token, los cuales podrás en “Crear Token - Admin” o “Crear Token - User” desde Postman
+ Para añadir el Token a un test con Token requerido a caducado  primero debes seleccionar el Endpoind en Postman por ejemplo: “Eliminar Secreto – By Id (Admin)”, selecciona "Authorization" y pega el Token generado en el punto anteriror



## Workspace H2
+ Url: http://localhost:8080/h2-console/
+ DriveClass:org.h2.Driver
+ JDBC URL:jdbc:h2:mem:testdb
+ Username:sa
+ Username:sa
+ Password:

Nota: El proyecto tiene archivos import.sql para crear registros por defecto. Este arcivo existe en la carpeta main y test

## FrondEnd:
Esta API se puede complementar con una interfaz grafica construida con Angular, para poder acceder a ella y a su documentación da clic en el siguiente enlace:
https://github.com/sandovalguichoo/angular-secrets

