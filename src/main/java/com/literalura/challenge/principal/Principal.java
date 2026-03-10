package com.literalura.challenge.principal;

import com.literalura.challenge.dto.DatosLibro;
import com.literalura.challenge.dto.DatosRespuesta;
import com.literalura.challenge.model.Autor;
import com.literalura.challenge.model.Libro;
import com.literalura.challenge.repository.AutorRepository;
import com.literalura.challenge.repository.LibroRepository;
import com.literalura.challenge.service.ConsumoAPI;
import com.literalura.challenge.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("""
                    
                    ----- LiterAlura -----
                    
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """);

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {

                case 1:
                    buscarLibro();
                    break;

                case 2:
                    listarLibros();
                    break;

                case 3:
                    listarAutores();
                    break;

                case 4:
                    listarAutoresVivos();
                    break;

                case 5:
                    listarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando aplicación...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibro() {

        System.out.println("Escribe el nombre del libro:");
        String titulo = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ", "+"));
        DatosRespuesta datos = conversor.obtenerDatos(json, DatosRespuesta.class);

        if (datos.resultados().isEmpty()) {
            System.out.println("Libro no encontrado");
            return;
        }

        DatosLibro datosLibro = datos.resultados().get(0);

        System.out.println("\n----- Libro encontrado -----");
        System.out.println("Título: " + datosLibro.titulo());
        System.out.println("Autor: " + datosLibro.autores().get(0).nombre());
        System.out.println("Idioma: " + datosLibro.idiomas().get(0));
        System.out.println("Descargas: " + datosLibro.numeroDescargas());

        List<Libro> librosExistentes = libroRepository.findByTitulo(datosLibro.titulo());

        if (!librosExistentes.isEmpty()) {
            System.out.println("\nEste libro ya está registrado en la base de datos.");
            return;
        }

        String nombreAutor = datosLibro.autores().get(0).nombre();

        List<Autor> autores = autorRepository.findByNombre(nombreAutor);

        Autor autor;

        if (!autores.isEmpty()) {
            autor = autores.get(0);
        } else {

            autor = new Autor(
                    nombreAutor,
                    datosLibro.autores().get(0).anioNacimiento(),
                    datosLibro.autores().get(0).anioFallecimiento()
            );

            autorRepository.save(autor);
        }

        Libro libro = new Libro(
                datosLibro.titulo(),
                datosLibro.idiomas().get(0),
                datosLibro.numeroDescargas(),
                autor
        );

        libroRepository.save(libro);

        System.out.println("\nLibro guardado en la base de datos.");
    }

    private void listarLibros() {

        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        libros.forEach(System.out::println);
    }

    private void listarAutores() {

        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        autores.forEach(System.out::println);
    }

    private void listarAutoresVivos() {

        System.out.println("Ingresa el año:");
        int anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = autorRepository.autoresVivosEnAnio(anio);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
            return;
        }

        autores.forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {

        System.out.println("""
                Ingresa el idioma:
                
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                """);

        String idioma = teclado.nextLine();

        List<Libro> libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros en ese idioma.");
            return;
        }

        libros.forEach(System.out::println);
    }
}