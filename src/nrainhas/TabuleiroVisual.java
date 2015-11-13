//
//=====================================================================================================================
// Problema das N - Rainhas:
// 
//   Cada "individuo" (cromossomo) do algoritmo genético (AG) representa um tabuleiro completo, de N x N posições, 
//   em que estão dispostas N rainhas. 
//=====================================================================================================================
//
package nrainhas;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
//
//--------------------------------------------------------------------------------------------------------------------
// Classe....: TabuleiroVisual                         
// Objetivo..: É a representação gráfica do "tabuleiro", ou seja, no monitor do computador.
// Observação: Utiliza as bibliotecas AWT e SWING do JAVA.
//--------------------------------------------------------------------------------------------------------------------
//
public class TabuleiroVisual extends JPanel {

    private int         tamanhoQuadrado = 50;      // Define o tamanho de cada quadrado do tabuleiro
    private Tabuleiro   tabuleiro;
    private int         widthComponent;
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: TabuleiroVisual (construtor da classe)        
    // Objetivo..: Gera, visualmente, o  tabuleiro no monitor do computador.                                      
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public TabuleiroVisual(Tabuleiro tabuleiro, int w) {
        this.tabuleiro        = tabuleiro;
        this.widthComponent   = w;
        tamanhoQuadrado       = (int) widthComponent / Algoritmo.getN();
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: paintComponent                                
    // Objetivo..: Sobrepõe o método paintComponent para gerar o desenho de um "tabuleiro" na tela, considerando o 
    //             número de linhas e colunas que este deve ter.
    //--------------------------------------------------------------------------------------------------------------------
    //  
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cont = 0;
        for (int y = 0; y < Algoritmo.getN(); y++) {
            cont++;
            for (int x = 0; x < Algoritmo.getN(); x++) {
                if (cont % 2 == 0) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                cont++;
                g.fillRect(tamanhoQuadrado * x, tamanhoQuadrado * y, tamanhoQuadrado, tamanhoQuadrado);
            }
        }

        for (int y = 0; y < Algoritmo.getN(); y++) {
            for (int x = 0; x < Algoritmo.getN(); x++) {
                if (tabuleiro.getTabuleiro()[x][y]) {
                    g.setColor(Color.red);
                    g.fillOval(tamanhoQuadrado * x, tamanhoQuadrado * y, tamanhoQuadrado, tamanhoQuadrado);
                }
            }
        }
    }
    //
    //--------------------------------------------------------------------------------------------------------------------
    // Metodo....: setTabuleiroVisual        
    // Objetivo..: Dimensiona e desenha o tabuleiro visual no monitor do computador.                           
    //--------------------------------------------------------------------------------------------------------------------
    //  
    public void setTabuleiroVisual(Tabuleiro tabuleiro) {
        this.tabuleiro  = tabuleiro;
        tamanhoQuadrado = (int) widthComponent / Algoritmo.getN();
        paintComponent(super.getGraphics());
    }
}