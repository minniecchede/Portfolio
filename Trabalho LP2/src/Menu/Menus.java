package Menu;

import Work.*;
import enums.PessoaTipo;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Davide Barruncho
 * @author Yasmine Chede
 */
public class Menus {

    /**
     *
     */
    public static Scanner teclado = new Scanner(System.in).useDelimiter("\n");
    /**
     *
     */
    public static String opcao = null;
    /**
     *
     */
    public static char opcao2 = 0;

    /**
     *
     */
    public static void menu_principal() {
        do {
            System.out.println("********************************************");
            System.out.println("*           Menu Principal                 *");
            System.out.println("*                                          *");
            System.out.println("*    1 - Eventos                           *");
            System.out.println("*    2 - Artistas                          *");
            System.out.println("*    3 - Agentes                           *");
            System.out.println("*    4 - Reparticoes                       *");
            System.out.println("*    5 - Contractos                        *");
            System.out.println("*    6 - Listar Pessoas                    *");
            System.out.println("*    S - Sair                              *");
            System.out.println("*                                          *");
            System.out.println("********************************************");

            opcao = teclado.next();
            opcao2 = opcao.charAt(0);

            switch (opcao2) {
                case '1':
                    WEvento.ler_Evento();
                    menu_Eventos();
                    break;
                case '2':
                    WPessoa.ler_Pessoa();
                    menu_Pessoa(PessoaTipo.Artista);
                    break;
                case '3':
                    WPessoa.ler_Pessoa();
                    menu_Pessoa(PessoaTipo.Agente);
                    break;
                case '4':
                    WReparticao.ler_Reparticao();
                    menu_Reparticoes();
                    break;
                case '5':
                    WReparticao.ler_Reparticao();
                    menu_Contractos();
                    break;
                case '6':
                    WPessoa.ler_Pessoa();
                    WPessoa.listar();
                    break;
                case 's':
                case 'S':
                    System.out.println("Máquina Desligada!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Introduzio uma opcao errada! Por favor tente de novo!");
            }
        } while (opcao2 != 's' && opcao2 != 'S');
    }

    /**
     *
     */
    public static void menu_Eventos() {
        do {
            System.out.println("********************************************");
            System.out.println("*           Menu Eventos                   *");
            System.out.println("*                                          *");
            System.out.println("*    1 - Criar                             *");
            System.out.println("*    2 - Editar                            *");
            System.out.println("*    3 - Editar Campo Individual           *");
            System.out.println("*    4 - Apagar                            *");
            System.out.println("*    5 - Listar                            *");
            System.out.println("*    V - Voltar                            *");
            System.out.println("*                                          *");
            System.out.println("********************************************");

            opcao = teclado.next();
            opcao2 = opcao.charAt(0);

            switch (opcao2) {
                case '1':
                    WEvento.criar_Evento();
                    break;
                case '2':
                    WEvento.alterar_Evento();
                    break;
                case '3':
                    menu_Alterar_Individual_Evento();
                    break;
                case '4':
                    WEvento.remover();
                    break;
                case '5':
                    WEvento.listar();
                    break;
                case 'v':
                case 'V':
                    break;
                default:
                    System.out.println("Introduzio uma opcao errada! Por favor tente de novo!");
            }
        } while (opcao2 != 'v' && opcao2 != 'V');
    }

    /**
     *
     * @param PessoaTipo
     */
    public static void menu_Pessoa(PessoaTipo PessoaTipo) {
        do {
            System.out.println("********************************************");
            if (PessoaTipo == PessoaTipo.Agente) {
                System.out.println("*           Menu Agente                    *");
            } else {
                System.out.println("*           Menu Artista                   *");
            }
            System.out.println("*                                          *");
            System.out.println("*    1 - Criar                             *");
            System.out.println("*    2 - Editar                            *");
            System.out.println("*    3 - Editar Campo Individual           *");
            System.out.println("*    4 - Apagar                            *");
            System.out.println("*    5 - Listar                            *");
            System.out.println("*    6 - Pesquisar por nome                *");
            System.out.println("*    7 - Pesquisar por apelido             *");
            System.out.println("*    8 - Pesquisar por NIF                 *");
            System.out.println("*    V - Voltar                            *");
            System.out.println("*                                          *");
            System.out.println("********************************************");

            opcao = teclado.next();
            opcao2 = opcao.charAt(0);

            switch (opcao2) {
                case '1':
                    if (PessoaTipo == PessoaTipo.Agente) {
                        WPessoa.criar_Pessoa(PessoaTipo.Agente);
                    } else {
                        WPessoa.criar_Pessoa(PessoaTipo.Artista);
                    }
                    break;
                case '2':
                    if (PessoaTipo == PessoaTipo.Agente) {
                        WPessoa.alterar_Pessoa(PessoaTipo.Agente);
                    } else {
                        WPessoa.alterar_Pessoa(PessoaTipo.Artista);
                    }
                    break;
                case '3':
                    menu_Alterar_Individual_Pessoa(PessoaTipo);
                    break;
                case '4':
                    WPessoa.remover(PessoaTipo);
                    break;
                case '5':
                    if (PessoaTipo == PessoaTipo.Agente) {
                        WPessoa.listar_Pessoa(PessoaTipo.Agente);
                    } else {
                        WPessoa.listar_Pessoa(PessoaTipo.Artista);
                    }
                    break;
                case '6':
                    WPessoa.pesquisar_nome();
                    break;
                case '7':
                    WPessoa.pesquisar_apelido();
                    break;
                case '8':
                    WPessoa.pesquisar_nif();
                    break;
                case 'v':
                case 'V':
                    break;
                default:
                    System.out.println("Introduzio uma opcao errada! Por favor tente de novo!");
            }
        } while (opcao2 != 'v' && opcao2 != 'V');
    }

