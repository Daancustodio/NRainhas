//
//=====================================================================================================================
// Problema das N - Rainhas (N-Queen Problem)
// 
//    Enunciado do problema: 
// 
//    Considere um tabuleiro quadrado, quadriculado com N linhas e N colunas, N >= 4, em que cada quadrado pode ser 
//    PRETO ou BRANCO. 
//    
//    Deseja-se posicionar neste tabuleiro N RAINHAS, de forma de cada uma delas não seja capaz de capturar as demais,
//    ou seja, duas rainhas não podem estar na mesma linha (HORIZONTAL), na mesma coluna (VERTICAL) ou na mesma 
//    DIAGONAL (cada rainha participa de até duas diagonais).
//
//    Lembre-se que a rainha é a peça que possui a movimentação mais poderosa do jogo de xadrez: movimenta-se em qual-
//    quer direção (HORIZONTAL, VERTICAL, DIAGONAL), por qualquer número de casas.
//
//    Como posicionar as N-Rainhas para atender à solicitação?
//
// Este algoritmo genético (AG) resolve o problema.
//=====================================================================================================================
//
package nrainhas;

public class NRainhas {
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: main                                          
    // Objetivo..: Executar a aplicação - Problema das N-Rainhas, com interface visual         
    //
    // Observacao: Por ser uma aplicação que emprega uma INTERFACE GRÁFICA, o que o "main" faz é apenas executar o
    //             frame.
    //
    // 
    //--------------------------------------------------------------------------------------------------------------------
    //  
   public static void main(String[] args) {
      
      new FrameNRainhas().setVisible(true);

   }
   
}
