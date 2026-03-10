package com.literalura.challenge.model;

import jakarta.persistence.*;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Double numeroDescargas;

    @ManyToOne
    private Autor autor;

    public Libro(){}

    public Libro(String titulo, String idioma, Double numeroDescargas, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return
                "\n----- LIBRO -----" +
                        "\nTítulo: " + titulo +
                        "\nAutor: " + autor.getNombre() +
                        "\nIdioma: " + idioma +
                        "\nDescargas: " + numeroDescargas +
                        "\n-----------------\n";
    }
}