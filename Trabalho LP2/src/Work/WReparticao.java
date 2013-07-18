/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Work;

import entidades.Agente;
import entidades.Pessoa;
import entidades.Reparticao;
import enums.PessoaTipo;
import interfaces.IAgente;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Davide Barruncho
 * @author Yasmine Chede
 */
public class WReparticao {

    /**
     *
     */
    public static ArrayList<Reparticao> list = new ArrayList<Reparticao>();
    /**
     *
     */
    public static long reparticao_id = 0, opcao;
    /**
     *
     */
    public static int tamanho_artistas = 0;
    /**
     *
     */
    public static double receita = 0,
            /**
             *
             */
            percentagem_artistas = 0, receita_artistas = 0,
            /**
             *
             */
            percentagem_por_artista = 0, comissao_agente = 50;
    /**
     *
     */
    public static double[] comissoes, montante_artista;
    /**
     *
     */
    public static long[] artistas_ids;
    /**
     *
     */
    public static String frase = null;
    /**
     *
     */
    public static Scanner leitor = new Scanner(System.in).useDelimiter("\n");

    /**
     *
     * @param evento_int
     */
    public static void criar_Reparticao(int evento_int) {
        boolean erro = false;
        System.out.println(Reparticao.getId_aux_reparticao());
        Reparticao.setId_aux_reparticao(Reparticao.getId_aux_reparticao() + 1);
        System.out.println(Reparticao.getId_aux_reparticao());
        reparticao_id = Reparticao.getId_aux_reparticao();

        do {
            try {
                System.out.println("Insira a receita do Evento " + WEvento.list.get(evento_int).getNome_evento() + ": ");
                frase = leitor.next();
                receita = Double.parseDouble(frase);
                WEvento.list.get(evento_int).setReceita(receita);
            } catch (NumberFormatException e) {
                System.out.println("Insira um numero!");
            }

            tamanho_artistas = WEvento.list.get(evento_int).getArtistas().length;
            artistas_ids = WEvento.list.get(evento_int).getArtistas();

            do {
                try {
                    System.out.println("Qual e a percentagem da receita total para os Artistas ? ");
                    frase = leitor.next();
                    percentagem_artistas = Double.parseDouble(frase);
                    int a = Integer.parseInt(frase);
                } catch (NumberFormatException e) {
                    System.out.println("Insira um numero!");
                }
                if (percentagem_artistas < 0 || percentagem_artistas > 100) {
                    System.out.println("Insira um numero entre 0 e 100!");
                    erro = true;
                }

                double x = percentagem_artistas * 0.01;
                double y = receita * x;
                System.out.println("A receita do Evento " + WEvento.list.get(evento_int).getNome_evento() + " e de " + receita + "€. O montante_artista para os Artistas e de " + y + "€");
            } while (erro);

            receita_artistas = (percentagem_artistas * 0.01) * receita;

            if (tamanho_artistas == 1) {
                System.out.println(tamanho_artistas + " Artista vai ao Evento " + WEvento.list.get(evento_int).getNome_evento());
                comissoes = new double[tamanho_artistas];
            } else if (tamanho_artistas > 1) {
                System.out.println(tamanho_artistas + " Artistas vao ao Evento " + WEvento.list.get(evento_int).getNome_evento());
                comissoes = new double[tamanho_artistas];
            }
            montante_artista = new double[tamanho_artistas];

            do {
                double percentagem_restante = 100;
                for (int i = 0; i < tamanho_artistas; i++) {
                    if (i == (tamanho_artistas - 1)) {
                        comissoes[i] = percentagem_restante;
                        System.out.println("Artista numero: " + artistas_ids[i]);
                        System.out.println("Insira a percentagem da receita: \n" + percentagem_restante);
                        percentagem_restante -= percentagem_restante;

                    } else {
                        try {
                            System.out.println("Percentagem Restante: " + percentagem_restante);
                            System.out.println("Artista numero: " + artistas_ids[i]);
                            System.out.println("Insira a percentagem da receita: ");
                            frase = leitor.next();
                            percentagem_por_artista = Integer.parseInt(frase);
                            percentagem_restante -= percentagem_por_artista;
                            comissoes[i] = percentagem_por_artista;
                        } catch (NumberFormatException e) {
                            System.out.println("Insira um numero!");
                        }
                    }

                    System.out.println("SDNSNSDNSDNGGDN" + percentagem_restante);

                    montante_artista[i] = (percentagem_por_artista * 0.01) * receita_artistas;//dinheiro para o artista
                }

                System.out.println("sggdsafgdgfdgf" + percentagem_restante);

                if (percentagem_restante < 0 && percentagem_restante != 0) {
                    System.out.println("\n\n\n\n\n\n\n\nErro!");
                    System.out.println("PERCENTAGEM NEGATIVA!!!");
                    erro = true;
                }
            } while (erro);

            list.add(new Reparticao(reparticao_id, WEvento.list.get(evento_int).getEvento_ID(), receita_artistas, percentagem_artistas, comissoes, montante_artista));

            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Reparticao criada com sucesso!");
            listar_individual(reparticao_id);

            escrever_Reparticao();

        } while (erro);
    }

    /**
     *
     */
    public static void alterar() {
        WEvento.listar();
        WEvento.confirmar_Id();

        for (int i = 0; i < WEvento.list.size(); i++) {
            if (WEvento.list.get(i).getEvento_ID() == opcao) {
                for (int j = 0; j < list.size(); j++) {
                    if (opcao == list.get(j).getEvento_id()) {
                        list.remove(j);
                        criar_Reparticao(j);
                        break;
                    }
                }
            }
        }
    }

