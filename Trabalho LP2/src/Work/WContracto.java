/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Work;

import entidades.Contracto;
import entidades.Pessoa;
import enums.PessoaTipo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author Davide Barruncho
 * @author Yasmine Chede
 */
public class WContracto {

    /**
     *
     */
    public static ArrayList<Contracto> list = new ArrayList<Contracto>();
    /**
     *
     */
    public static ArrayList<Contracto> historico = new ArrayList<Contracto>();
    /**
     *
     */
    public static long contracto_id = 0,
            /**
             *
             */
            artista_id = 0,
            /**
             *
             */
            agente_id = 0,
            /**
             *
             */
            opcao = -1;
    /**
     *
     */
    public static Date data_inicial = new Date();
    /**
     *
     */
    public static Date data_final;
    /**
     *
     */
    public static String obs = null;
    /**
     *
     */
    public static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    /**
     *
     */
    public static Scanner leitor = new Scanner(System.in).useDelimiter("\n");

    /**
     *
     */
    public static void criar_Contracto() {
        System.out.println(Contracto.getId_aux_contracto());
        Contracto.setId_aux_contracto(Contracto.getId_aux_contracto() + 1);
        contracto_id = Contracto.getId_aux_contracto();

        Date data_actual = new Date();

        artista_id = WPessoa.pedir_confirmar_Pessoa(PessoaTipo.Artista);

        if (list.isEmpty()) {
            agente_id = WPessoa.pedir_confirmar_Pessoa(PessoaTipo.Agente);
            data_actual();
            data_final();

            System.out.println("Insira uma Observacao: ");
            obs = leitor.next();

            list.add(new Contracto(contracto_id, artista_id, agente_id, data_inicial, data_final, obs));
            historico.add(new Contracto(contracto_id, artista_id, agente_id, data_inicial, data_final, obs));

            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Contracto criado com sucesso!");
            listar_individual(contracto_id);
            escrever_Contracto();
        } else {
            for (int i = 0; i < list.size(); i++) {//percorre lista contracto
                if (list.get(i).getArtista_Id() == artista_id) {//verifica se o id do artista que escolhemos já existe em algum contracto
                    if (list.get(i).getData_Final().before(data_actual)) {//verifica se a data final do contracto é antes da data actual
                        continue;
                    } else {
                        System.out.println("O Artista " + list.get(i).getArtista_Id() + " ja tem um contracto pendente!");
                        break;
                    }
                }
                agente_id = WPessoa.pedir_confirmar_Pessoa(PessoaTipo.Agente);
                data_actual();
                data_final();

                System.out.println("Insira uma Observacao: ");
                obs = leitor.next();

                list.add(new Contracto(contracto_id, artista_id, agente_id, data_inicial, data_final, obs));
                historico.add(new Contracto(contracto_id, artista_id, agente_id, data_inicial, data_final, obs));

                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.out.println("Contracto criado com sucesso!");
                listar_individual(contracto_id);
                escrever_Contracto();


            }
        }
    }

