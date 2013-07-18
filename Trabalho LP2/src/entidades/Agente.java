/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import enums.PessoaTipo;
import interfaces.IAgente;
import java.io.Serializable;

/**
 *
 * @author Davide Barruncho
 * @author Yasmine Chede
 */
public class Agente extends Pessoa implements IAgente {

    private double montante_devido_bruto_agente = 0;
    private double montante_devido_liquido_agente = 0;


    /**
     *
     * @return
     */
    public double getMontante_Devido_Bruto_Agente() {
        return montante_devido_bruto_agente;
    }

    /**
     *
     * @param montante_devido_bruto_agente
     */
    public void setMontante_Devido_Bruto_Agente(double montante_devido_bruto_agente) {
        this.montante_devido_bruto_agente = montante_devido_bruto_agente;
    }

    /**
     *
     * @return
     */
    public double getMontante_Devido_Liquido_Agente() {
        return montante_devido_liquido_agente;
    }

    /**
     *
     * @param montante_devido_liquido_agente
     */
    public void setMontante_Devido_Liquido_Agente(double montante_devido_liquido_agente) {
        this.montante_devido_liquido_agente = montante_devido_liquido_agente;
    }

    /**
     *
     * @param nome
     * @param apelido
     * @param id
     * @param nif
     * @param montante_devido_bruto_agente
     * @param montante_devido_liquido_agente
     * @param capital
     */
    public Agente(String nome, String apelido, long id, long nif, double montante_devido_bruto_agente,
            double montante_devido_liquido_agente, double capital) {
        super(PessoaTipo.Agente, nome, apelido, nif, id, capital);
        this.montante_devido_bruto_agente = montante_devido_bruto_agente;
        this.montante_devido_liquido_agente = montante_devido_liquido_agente;
    }

    @Override
    public String toString() {
        return ("----ID:" + getID() + "----"
                + "\nNome: " + getNome() + " " + getApelido()
                + "\nNIF: " + getNif()
                + "\nCapital: " + getCapital() + "€"
                + "\nMontante Devido Bruto: " + this.montante_devido_bruto_agente + "€"
                + "\nMontante Devido Liquido: " + this.montante_devido_liquido_agente + "€");
    }
}
