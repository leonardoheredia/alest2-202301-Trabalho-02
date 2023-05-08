import Grafos.BuscaEmLargura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class PainelMapa extends JPanel implements MouseListener {
    private Mapa mapa;
    private double alturaCelula;
    private double larguraCelula;
    public PainelMapa() {
        addMouseListener(this);
    }
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if(mapa==null)  {
            g2.setColor(Color.BLACK);
            g2.drawLine(0,0, getWidth(), getHeight());
            return;
        }

        this.alturaCelula = (double) getHeight() / (double) mapa.getLinhas();
        this.larguraCelula = (double) getWidth() / (double) mapa.getColunas();

        g2.setColor(Color.BLACK);
        for (int lin = 0; lin <mapa.getLinhas() ;lin++) {
            for (int col = 0; col < mapa.getColunas(); col++) {
                if(mapa.getMapaDeChar()[lin][col]=='.') {
                    g2.setColor(Color.BLUE);
                    g2.fill(new Rectangle2D.Double(col*larguraCelula, lin*alturaCelula, larguraCelula, alturaCelula));
                }
                else if(mapa.getMapaDeChar()[lin][col]=='*') {
                    g2.setColor(Color.GRAY);
                    g2.fill(new Rectangle2D.Double(col*larguraCelula, lin*alturaCelula, larguraCelula, alturaCelula));
                }
                else {
                    Font f = new Font(Font.DIALOG, Font.BOLD, 12);
                    g2.setColor(Color.WHITE);
                    g2.fill(new Rectangle2D.Double(col*larguraCelula, lin*alturaCelula, larguraCelula, alturaCelula));
                    g2.setColor(Color.BLACK);
                    g2.setFont(f);
                    g2.drawString(String.valueOf(mapa.getMapaDeChar()[lin][col]),
                            (float)(col*larguraCelula+(larguraCelula/2)-2),
                            (float)(lin*alturaCelula+(alturaCelula/2)) );
                }
            }
        }

        //desenhar caminho se houver

        if(!mapa.getCaminhos().isEmpty()) {
            for (int i = 0; i < mapa.getCaminhos().size(); i++) {
                ArrayList<MapaCelula> caminho = mapa.getCaminhos().get(i);

                int alterna = 0;
                for (MapaCelula celula : caminho) {
                    if (mapa.getMapaDeChar()[celula.linha][celula.coluna] == '.') {
                        if(alterna%2==0) g2.setColor(Color.RED);
                        else g2.setColor(Color.GREEN);
                        g2.fill(new Rectangle2D.Double(celula.coluna * larguraCelula, celula.linha * alturaCelula, larguraCelula, alturaCelula));
                        alterna++;
                    }
                }
            }
        }

        /*if(!(mapa.getCaminho().isEmpty())) {
            for(MapaCelula celula:mapa.getCaminho()) {
                if(mapa.getMapaDeChar()[celula.linha][celula.coluna]=='.') {
                    g2.setColor(Color.RED);
                    g2.fill(new Rectangle2D.Double(celula.coluna*larguraCelula, celula.linha*alturaCelula, larguraCelula, alturaCelula));
                }
            }
        }*/
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + " - " + e.getY());
        int linha, coluna;
        linha = (int) (e.getY() / alturaCelula);
        coluna = (int)  (e.getX() / larguraCelula);
        System.out.println("celula(" + linha + "," + coluna + ")");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
