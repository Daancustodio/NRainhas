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
// Classe....: Tabuleiro                               
// Objetivo..: Define um "tabuleiro" para o Problema das N-Rainhas. O tabuleiro é quadrado, com dimensões "tamanho" x
//             "tamanho". Armazena valores lógicos (TRUE, FALSE) para indicar se há uma "rainha" naquela posição ou 
//             não.
//--------------------------------------------------------------------------------------------------------------------
//
public class Tabuleiro {

    public boolean[][]  tabuleiro;
    public int          tamanho;
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: Tabuleiro (construtor da classe)        
    // Objetivo..: Gera um tabuleiro, de tamanho especificado
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public Tabuleiro(int tamanho) {
        this.tamanho    = tamanho;
        tabuleiro       = new boolean[tamanho][tamanho];
        inicializarTabuleiro();
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Gets and Sets...                                    
    //--------------------------------------------------------------------------------------------------------------------
    //    
    public boolean[][] getTabuleiro() {
        return tabuleiro;
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
        String r = "";
        for (int y = 0; y < tamanho; y++) {
            for (int x = 0; x < tamanho; x++) {
                if (tabuleiro[x][y]) {
                    r += " x";
                } else {
                    r += " o";
                }
            }
            r += "\n";
        }
        return r;
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: inicializarTabuleiro                    
    // Objetivo..: Inicializa o tabuleiro, sem NENHUMA PEÇA sobre ele (FALSE em todas as suas posições).                                     
    //--------------------------------------------------------------------------------------------------------------------
    //
    public void inicializarTabuleiro() {
        for (int y = 0; y < tamanho; y++) {
            for (int x = 0; x < tamanho; x++) {
                tabuleiro[x][y] = false;
            }
        }
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: atualizarTabuleiro                    
    // Objetivo..: Atualiza o tabuleiro, verificando cada uma de suas posições para indicar quais possuem peças.
    //--------------------------------------------------------------------------------------------------------------------
    //
    public void atualizarTabuleiro(int[] colunasTabuleiro) {
        inicializarTabuleiro();
        for (int i = 0; i < Algoritmo.getN(); i++) {
            if (colunasTabuleiro[i] != -1) {
                tabuleiro[i][colunasTabuleiro[i]] = true;
            }
        }
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: posicaoEstaLivre                      
    // Objetivo..: Verifica se a posição indicada pelos parâmetros "linhaTabuleiro" e "colunaTabuleiro" está livre (TRUE).
    //--------------------------------------------------------------------------------------------------------------------
    //
    public boolean posicaoEstaLivre(int linhaTabuleiro, int colunaTabuleiro) {
        boolean livre = true;
        if (tabuleiro[linhaTabuleiro][colunaTabuleiro]) {
            livre = false;
        }
        return (livre);
    }
}