    /**
     *
     */
    public static void menu_Reparticoes() {
        do {
            System.out.println("********************************************");
            System.out.println("*           Menu Reparticoes               *");
            System.out.println("*                                          *");
            System.out.println("*    1 - Editar                            *");
            System.out.println("*    2 - Apagar(Cria Reparticao de seguida)*");
            System.out.println("*    V - Voltar                            *");
            System.out.println("*                                          *");
            System.out.println("********************************************");

            opcao = teclado.next();
            opcao2 = opcao.charAt(0);

            switch (opcao2) {
                case '1':
                    WReparticao.alterar();
                    break;
                case '2':
                    WReparticao.remover();
                    break;
                case 'v':
                case 'V':
                    WReparticao.escrever_Reparticao();
                    break;
                default:
                    System.out.println("Introduzio uma opcao errada! Por favor tente de novo!");
            }
        } while (opcao2 != 'v' && opcao2 != 'V');
    }

    /**
     *
     */
    public static void menu_Contractos() {
        do {
            System.out.println("********************************************");
            System.out.println("*           Menu Contractos                *");
            System.out.println("*                                          *");
            System.out.println("*    1 - Criar                             *");
            System.out.println("*    2 - Editar                            *");
            System.out.println("*    3 - Editar Campo Individual           *");
            System.out.println("*    4 - Apagar                            *");
            System.out.println("*    5 - Listar                            *");
            System.out.println("*    6 - Listar Artista de Agente por Data *");
            System.out.println("*    7 - Pesquisar por nome de Artista     *");
            System.out.println("*    8 - Pesquisar por nome de Agente      *");
            System.out.println("*    V - Voltar                            *");
            System.out.println("*                                          *");
            System.out.println("********************************************");

            opcao = teclado.next();
            opcao2 = opcao.charAt(0);

            switch (opcao2) {
                case '1':
                    WContracto.criar_Contracto();
                    break;
                case '2':
                    WContracto.alterar_Contracto();
                    break;
                case '3':
                    menu_Alterar_Individual_Contracto();
                    break;
                case '4':
                    WContracto.remover();
                    break;
                case '5':
                    WContracto.listar();
                    break;
                case '6':
                    WContracto.listar_artista_agente_data();
                    break;
                case '7':
                    WContracto.pesquisar_nome_contracto();
                    break;
                /*case '8':
                 WContracto.pesquisar_nome_agente();
                 break;*/
                case 'v':
                case 'V':
                    break;
                default:
                    System.out.println("Introduzio uma opcao errada! Por favor tente de novo!");
            }
        } while (opcao2 != 'v' && opcao2 != 'V');
    }

