/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import enums.PessoaTipo;
import interfaces.IArtista;
import java.io.Serializable;

/**
 *
 * @author Davide Barruncho
 * @author Yasmine Chede
 */
public class Artista extends Pessoa implements IArtista {

    private String nome_artistico = null;
    private double capital = 0;

    /**
     *
     * @return
     */
    public String getNome_Artistico() {
        return nome_artistico;
    }

    /**
     *
     * @param nome_artistico
     */
    public void setNome_Artistico(String nome_artistico) {
        this.nome_artistico = nome_artistico;
    }

    /**
     *
     * @param nome
     * @param apelido
     * @param id
     * @param nome_artistico
     * @param nif
     * @param capital
     */
    public Artista(String nome, String apelido, long id, String nome_artistico, long nif, double capital) {
        super(PessoaTipo.Artista, nome, apelido, nif, id, capital);
        this.nome_artistico = nome_artistico;
        this.capital = capital;
    }

    @Override
    public String toString() {
        return ("----ID:" + getID() + "----"
                + "\nNome: " + getNome() + " " + getApelido()
                + "\nNome Artistico: " + this.nome_artistico
                + "\nNIF: " + getNif()
                + "\nCapital: " + getCapital());
    }
}
