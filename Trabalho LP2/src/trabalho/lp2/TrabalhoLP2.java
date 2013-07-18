/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho.lp2;

import Menu.Menus;
import Work.*;
import java.util.Date;

/**
 *
 * @author Davide Barruncho
 * @author Yasmine Chede
 */
public class TrabalhoLP2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WEvento.ler_Evento();
        for (int i = 0; i < WEvento.list.size(); i++) {
            if (WEvento.list.get(i).getPago() == false) {
                if (WEvento.list.get(i).getData().before(new Date())) {
                    WReparticao.pagamento_Main();
                }
            }
        }
        for (;;) {
            Menus.menu_principal();
        }
    }
}
