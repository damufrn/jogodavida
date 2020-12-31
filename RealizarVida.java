//Criado por Darlan Araújo Moreira
//Agosto de 2020
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import java.util.Timer;
import java.util.TimerTask;


import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;


import java.awt.event.*;

import java.lang.Math;
class MNU 
{

  final static String ARQUIVO = "Arquivo";
  final static String INICIAR = "Iniciar";
  final static String PARAR = "Parar";
  final static String LIMPAR = "Limpar";
  final static String SAIR = "Sair";
}


public class RealizarVida   //Classe de Aplicação
{
  public static void main(String args [])
  {
     JFrame tela = new JFrame();
     Painel painel = new Painel();
     
     JMenuBar barraDeMenu = new JMenuBar();
     JMenu menuArquivo = new JMenu(MNU.ARQUIVO);
     JMenuItem acaoIniciar = new JMenuItem(MNU.INICIAR);
     JMenuItem acaoParar = new JMenuItem(MNU.PARAR);
     JMenuItem acaoLimpar = new JMenuItem(MNU.LIMPAR);
     JMenuItem acaoSair = new JMenuItem(MNU.SAIR);
     menuArquivo.add(acaoIniciar);
     menuArquivo.add(acaoParar);
     menuArquivo.add(acaoLimpar);
     menuArquivo.addSeparator();
     menuArquivo.add(acaoSair);
     acaoIniciar.addActionListener(painel);
     acaoLimpar.addActionListener(painel);
     acaoParar.addActionListener(painel);
     acaoSair.addActionListener(painel);

     barraDeMenu.add(menuArquivo);

     painel.addMouseListener(painel);
     tela.setTitle("Jogo da Viva - Darlan Moreira ago/2020");
     tela.setResizable(false);
     tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     tela.add(painel);
     tela.setJMenuBar(barraDeMenu); 
     tela.setSize(1001,1001);
     tela.setLocation(500,100);
     tela.setVisible(true);

  }







}

class Vida
{
     static final int VAZIO = 0;
     static final int OCUPADO = 1;
     int passos =0;
     int numCol;
     int numLin;
     int [][] elementos;

     Vida(int numCol, int numLin)
     {
        this.numCol=numCol;
        this.numLin=numLin;
        elementos = new int [this.numCol][this.numLin];
     }

     Vida()
     {
        this(100,100);
     }

     int getCol()
     {
        return(this.numCol);
     }
     int getLin()
     {
        return(this.numLin);
     }
     
     void setLength(int numCol, int numLin)
     {
      this.numCol=numCol;
      this.numLin=numLin;
      elementos = new int [this.numCol][this.numLin];
      this.clear();
     }

     void clear()
     {
       for(int i=0;i<numCol;i++)
          for(int j=0;j<numLin;j++)
             elementos[i][j]=Vida.VAZIO;

     }


     void setOcupado(int col, int lin)
     {
        elementos[col][lin]=Vida.OCUPADO;
     }
     void setVazio(int col, int lin)
     {
        elementos[col][lin]=Vida.VAZIO;
     }

     void setTrocar(int col, int lin)
     {
        if (elementos[col][lin]==Vida.VAZIO) elementos[col][lin]=Vida.OCUPADO;
          else
            if (elementos[col][lin]==Vida.OCUPADO) elementos[col][lin]=Vida.VAZIO;
     }


     int getSituacao(int col, int lin)
     {
        return elementos[col][lin];
     }

     void executarUmPasso()
     {
       int numVizinhos=0,colAnt,linAnt,colPos,linPos;
       int [][] elementosNovos = new int [this.numCol][this.numLin];
      // System.out.println("----------------------------");
       for(int i=0;i<numCol;i++)
          for(int j=0;j<numLin;j++)
          {
             numVizinhos=0;
             colAnt=(numCol+i-1) % numCol;
             linAnt=(numLin+j-1) % numLin;
             colPos=(i+1)%this.numCol;
             linPos=(j+1)%this.numLin;
             //OCUPADO tem que ser igual a 1.
             numVizinhos=elementos[colAnt][j]+elementos[colAnt][linAnt]+elementos[colAnt][linPos];
             numVizinhos+=elementos[colPos][j]+elementos[colPos][linAnt]+elementos[colPos][linPos];
             numVizinhos+=elementos[i][linAnt]+elementos[i][linPos];
             //if (numVizinhos>0) System.out.println(i +  " " + j + " " + numVizinhos);
             elementosNovos[i][j]=elementos[i][j];
             if ((elementos[i][j]==OCUPADO) && (numVizinhos<2)) elementosNovos[i][j]=VAZIO;
               else if ((elementos[i][j]==OCUPADO) && (numVizinhos>3)) elementosNovos[i][j]=VAZIO;
                 else if ((elementos[i][j]==VAZIO  ) && (numVizinhos==3)) elementosNovos[i][j]=OCUPADO;

          }     
       elementos = elementosNovos;
       passos++;
       //System.out.println(passos);

     }

}




