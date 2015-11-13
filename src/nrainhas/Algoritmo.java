//
//=====================================================================================================================
// Problema das N - Rainhas:
// 
//   Cada "individuo" (cromossomo) do algoritmo genético (AG) representa um tabuleiro completo, de N x N posições, 
//   em que estão dispostas N rainhas. 
//=====================================================================================================================
//
package nrainhas;

import java.util.ArrayList;
import java.util.Random;
//
//--------------------------------------------------------------------------------------------------------------------
// Classe....: Algoritmo                                     
// Objetivo..: Implementa o Algoritmo Genético (AG).                                                         
//--------------------------------------------------------------------------------------------------------------------
// 
public class Algoritmo {

    private static int     N;
    private static double  taxaDeCrossover;
    private static double  taxaDeMutacao;
    private static int     numeroMaximoGeracoes;
    private static int     tamanhoPopulacao;
    private static boolean elitismo;

    public static void AG(FrameNRainhas frame) {
        new Thread(new ExecutaAG(frame)).start();
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: novaGeracao                                   
    // Objetivo..: Constrói a "nova" geração de indivídudos do AG a partir da "atual" geração de indíviduos. 
    //--------------------------------------------------------------------------------------------------------------------
    // 
    public static Populacao novaGeracao(Populacao populacao) {

        Random r;
        Populacao novaPopulacao = new Populacao(populacao.getTamanhoPopulacao(), false);
        
        if (isElitismo()) {
            novaPopulacao.setIndividuo(populacao.getIndividuo(0));
        }

        while (novaPopulacao.getNumeroIndividuos() < novaPopulacao.getTamanhoPopulacao()) {

            Individuo pais[]     = new Individuo[2];
            Individuo filhos[]   = new Individuo[2];
            //
            // Para realizar a recombinação (crossover1point) os pais (progenitores) são escolhidos empregado a 
            // "Seleção por Sorteio" 
            //
            pais[0] = selecaoTorneio(populacao);
            pais[1] = selecaoTorneio(populacao);

            r = new Random();
            if (r.nextDouble() <= taxaDeCrossover) {
                filhos = Algoritmo.crossover1point(pais[0], pais[1]);
                filhos[0].calcularAptidao();
                filhos[1].calcularAptidao();
            } 
            else {
                filhos[0] = pais[0];
                filhos[1] = pais[1];
            }

            novaPopulacao.setIndividuo(filhos[0]);
            novaPopulacao.setIndividuo(filhos[1]);

        }
        novaPopulacao.ordenaPopulacao();
        return novaPopulacao;
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: crossover1point                                     
    // Objetivo..: Imprimenta a recombinação (crossover) empregando o método "one-point crossover", ou seja, os cromos-
    //             somos dos dois pais são quebrados num único ponto para gerar os dois cromossommos filhos.
    //--------------------------------------------------------------------------------------------------------------------
    // 
    public static Individuo[] crossover1point(Individuo pai1, Individuo pai2) {
        Random r           = new Random();
        Individuo filhos[] = new Individuo[2];

        //
        // Cria dois indivíduos filhos, inicialmente VAZIOS.
        //
        filhos[0] = new Individuo(false);
        filhos[1] = new Individuo(false);
        //
        // Escolhe, aleatoriamente, um "ponto de quebra" (ou ponto de corte) para gerar, a partir dos dois pais, os
        // dois filhos.
        //
        int pontoCorte = r.nextInt(Algoritmo.N);
        //
        // Constrói os dois filhos por meio da "troca" dos segmentos superior e inferior entre os dois pais.
        //
        for (int i = 0; i < Algoritmo.N; i++) {
            if (i < pontoCorte) {
                filhos[0].adicionarRainha(i, pai1.getPosicoesY()[i]);
                filhos[1].adicionarRainha(i, pai2.getPosicoesY()[i]);
            } 
            else {
                filhos[0].adicionarRainha(i, pai2.getPosicoesY()[i]);
                filhos[1].adicionarRainha(i, pai1.getPosicoesY()[i]);
            }
        }
        filhos[0].calcularAptidao();
        filhos[1].calcularAptidao();

        return (filhos);
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: selecaoTorneio                                
    // Objetivo..: Constrói a "nova" geração de indivídudos do AG a partir da "atual" geração de indíviduos empregando
    //             a técnica do TORNEIO.
    //--------------------------------------------------------------------------------------------------------------------
    // 
    public static Individuo selecaoTorneio(Populacao populacao) {
       
        Random r                          = new Random();
        //
        // Gera a popúlação intermediária, com apenas DOIS indivíduos inicialmente "vazios"...
        //   Eles são, em seguida, preenchidos de forma aleatória a partir dos indivíduos da população atual
        //
        Populacao populacaoIntermediaria  = new Populacao(2, false);
        
        populacaoIntermediaria.setIndividuo(populacao.getIndividuo(r.nextInt(populacao.getTamanhoPopulacao())));
        r = new Random();
        populacaoIntermediaria.setIndividuo(populacao.getIndividuo(r.nextInt(populacao.getTamanhoPopulacao())));
        //
        // Coloca a população (de DOIS indivíduos) em ordem e escolhe um eles... com 90% de chance para o primeiro e
        //                                                                           10% de chance para o segundo
        //
        populacaoIntermediaria.ordenaPopulacao();

        int pos;
        r   = new Random();
        if (r.nextDouble() < 0.9) {
            pos = 0;
        } 
        else {
            pos = 1;
        }

        return (populacaoIntermediaria.getIndividuo(pos));
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: selecaoRodaDaRoleta                           
    // Objetivo..: Constrói a "nova" geração de indivídudos do AG a partir da "atual" geração de indíviduos empregando
    //             a técnica da ROLETA.
    //
    // Observacao: Atualmente esta técnica não está sendo utilizada no AG. Pode substituir da selação por torneio.
    //--------------------------------------------------------------------------------------------------------------------
    // 
    public static Individuo[] selecaoRodaDaRoleta(Populacao populacao) {
        //
        // Gera a popúlação intermediária, com apenas DOIS indivíduos inicialmente "vazios"...
        //
        Individuo[] individuosEscolhidos = new Individuo[2];
        ArrayList<Double> individuosTemp = new ArrayList<Double>();
        //
        // Executa a construção da "roleta": primeiro calcula qual é aptidão aculuada até aquele indivíduo...
        //
        double aptidaoAcumulada = 0;
        for (int i = 0; i < populacao.getNumeroIndividuos(); i++) {
            aptidaoAcumulada += populacao.getIndividuo(i).getAptidao();
            individuosTemp.add(i, aptidaoAcumulada);
        }
        //
        // ... em seguida, escolhe o primeiro indivíduo...
        //
        Random r        = new Random();
        double ponteiro = r.nextDouble() * aptidaoAcumulada;

        for (int i = 0; i < individuosTemp.size(); i++) {
            if (individuosTemp.get(i) > ponteiro) {
                individuosEscolhidos[0] = populacao.getIndividuo(i);
                break;
            }
        }
        //
        //... por fim, escolhe o segundo indivíduo...
        //
        r = new Random();
        ponteiro = r.nextDouble() * aptidaoAcumulada;

        for (int i = 0; i < individuosTemp.size(); i++) {
            if (individuosTemp.get(i) > ponteiro) {
                individuosEscolhidos[1] = populacao.getIndividuo(i);
                break;
            }
        }
        //
        // Retorna os dois indíviduos escolhidos (que irão participar da recombinação)
        //
        return (individuosEscolhidos);
    }

    public static double getTaxaDeCrossover() {
        return taxaDeCrossover;
    }

    public static void setTaxaDeCrossover(double taxaDeCrossover) {
        Algoritmo.taxaDeCrossover = taxaDeCrossover;
    }

    public static double getTaxaDeMutacao() {
        return taxaDeMutacao;
    }

    public static void setTaxaDeMutacao(double taxaDeMutacao) {
        Algoritmo.taxaDeMutacao = taxaDeMutacao;
    }

    public static int getNumeroMaximoGeracoes() {
        return numeroMaximoGeracoes;
    }

    public static void setNumeroMaximoGeracoes(int numeroMaximoGeracoes) {
        if (numeroMaximoGeracoes > 0) {
           Algoritmo.numeroMaximoGeracoes = numeroMaximoGeracoes;
        }
    }

    public static int getN() {
        return N;
    }

    public static void setN(int N) {
       Algoritmo.N = N;
    }

    public static int getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public static void setTamanhoPopulacao(int tamanhoPopulacao) {
        if (tamanhoPopulacao > 1) {
           Algoritmo.tamanhoPopulacao = tamanhoPopulacao;
        }
    }

    public static boolean isElitismo() {
        return elitismo;
    }

    public static void setElitismo(boolean elitismo) {
        Algoritmo.elitismo = elitismo;
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Classe....: ExecutaAG                                     
    // Objetivo..: Implementa o "corpo principal" do AG, ou seja, é o seu controlador.                           
    //--------------------------------------------------------------------------------------------------------------------
    // 
    private static class ExecutaAG implements Runnable {

        private FrameNRainhas frame;
         
        public ExecutaAG(FrameNRainhas frame) {
            this.frame = frame;
        }

        @Override
        public void run() {

            frame.setEstadoIniciarBotao(false);

            
            //
            // Cria a PRIMEIRA GERAÇÃO da população, ordenando-a de acordo com a aptidão de cada indivíduo...
            //
            int geracao          = 1;
            Populacao populacao  = new Populacao(getTamanhoPopulacao(), true);
            populacao.ordenaPopulacao();
            frame.setLog("Geração " + geracao + ":\n"
                    + "Melhor: " + populacao.getIndividuo(0).getAptidao() + " (" + populacao.getIndividuo(0).getColisoes() + ")" + "\n"
                    + "Média: " + populacao.calculaMediaAptidao() + "\n"
                    + "Pior: " + populacao.getIndividuo(populacao.getNumeroIndividuos() - 1).getAptidao() + "(" + populacao.getIndividuo(populacao.getNumeroIndividuos() - 1).getColisoes() + ")" + "\n"
                    + "-------------------------------------");

            frame.getTabuleiroVisual().setTabuleiroVisual(populacao.getIndividuo(0).getTabuleiro());
            //
            // Realiza o processo de construção das gerações: 2, 3, 4, ...
            //
            while (geracao < getNumeroMaximoGeracoes()) {
                geracao++;
                //
                // Gera a "nova" população a partir da geração atual...
                //
                populacao = Algoritmo.novaGeracao(populacao);

                frame.setLog("Geração " + geracao + ":\n"
                        + "Melhor: " + populacao.getIndividuo(0).getAptidao() + " (" + populacao.getIndividuo(0).getColisoes() + ")" + "\n"
                        + "Média: " + populacao.calculaMediaAptidao() + "\n"
                        + "Pior: " + populacao.getIndividuo(populacao.getNumeroIndividuos() - 1).getAptidao() + "(" + populacao.getIndividuo(populacao.getNumeroIndividuos() - 1).getColisoes() + ")" + "\n"
                        + "-------------------------------------");

                //
                // A cada 1000 gerações, o gráfico (de análise do AG) é atualizado e apresentado no monitor...
                //
                if (geracao % (getNumeroMaximoGeracoes() / 1000) == 0) {
                    frame.getChart().update(geracao, populacao.getIndividuo(0).getAptidao(), populacao.calculaMediaAptidao(), populacao.getIndividuo(populacao.getNumeroIndividuos() - 1).getAptidao());
                }
                //
                // O melhor indivíduo da geração atual é uma solução (ou seja, não apresenta nenhuma COLISÃO de rainhas)?
                //   SIM, então pare, pois encontrou a solução.
                //   NÃO, continue e construa a próxima geração.
                //
                if (populacao.getIndividuo(0).getColisoes() == 0) {
                    frame.setLog("SOLUÇÃO ENCONTRADA!");
                    frame.getChart().update(geracao, populacao.getIndividuo(0).getAptidao(), populacao.calculaMediaAptidao(), populacao.getIndividuo(populacao.getNumeroIndividuos() - 1).getAptidao());
                    break;
                }

            }
            frame.getTabuleiroVisual().setTabuleiroVisual(populacao.getIndividuo(0).getTabuleiro());

            frame.setEstadoIniciarBotao(true);

        }
    }
}