    /**
     *
     */
    public static void alterar_Contracto() {
        listar();
        confirmar_Id();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getContracto_Id() == opcao) {
                list.remove(i);
                historico.remove(i);

                artista_id = WPessoa.pedir_confirmar_Pessoa(PessoaTipo.Artista);
                agente_id = WPessoa.pedir_confirmar_Pessoa(PessoaTipo.Agente);
                data_actual();
                data_final();
                obs();

                list.add(new Contracto(opcao, artista_id, agente_id, data_inicial, data_final, obs));

                for (int h = 0; h < historico.size(); h++) {
                    if (historico.get(h).getContracto_Id() == opcao) {
                        historico.add(new Contracto(opcao, artista_id, agente_id, data_inicial, data_final, obs));
                    }
                }

                escrever_Contracto();
            }
        }
    }

    /**
     *
     * @param opcao2
     */
    public static void alterar_Contracto_individual(int opcao2) {
        listar();
        confirmar_Id();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getContracto_Id() == opcao) {

                if (opcao2 == 1) {
                    System.out.println("ID Artista: " + list.get(i).getArtista_Id() + "Nome Artista: " + WPessoa.list.get(i).getNome());
                    artista_id = WPessoa.pedir_confirmar_Pessoa(PessoaTipo.Artista);
                    list.get(i).setArtista_Id(artista_id);
                    for (int h = 0; h < historico.size(); h++) {
                        if (historico.get(h).getContracto_Id() == opcao) {
                            historico.get(h).setArtista_Id(artista_id);
                        }
                    }
                }
                if (opcao2 == 2) {
                    System.out.println("ID Agente: " + list.get(i).getAgente_Id() + "Nome Agente: " + WPessoa.list.get(i).getNome());
                    agente_id = WPessoa.pedir_confirmar_Pessoa(PessoaTipo.Agente);
                    list.get(i).setAgente_Id(agente_id);
                    for (int h = 0; h < historico.size(); h++) {
                        if (historico.get(h).getContracto_Id() == opcao) {
                            historico.get(h).setAgente_Id(agente_id);
                        }
                    }
                }
                if (opcao2 == 3) {
                    System.out.println("Data do Contracto: " + list.get(i).getData_Final());
                    data_final();
                    list.get(i).setData_Final(data_final);
                    for (int h = 0; h < historico.size(); h++) {
                        if (historico.get(h).getContracto_Id() == opcao) {
                            historico.get(h).setData_Final(data_final);
                        }
                    }
                }
                if (opcao2 == 4) {
                    System.out.println("Observacoes Antigas: " + list.get(i).getObs());
                    obs();
                    list.get(i).setObs(obs);
                    for (int h = 0; h < historico.size(); h++) {
                        if (historico.get(h).getContracto_Id() == opcao) {
                            historico.get(h).setObs(obs);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     */
    public static void remover() {
        listar();
        confirmar_Id();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getContracto_Id() == opcao) {
                if (list.get(i).getData_Final().after(data_inicial)) {
                    System.out.println("Este contracto ainda não acabou!");
                } else {
                    list.remove(i);
                    System.out.println("Contracto removido com sucesso!");
                    break;
                }
            }
        }
    }

    /**
     *
     */
    public static void listar() {
        ordenar();
        for (int a = 0; a < list.size(); a++) {
            System.out.println(list.get(a));
        }
    }

    /**
     *
     */
    public static void listar_artista_agente_data() {
        boolean erro = false;
        Date data_pesquisar = null;

        long agente = WPessoa.pedir_confirmar_Pessoa(PessoaTipo.Agente);

        do {
            try {
                System.out.println("Insira a data no formato dd/mm/aaaa: ");
                String frase = leitor.next();
                data_pesquisar = df.parse(frase);
            } catch (ParseException e) {
                e.printStackTrace();
                erro = true;
            }
        } while (erro);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAgente_Id() == agente) {
                if (data_pesquisar.after(list.get(i).getData_Inicial()) && data_pesquisar.before(list.get(i).getData_Final())) {
                    for (int j = 0; j < WPessoa.list.size(); j++) {
                        if (WPessoa.list.get(j).getID() == list.get(i).getArtista_Id()) {
                            System.out.println(WPessoa.list.get(j));
                        }
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
            if (list.get(i).getContracto_Id() == opcao) {
                System.out.println(list.get(i));
            }
        }
    }

    /**
     *
     */
    public static void pesquisar_nome_contracto() {
        ArrayList<Pessoa> nome = new ArrayList<Pessoa>();
        ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
        ArrayList<Contracto> total = new ArrayList<Contracto>();

        for (int x = 0; x < historico.size(); x++) {
            for (int i = 0; i < WPessoa.list.size(); i++) {
                if (historico.get(x).getArtista_Id() == WPessoa.list.get(i).getID()) {
                    nome.add(WPessoa.list.get(i));
                }
            }
        }
        System.out.println(nome.size());//numero de ocurrencias


        System.out.println("Insira nome de Artista a pesquisa: ");
        String procura = leitor.next();

        for (int j = 0; j < nome.size(); j++) {
            Pessoa y = nome.get(j);
            if (y.getNome().toUpperCase().contains(procura.toUpperCase())) {
                lista.add(y);
            }
        }
        System.out.println(lista.size());//numero de ocurrencias

        for (int x = 0; x < historico.size(); x++) {
            Contracto y = historico.get(x);
            String b = String.valueOf(y.getArtista_Id());
            for (int k = 0; k < lista.size(); k++) {
                String a = String.valueOf(lista.get(k).getID());
                if (b.toUpperCase().contains(a)) {
                    total.add(y);
                }
            }
        }
        System.out.println("\n\n\n\n\n\n\n");

        System.out.println(total.size());//numero de ocurrencias

        for (int k = 0; k < total.size(); k++) {
            System.out.println(total.get(k));
        }

        if (total.isEmpty()) {
            System.out.println("Não artista corresponde ao procurado!");
        }
    }

    /**
     *
     */
    public static void ordenar() {
        Collections.sort(list, new Comparator<Contracto>() {
            @Override
            public int compare(Contracto o1, Contracto o2) {
                if (o1.getContracto_Id() < o2.getContracto_Id()) {
                    return -1;
                } else if (o1.getContracto_Id() > o2.getContracto_Id()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    /**
     *
     */
    public static void confirmar_Id() {
        boolean erro = false;
        String numero;
        do {
            do {
                try {
                    System.out.println("Insira ID Contracto: ");
                    numero = leitor.next();
                    opcao = Long.parseLong(numero);
                } catch (NumberFormatException e) {
                    System.out.println("Numero Incorrecto!");
                    erro = true;
                }
            } while (erro);

            for (int a = 0; a < list.size(); a++) {
                if (opcao == list.get(a).getContracto_Id()) {
                    erro = false;
                } else {
                    System.out.println("Contracto inexistente!");
                    erro = true;
                }
            }
        } while (erro);
    }

    /**
     *
     */
    public static void data_actual() {
        data_inicial = new Date();
        String data_actual = df.format(data_inicial);
    }

    /**
     *
     */
    public static void data_final() {
        boolean erro = false;
        String frase;
        int ano = 0;
        do {
            try {
                System.out.println("Quantos anos de contracto ?");
                frase = leitor.next();
                ano = Integer.parseInt(frase);
                if (ano < 0) {
                    System.out.println("Ano inferior a zero!");
                    erro = true;
                }
                if (ano == 0) {
                    System.out.println("Ano igual a zero!");
                    erro = true;
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Insira um numero Inteiro!");
                erro = true;
            }
        } while (erro);

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data_inicial);
        gc.add(Calendar.YEAR, ano);
        data_final = gc.getTime();

        System.out.println("Data final do contratcto: " + df.format(data_final));
    }

    /**
     *
     */
    public static void obs() {
        System.out.println("Insira uma Observacao: ");
        obs = leitor.next();
    }

    /**
     *
     */
    public static void escrever_Contracto() {
        try {  // Catch errors in I/O if necessary.
            // Open a file to write to, named SavedObj.dat.
            FileOutputStream saveFile = new FileOutputStream("Contracto.dat");
            
            FileOutputStream saveFileH = new FileOutputStream("Contracto_Hist.dat");

            // Create an ObjectOutputStream to put objects into save file.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            
            ObjectOutputStream saveH = new ObjectOutputStream(saveFileH);

            // Now we do the save.
            save.writeObject(list);
            save.writeLong(Contracto.getId_aux_contracto());

            saveH.writeObject(historico);

            // Close the file.
            save.close(); // This also closes saveFile.
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    /**
     *
     */
    public static void ler_Contracto() {
        // Wrap all in a try/catch block to trap I/O errors.
        try {
            // Open file to read from, named SavedObj.dat.
            FileInputStream saveFile = new FileInputStream("Contracto.dat");
            
            FileInputStream saveFileH = new FileInputStream("Contracto_Hist.dat");

            // Create an ObjectInputStream to get objects from save file.
            ObjectInputStream save = new ObjectInputStream(saveFile);
            
            ObjectInputStream saveH = new ObjectInputStream(saveFileH);

            // Now we do the restore.
            // readObject() returns a generic Object, we cast those back
            // into their original class type.
            // For primitive types, use the corresponding reference class.
            list = (ArrayList<Contracto>) save.readObject();
            long x = save.readLong();
            
            Contracto.setId_aux_contracto(x);

            historico = (ArrayList<Contracto>) saveH.readObject();

            // Close the file.
            save.close(); // This also closes saveFile.
        } catch (FileNotFoundException ex) {
            System.out.println("Ficheiro Contracto.dat nao encontrado!");
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }
}
