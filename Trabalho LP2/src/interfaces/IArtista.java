/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author Davide Barruncho
 * @author Yasmine Chede
 */
public interface IArtista extends IPessoa {

    /**
     *
     * @return
     */
    String getNome_Artistico();
    
    /**
     *
     * @param nome_artistico
     */
    void setNome_Artistico(String nome_artistico);
    
}