    /**
     * **************************************************************
     * @param PessoaTipo
     */
    public static void menu_Alterar_Individual_Pessoa(PessoaTipo PessoaTipo) {
        do {
            int opcao3 = 0;
            System.out.println("********************************************");
            if (PessoaTipo == PessoaTipo.Agente) {
                System.out.println("*           Menu Alterar Agente            *");
            } else {
                System.out.println("*           Menu Alterar Artista           *");
            }
            System.out.println("*                                          *");
            System.out.println("*    1 - Nome                              *");
            System.out.println("*    2 - Apelido                           *");
            System.out.println("*    3 - NIF                               *");
            if (PessoaTipo == PessoaTipo.Artista) {
                System.out.println("*    4 - Nome Artistico                    *");
            } else {
                System.out.println("*    4 - Comissao                          *");
            }
            System.out.println("*    V - Voltar                            *");
            System.out.println("*                                          *");
            System.out.println("********************************************");

            opcao = teclado.next();
            opcao2 = opcao.charAt(0);

            if (PessoaTipo == PessoaTipo.Agente) {
                switch (opcao2) {
                    case '1':
                        opcao3 = Integer.parseInt(opcao);
                        WPessoa.alterar_individual(PessoaTipo, opcao3);
                        break;
                    case '2':
                        opcao3 = Integer.parseInt(opcao);
                        WPessoa.alterar_individual(PessoaTipo, opcao3);
                        break;
                    case '3':
                        opcao3 = Integer.parseInt(opcao);
                        WPessoa.alterar_individual(PessoaTipo, opcao3);
                        break;
                    case '4':
                        opcao3 = Integer.parseInt(opcao);
                        WPessoa.alterar_individual(PessoaTipo, opcao3);
                        break;
                    case 'v':
                    case 'V':
                        WPessoa.escrever_Pessoa();
                        menu_Pessoa(PessoaTipo);
                        break;
                    default:
                        System.out.println("Introduzio uma opcao errada! Por favor tente de novo!");
                }
            } else {
                switch (opcao2) {
                    case '1':
                        opcao3 = Integer.parseInt(opcao);
                        WPessoa.alterar_individual(PessoaTipo, opcao3);
                        break;
                    case '2':
                        opcao3 = Integer.parseInt(opcao);
                        WPessoa.alterar_individual(PessoaTipo, opcao3);
                        break;
                    case '3':
                        opcao3 = Integer.parseInt(opcao);
                        WPessoa.alterar_individual(PessoaTipo, opcao3);
                        break;
                    case '4':
                        opcao3 = Integer.parseInt(opcao);
                        WPessoa.alterar_individual(PessoaTipo, opcao3);
                        break;
                    case 'v':
                    case 'V':
                        WPessoa.escrever_Pessoa();
                        menu_Pessoa(PessoaTipo);
                        break;
                    default:
                        System.out.println("Introduzio uma opcao errada! Por favor tente de novo!");
                }
            }
        } while (opcao2 != 'v' && opcao2 != 'V');
    }

    /**
     *
     */
    public static void menu_Alterar_Individual_Contracto() {
        do {
            int opcao3 = 0;
            System.out.println("********************************************");
            System.out.println("*           Menu Alterar Contracto         *");
            System.out.println("*                                          *");
            System.out.println("*    1 - ID Artista                        *");
            System.out.println("*    2 - ID Agente                         *");
            System.out.println("*    3 - Data do Contracto                 *");
            System.out.println("*    4 - Observacoes                       *");
            System.out.println("*    V - Voltar                            *");
            System.out.println("*                                          *");
            System.out.println("********************************************");

            opcao = teclado.next();
            opcao2 = opcao.charAt(0);

            switch (opcao2) {
                case '1':
                    opcao3 = Integer.parseInt(opcao);
                    WContracto.alterar_Contracto_individual(opcao3);
                    break;
                case '2':
                    opcao3 = Integer.parseInt(opcao);
                    WContracto.alterar_Contracto_individual(opcao3);
                    break;
                case '3':
                    opcao3 = Integer.parseInt(opcao);
                    WContracto.alterar_Contracto_individual(opcao3);
                    break;
                case '4':
                    opcao3 = Integer.parseInt(opcao);
                    WContracto.alterar_Contracto_individual(opcao3);
                    break;
                case 'v':
                case 'V':
                    WContracto.escrever_Contracto();
                    menu_Contractos();
                    break;
                default:
                    System.out.println("Introduzio uma opcao errada! Por favor tente de novo!");
            }
        } while (opcao2 != 'v' && opcao2 != 'V');
    }

    /**
     *
     */
    public static void menu_Alterar_Individual_Evento() {
        do {
            int opcao3 = 0;
            System.out.println("********************************************");
            System.out.println("*           Menu Alterar Evento            *");
            System.out.println("*                                          *");
            System.out.println("*    1 - Nome do Evento                    *");
            System.out.println("*    2 - Numero de Artistas                *");
            System.out.println("*    3 - Data do Evento                    *");
            System.out.println("*    V - Voltar                            *");
            System.out.println("*                                          *");
            System.out.println("********************************************");

            opcao = teclado.next();
            opcao2 = opcao.charAt(0);

            switch (opcao2) {
                case '1':
                    opcao3 = Integer.parseInt(opcao);
                    WEvento.alterar_Evento_individual(opcao3);
                    break;
                case '2':
                    opcao3 = Integer.parseInt(opcao);
                    WEvento.alterar_Evento_individual(opcao3);
                    break;
                case '3':
                    opcao3 = Integer.parseInt(opcao);
                    WEvento.alterar_Evento_individual(opcao3);
                    break;
                case 'v':
                case 'V':
                    WEvento.escrever_Evento();
                    menu_Eventos();
                    break;
                default:
                    System.out.println("Introduzio uma opcao errada! Por favor tente de novo!");
            }
        } while (opcao2 != 'v' && opcao2 != 'V');
    }
}