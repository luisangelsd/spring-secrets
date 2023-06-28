# Documentacion:
El objetivo de este proyecto es implementar una Api Restful con los métodos HTTP más utilizados: Get, Put, Post, Delete para despues implementar pruebas unitarias a cada una de las capaz (Repositories, Services, Controllers + Integración con TestRestTemplate y WebClientTest).

+ **Nota:** El proyecto consiste en el funcionamiento de un banco, en donde puedes administrar cuentas y realizar transferencias entre ellas.
+ **Nota:** El proyecto ya viene configurado, solamente tienes que descargar y ejecutarlo.


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