    /**
     *
     */
    public static void remover() {
        WEvento.listar();
        WEvento.confirmar_Id();

        for (int i = 0; i < WEvento.list.size(); i++) {
            if (WEvento.list.get(i).getEvento_ID() == opcao) {
                for (int j = 0; j < list.size(); j++) {
                    if (opcao == list.get(j).getEvento_id()) {
                        list.remove(j);
                        criar_Reparticao(j);
                        break;
                    }
                }
            }
        }
    }

    /**
     *
     * @param opcao
     */
    public static void listar_individual(long opcao) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < WReparticao.list.size(); j++) {
                if (list.get(i).getEvento_id() == opcao) {
                    if (!WReparticao.list.isEmpty()) {
                        if (list.get(i).getEvento_id() == WReparticao.list.get(j).getEvento_id()) {
                            System.out.println("REPARTICAO:");
                            System.out.println(WReparticao.list.get(j));
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param i
     * @param montante_artista
     */
    /*
     public static void pagamento(int i, double montante_artista) {
     for (int j = 0; j < WPessoa.list.size(); j++) {
     if (WPessoa.list.get(j).getID() == artistas_ids[i]) {
     for (int g = 0; g < WContracto.list.size(); g++) {
     if (WPessoa.list.get(j).getID() == WContracto.list.get(g).getArtista_Id()) {
     double agente = montante_artista * (comissao_agente * 0.01);
     double artista = montante_artista - agente;
     WPessoa.list.get(j).setCapital(WPessoa.list.get(j).getCapital() + artista);
     for (int k = 0; k < WPessoa.list.size(); k++) {
     if (WContracto.list.get(g).getAgente_Id() == WPessoa.list.get(k).getID()) {
     IAgente Agente = (IAgente) WPessoa.list.get(k);
     double bruto = Agente.getMontante_Devido_Bruto_Agente() + (agente * 0.77);
     double liquido = Agente.getMontante_Devido_Liquido_Agente() + agente;
     Agente.setMontante_Devido_Bruto_Agente(bruto);
     Agente.setMontante_Devido_Liquido_Agente(liquido);
     WPessoa.list.get(k).setCapital(WPessoa.list.get(k).getCapital() + agente);
     }
     }
     } else if (WPessoa.list.get(j).getID() != WContracto.list.get(g).getArtista_Id()) {
     WPessoa.list.get(j).setCapital(WPessoa.list.get(j).getCapital() + montante_artista);
     }
     }
     }
     }
     }*/
    /**
     *
     */
    public static void pagamento_Main() {
        for (int k = 0; k < WEvento.list.size(); k++) {
            for (int i = 0; i < WEvento.list.get(k).getArtistas().length; i++) {
                double[] montante_artista = WReparticao.list.get(i).getMontante_artista();

                long[] artistas = WEvento.list.get(k).getArtistas();
                long[] agentes = WEvento.list.get(k).getAgentes();

                double pagar_agente = montante_artista[i] * (comissao_agente * 0.01);
                double pagar_artista = montante_artista[i] - pagar_agente;

                for (int j = 0; j < WPessoa.list.size(); j++) {
                    if (artistas[i] == WPessoa.list.get(j).getID()) {
                        WPessoa.list.get(j).setCapital(WPessoa.list.get(j).getCapital() + pagar_artista);
                    }
                    if (agentes[i] == WPessoa.list.get(j).getID()) {
                        IAgente Agente = (IAgente) WPessoa.list.get(j);
                        double bruto = Agente.getMontante_Devido_Bruto_Agente() + (pagar_agente * 0.77);
                        double liquido = Agente.getMontante_Devido_Liquido_Agente() + pagar_agente;
                        Agente.setMontante_Devido_Bruto_Agente(bruto);
                        Agente.setMontante_Devido_Liquido_Agente(liquido);
                        WPessoa.list.get(j).setCapital(WPessoa.list.get(j).getCapital() + pagar_agente);
                    }
                }
            }
        }
    }

    /**
     *
     */
    public static void escrever_Reparticao() {
        try {  // Catch errors in I/O if necessary.
            // Open a file to write to, named SavedObj.dat.
            FileOutputStream saveFile = new FileOutputStream("Reparticao.dat");

            // Create an ObjectOutputStream to put objects into save file.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            // Now we do the save.
            save.writeObject(list);
            save.writeLong(Reparticao.getId_aux_reparticao());

            // Close the file.
            save.close(); // This also closes saveFile.
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    /**
     *
     */
    public static void ler_Reparticao() {

        // Wrap all in a try/catch block to trap I/O errors.
        try {
            // Open file to read from, named SavedObj.dat.
            FileInputStream saveFile = new FileInputStream("Reparticao.dat");

            // Create an ObjectInputStream to get objects from save file.
            ObjectInputStream save = new ObjectInputStream(saveFile);

            // Now we do the restore.
            // readObject() returns a generic Object, we cast those back
            // into their original class type.
            // For primitive types, use the corresponding reference class.
            list = (ArrayList<Reparticao>) save.readObject();
            long x = save.readLong();
            Pessoa.setId_aux_pessoa(x);

            // Close the file.
            save.close(); // This also closes saveFile.
        } catch (FileNotFoundException ex) {
            System.out.println("Ficheiro nao encontrado!");
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }
}