class Painel extends JPanel implements MouseListener, ActionListener
{
   Timer temporizador;
   int SALTOMS=20; 
   int a = 0;
   int n=-1, m=-1;
   double angulo;
   double altura, largura;
   Vida tabuleiro = new Vida(100,100);

   public Painel()
   {
     angulo =0.0;
   }

   public void paintComponent(Graphics g)
   {
     super.paintComponent(g);
     int numCol = tabuleiro.getCol();
     Color COR_VAZIO = Color.white;
     Color COR_OCUPADO = Color.blue;
     Color COR_LINHA = Color.gray;
     Color COR_CIRCULO = Color.black;

     int numLin= tabuleiro.getLin();
     int width =  getWidth();
     int height = getHeight();
     largura =(double) width / (double) numCol; 
     altura = (double) height / (double) numLin;
     g.setColor(COR_CIRCULO);
     
     for(int i=0; i<numCol; i++)
        for(int j=0; j<numLin; j++)
         {
            if (tabuleiro.getSituacao(i,j)==Vida.VAZIO)
               g.setColor(COR_VAZIO);
            else
               g.setColor(COR_OCUPADO);
   
               //g.setColor(Color.BLUE);
            g.fillRect( (int) (i*largura), (int) (j*altura), (int) (largura), (int) (altura) );

         }
     g.setColor(COR_LINHA);
    for(int i=0; i<=numCol; i++)
       g.drawLine( (int) (i*largura),0 ,(int)(i*largura),(int) (numLin*altura));
     for(int i=0; i<=numLin; i++)
      g.drawLine(0 , (int) (i*altura)  ,(int) (numCol*largura)  ,(int) (i*altura) );

     g.drawOval(0,0,40,40);
     g.drawLine( 20,20,(int)   (20 + 20* Math.cos(-angulo+Math.PI/2)     ),(int) (20 - 20* Math.sin(-angulo+Math.PI/2)     ));
   }



    public void mouseClicked(MouseEvent e)
    { 
     
      if (e.getButton()==MouseEvent.BUTTON1) 
      {   
        n =  (int)( (double) e.getX() / largura);
        m = (int) ((double) e.getY() / altura);
        tabuleiro.setTrocar(n,m);         
      };
      if (e.getButton()==MouseEvent.BUTTON3) 
      {   
//        tabuleiro.executarUmPasso();   
//        temporizador = new Timer();
//        temporizador.schedule(new UmPasso(),0,SALTOMS);
      }; 
      this.repaint();
      
    }
     
    public void mouseEntered(MouseEvent e) {}
 
    public void mouseExited(MouseEvent e) {}
 
    public void mousePressed(MouseEvent e) {}
 
    public void mouseReleased(MouseEvent e) {}
     

  
 


     class UmPasso extends TimerTask 
     {
            public void run() 
            {
              tabuleiro.executarUmPasso();
              angulo=angulo+Math.PI/30;
              if (angulo>2*Math.PI) angulo=0;
              repaint();
            }
      }
  
     public void actionPerformed(ActionEvent e)
     {
        if ( e.getActionCommand().equals(MNU.INICIAR) ) 
        {
            if (temporizador==null)
            {   
               temporizador = new Timer();
               temporizador.schedule(new UmPasso(),0,SALTOMS);
            }
        }
        
        if ( e.getActionCommand().equals(MNU.PARAR) ) 
        {
          if (temporizador!=null) 
          {
               temporizador.cancel();
               temporizador= null;
          }
        } 
        
        if ( e.getActionCommand().equals(MNU.LIMPAR) ) 
        {
          
          if (temporizador!=null) 
          {  
             temporizador.cancel();
             temporizador = null;
          }
          angulo=0;
          tabuleiro.clear();
          this.repaint();
        } 
        if ( e.getActionCommand().equals(MNU.SAIR) ) 
        {
          System.exit(0);
        } 

     } /*end of actionPerformed*/
    


}


//http://www.dsc.ufcg.edu.br/~jacques/cursos/map/html/threads/timer.html




