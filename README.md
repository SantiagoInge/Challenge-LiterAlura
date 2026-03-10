# Literalura

Literalura es una aplicación de consola desarrollada en Java que permite consultar, registrar y gestionar información de libros y autores utilizando una API pública. Los datos obtenidos se almacenan en una base de datos para permitir consultas posteriores sin necesidad de consumir nuevamente la API.

El proyecto implementa persistencia de datos mediante JPA y Hibernate y utiliza PostgreSQL como sistema de gestión de base de datos.

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Gutendex API

## Funcionalidades

La aplicación permite realizar las siguientes operaciones desde un menú interactivo en consola:

1. Buscar libro por título en la API y guardarlo en la base de datos.
2. Listar todos los libros registrados.
3. Listar todos los autores registrados.
4. Listar autores vivos en un determinado año.
5. Listar libros por idioma.

Cada búsqueda realizada consulta la API, procesa la respuesta en formato JSON y guarda la información relevante en la base de datos.

## Estructura del proyecto

El proyecto está organizado en las siguientes capas:

- model: contiene las entidades del sistema (Libro y Autor).
- repository: interfaces que gestionan el acceso a la base de datos mediante JPA.
- service: lógica de negocio y consumo de la API externa.
- principal: manejo del menú interactivo en consola.
- ChallengeApplication: clase principal que inicia la aplicación.

## Configuración de la base de datos

Crear la base de datos en PostgreSQL:

CREATE DATABASE literalura;

Configurar el archivo application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

## Ejecución del proyecto

1. Clonar el repositorio

git clone https://github.com/tu-usuario/literalura.git

2. Abrir el proyecto en un IDE compatible con Java como IntelliJ IDEA.

3. Configurar la conexión a PostgreSQL en el archivo application.properties.

4. Ejecutar la clase principal:

ChallengeApplication.java

5. Utilizar el menú en consola para interactuar con la aplicación.

## Objetivo del proyecto

Este proyecto fue desarrollado con el objetivo de aplicar conceptos fundamentales del desarrollo backend con Java, incluyendo consumo de APIs REST, persistencia de datos con JPA, manejo de bases de datos relacionales y estructuración de aplicaciones utilizando Spring Boot.

## Autor

Proyecto desarrollado como práctica de aprendizaje en desarrollo backend con Java.
