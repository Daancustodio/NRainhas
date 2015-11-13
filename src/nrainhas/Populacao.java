//
//=====================================================================================================================
// Problema das N - Rainhas:
// 
//   Cada "individuo" (cromossomo) do algoritmo genético (AG) representa um tabuleiro completo, de N x N posições, 
//   em que estão dispostas N rainhas. 
//=====================================================================================================================
//
package nrainhas;
//
//--------------------------------------------------------------------------------------------------------------------
// Classe....: Populacao                               
// Objetivo..: Representa a população atual (ou geração atual de indivíduos), com "tamanhoPopulacao" indivíduos. 
//             Lembre-se: cada indivíduo é um "tabuleiro" inteiro do problema das N-Rainhas, com suas informações 
//             adicionais.               
//--------------------------------------------------------------------------------------------------------------------
//
public class Populacao {

    private Individuo[] individuos;
    private int         tamanhoPopulacao;
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: Populacao (construtor da classe)        
    // Objetivo..: Gera uma população de indivíduos (geração atual). Se o parâmetro "individuosAleatorios" for igual a
    //             TRUE, então gera indivíduos aleatórios (ou seja, tabuleiros aleatoriamente criados). Do contrário, 
    //             todos os indivíduos são NULOS.
    //--------------------------------------------------------------------------------------------------------------------
    //    
    public Populacao(int tamPop, boolean individuosAleatorios) {
        tamanhoPopulacao    = tamPop;
        individuos          = new Individuo[tamPop];

        for (int i = 0; i < individuos.length; i++) {
            if (individuosAleatorios) {
                individuos[i] = new Individuo(true);
            } 
            else {
                individuos[i] = null;
            }
        }
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: setIndividuo         
    // Objetivo..: Insere o indivíduo recebido como parâmetro, na posição indicada na população atual.           
    //--------------------------------------------------------------------------------------------------------------------
    //    
    public void setIndividuo(Individuo individuo, int posicao) {
        individuos[posicao] = individuo;
    }
    // 
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: setIndividuo         
    // Objetivo..: Insere o indivíduo recebido como parâmetro, na posição PRIMEIRA POSIÇÃO LIVRE da população atual.
    // Observação: Se nenhum posição estiver livre, o indivíduo não é inserido e, sim, desprezado.
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public void setIndividuo(Individuo individuo) {
        for (int i = 0; i < individuos.length; i++) {
            if (individuos[i] == null) {
                individuos[i] = individuo;
                return;
            }
        }
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: ordenaPopulacao      
    // Objetivo..: Ordena os indivíduos da população atual considerando o valor da aptidão (fitness) de cada um.
    // Observação: A ordem é crescente, ou seja, o indivíduo de maior aptidão (neste caso, a aptidão indica o número de
    //             colisões entre rainhas no tabuleiro) ficará no "final" da população. 
    //  
    //             Lembre-se: Neste problema, quanto menor o valor da aptidão, melhor, pois indica que temos menos 
    //                        colisões entre rainhas no tabuleiro.
    //
    //             O método de ordenação utilizado é o "Método da Bolha" (ou "Bubble Sort")
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public void ordenaPopulacao() {
        boolean trocou = true;
        while (trocou) {
            trocou = false;
            for (int i = 0; i < individuos.length - 1; i++) {
                if (individuos[i].getAptidao() > individuos[i + 1].getAptidao()) {
                    Individuo temp     = individuos[i];
                    individuos[i]      = individuos[i + 1];
                    individuos[i + 1]  = temp;
                    trocou             = true;
                }
            }
        }
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: getNumeroIndividuos         
    // Objetivo..: Retorna o número de indivíduos (não nulos) na população atual.                       
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public int getNumeroIndividuos() {
        int num = 0;
        for (int i = 0; i < individuos.length; i++) {
            if (individuos[i] != null) {
                num++;
            }
        }
        return (num);
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: calculaMediaAptidao 
    // Objetivo..: Calcula a aptidão média da população atual.                                                   
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public double calculaMediaAptidao() {
        return (calculalAptidaoTotal() / getNumeroIndividuos());
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: calculaAptidaoTotal 
    // Objetivo..: Calcula a aptidão total da população atual (soma todas as aptidões dos indivíduos).                                                   
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public double calculalAptidaoTotal() {
        double total = 0;
        for (int i = 0; (i < individuos.length); i++) {
            if (individuos[i] != null) {
                total += individuos[i].getAptidao();
            }
        }
        return (total);
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: getTamanhoPopulacao 
    // Objetivo..: Retorna o número de indivíduos da população atual                                                   
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public int getTamanhoPopulacao() {
        return (tamanhoPopulacao);
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: getIndividuo 
    // Objetivo..: Retorna o indivíduo que ocupa a posição "pos" na população atual                                                   
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public Individuo getIndividuo(int pos) {
        return (individuos[pos]);
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: getIndividuos 
    // Objetivo..: Retorna TODOS os indivíduos da  população atual                                                   
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public Individuo[] getIndividuos() {
        return (individuos);
    }
}
