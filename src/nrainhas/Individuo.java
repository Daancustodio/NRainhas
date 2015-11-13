//
//=====================================================================================================================
// Problema das N - Rainhas:
// 
//   Cada "individuo" (cromossomo) do algoritmo genético (AG) representa um tabuleiro completo, de N x N posições, 
//   em que estão dispostas N rainhas. 
//=====================================================================================================================
//
package nrainhas;


import java.util.Random;
//
//--------------------------------------------------------------------------------------------------------------------
// Classe....: Individuo                               
// Objetivo..: Calcula o valor da aptidão de um indivíduo a partir do número de colisões (entre rainhas) que 
//             existem nele.
//
//   O "individuo" armazenará...
//
//     tabuleiro - uma matriz N x N (N >= 4) de valores lógicos (TRUE or FALSE), indicando se há (TRUE) ou não (FALSE)
//                 uma rainha naquela posição.
//     colisões  - número de colisões existentes no tabuleiro, ou seja, o número de rainhas que estão se "atacando" 
//                 mutuamente. Para ser uma solução viável, devemos ter que colisões igual a ZERO.
//     aptidao   - valor da função de aptidão para o indivíduo. Corresponde ao número de colisões gerado e, por isso,
//                 deve ser igual a ZERO para que tenhamos uma solução viável para o problema.
//     posicoesY - 
//--------------------------------------------------------------------------------------------------------------------
//
public class Individuo {

    private double      aptidao;
    private int[]       posicoesY;
    private Tabuleiro   tabuleiro;
    private int         colisoes;
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: Individuo (construtor da classe)        
    // Objetivo..: Gera um individuo, com o seu tabuleiro "vazio". Se o parametro "rainhasAleatorias" for TRUE, insere
    //             N Rainhas aleatoriamente no tabuleiro.
    //--------------------------------------------------------------------------------------------------------------------
    //    
    public Individuo(boolean rainhasAleatorias) {
       
        posicoesY = new int[Algoritmo.getN()];
        tabuleiro = new Tabuleiro(Algoritmo.getN());

        for (int i = 0; i < posicoesY.length; i++) {
            posicoesY[i] = -1;
        }

        for (int i = 0; i < posicoesY.length; i++) {
            if (rainhasAleatorias) {
                posicoesY[i] = this.gerarYAleatorioExclusivo();
                tabuleiro.atualizarTabuleiro(posicoesY);
            }
        }

        if (rainhasAleatorias) {
            calcularAptidao();
        }
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Gets and Sets...                                    
    //--------------------------------------------------------------------------------------------------------------------
    //    
    public double getAptidao() {
        return aptidao;
    }
    
    public void setAptidao(int valorAptidao) {
        aptidao = valorAptidao;
    }
    
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public int getColisoes() {
        return colisoes;
    }
    
    public int[] getPosicoesY() {
        return posicoesY;
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: toString                                
    // Objetivo..: Sobrepõe a função "toString" padrão existente em JAVA por uma específica para o problema das 
    //             N Rainhas.        
    //--------------------------------------------------------------------------------------------------------------------
    //
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < Algoritmo.getN(); i++) {
            s += "[" + i + "," + posicoesY[i] + "] ";
        }
        return s;
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: gerarYAleatorioExclusivo                
    // Objetivo..: Gera um valor de Y (horizontal), de forma aleatória, mas que não coincida com uma posição onde já 
    //             esteja armazenada uma rainha.
    //--------------------------------------------------------------------------------------------------------------------
    //
    public int gerarYAleatorioExclusivo() {
        int y;
        Random r;
        boolean encontrou;

        do {
            r         = new Random();
            y         = r.nextInt(Algoritmo.getN());
            encontrou = false;
            for (int i = 0; i < Algoritmo.getN(); i++) {
                if (posicoesY[i] == y) {
                    encontrou = true;
                    break;
                }
            }

        } 
        while (encontrou);

        return y;
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: calcularAptidao                         
    // Objetivo..: Calcula o valor da aptidão de um indivíduo a partir do número de colisões (entre rainhas) que 
    //             existem nele.
    //--------------------------------------------------------------------------------------------------------------------
    //
    public void calcularAptidao() {
        this.colisoes   = calcularNumeroDeColisoes();
        this.aptidao    = colisoes;
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: adicionarRainha                         
    // Objetivo..: Adiciona uma rainha ao tabuleiro do indivíduo. Faz um "sorteio" para determinar se haverá ou não 
    //             uma mutação ocorrendo no momento da adição da rainha.
    //--------------------------------------------------------------------------------------------------------------------
    //
    public void adicionarRainha(int x, int y) {
        Random r = new Random();
        if (r.nextDouble() < Algoritmo.getTaxaDeMutacao()) {
            y = gerarYAleatorioExclusivo();
        }
        posicoesY[x] = y;
        tabuleiro.atualizarTabuleiro(posicoesY);
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: calcularNumeroDeColisoes                
    // Objetivo..: Adiciona uma rainha ao tabuleiro do indivíduo. Faz um "sorteio" para determinar se haverá ou não 
    //             uma mutação ocorrendo no momento da adição da rainha.
    //--------------------------------------------------------------------------------------------------------------------
    //    
    public int calcularNumeroDeColisoes() {
        int x        = 0;
        int y        = 0;
        int tempx    = 0;
        int tempy    = 0;

        int numeroColisoes = 0;
        int dx[]           = new int[]{-1, 1, -1, 1};
        int dy[]           = new int[]{-1, 1, 1, -1};
        boolean            done;
        //
        // Verifica se há colisões na horizontal...
        //
        for (int i = 0; i < Algoritmo.getN(); i++) {
            y = posicoesY[i];
            if (y != -1) {
                for (int j = 0; j < Algoritmo.getN(); j++) {
                    if (posicoesY[j] == y && j != i) {
                        numeroColisoes++;
                    }
                }
            }
        }
        //
        // Verifica se há colisões nas diagonais...
        //
        for (int i = 0; i < Algoritmo.getN(); i++) {
            x = i;
            y = this.posicoesY[i];
            if (y != -1) {
                for (int j = 0; j <= 3; j++) {
                    tempx = x;
                    tempy = y;
                    done = false;
                    while (!done) {
                        tempx += dx[j];
                        tempy += dy[j];
                        if ((tempx < 0 || tempx >= Algoritmo.getN()) || (tempy < 0 || tempy >= Algoritmo.getN())) {
                            done = true;
                        } 
                        else {
                            if (tabuleiro.getTabuleiro()[tempx][tempy]) {
                                numeroColisoes++;
                            }
                        }
                    }
                }
            }
        }
        return (numeroColisoes);
    }
}