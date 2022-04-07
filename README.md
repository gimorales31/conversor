# conversor
conversor de moneda

# Pasos después de la ejecución

1.- Auth - Autenticacion - (user/password- admin/admin)
ejemplo POST: localhost:9090/auth
json de entrada:{"username":"admin","password":"admin"}
json de salida:
{ "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTY0OTI4ODkxNDMxMywiZXhwIjoxNjQ5ODkzNzE0fQ.0m-687crpbwje-JC8b6XsRqJ9MB94qu2ThFrKn5PTh0LRQVeKipydwDlUOiLkfgZSlJh0x0Fvl5-ckr7nTc9hg" }

2.- Registro TipoCambio
ejemplo POST: localhost:9090/conversor/tipoCambio
En el header: Authorization=token
json de entrada:  {"monedaOrigen":"USD","monedaDestino":"PEN","tipoCambio": 3.6327 } 

3.- Consulta TipoCambio
ejemplo GET: localhost:9090/conversor/tipoCambio
En el header: Authorization=token

4.- Actualizar TipoCambio
ejemplo PUT: localhost:9090/conversor/tipoCambio
En el header: Authorization=token
json de entrada:
  {    "id": 1,    "monedaOrigen": "USD",    "monedaDestino": "PEN",    "tipoCambio": 3.6329 }
  
5.- Conversor Moneda
ejemplo POST: localhost:9090/conversor
En el header: Authorization=token
json de entrada: {"monto": 20.00,"monedaOrigen":"USD","monedaDestino":"PEN"}

# Mysql
1.- docker pull mysql
2.- docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest

# Dockerizar la app
0.- ejecutar una consola
1.- ubicarse en la ruta de proyecto.
2.- unicar el DockerFile en la raiz del proyecto
3.- ejecutar en la consola el comando: docker build -t "conversor-moneda" .
4.- luego ejecutar el comando: docker run --name conversor-springboot-docker -p 9090:9090 conversor-moneda:latest
5.- ejecutar el comando para visualizar la imagen docker: docker images
6.- ya se puede ejecutar los servicios en el postman.



