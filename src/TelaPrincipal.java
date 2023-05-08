import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal extends JFrame implements ActionListener{
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JTabbedPane painelAbas;
    private JPanel painelTopo;
    private PainelMapa painelDesenho;
    private JTextArea textArea;
    private JPanel painelRodape;
    private JLabel labelVersao;
    private JPanel painelMensagens;
    private JPanel painelMensagens2;
    private JLabel labelMensagens;
    private ActionListener listenerAcoes;
    private LeitorArquivoMapa arquivoDoMapa;
    private Mapa mapa;
    public TelaPrincipal() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("App Grafica");
        setLayout(new BorderLayout());

        construirMenu();
        construirPainelDesenho();
        construirRodape();

        painelTopo = new JPanel();
        add(painelTopo, BorderLayout.NORTH);

        construirPainelMensagens();
        construirToolBar();

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("sair")) {
            System.exit(0);
        }
        else if(e.getActionCommand().equals("abrir")) {
            JFileChooser seletorDeArquivo = new JFileChooser();
            int opcao = seletorDeArquivo.showOpenDialog(this);
            if(opcao==JFileChooser.APPROVE_OPTION) {
                File arquivoSelecionado = seletorDeArquivo.getSelectedFile();
                lerArquivoMapa(arquivoSelecionado);
            }
            else {
                labelMensagens.setText("abrir arquivo cancelado");
            }
        }
        else if(e.getActionCommand().equals("NAVEGAR")) {
            navegar();
            repaint();
        }
    }

    private void lerArquivoMapa(File arquivoSelecionado) {
        arquivoDoMapa = new LeitorArquivoMapa();
        if(!arquivoDoMapa.ler(arquivoSelecionado.getAbsolutePath())) {
            labelMensagens.setText("Erro ao ler arquivo");
            return;
        }
        mapa = new Mapa();
        mapa.setLinhas(arquivoDoMapa.getNumeroLinhas());
        mapa.setColunas(arquivoDoMapa.getNumeroColunas());
        mapa.setMapaDeChar(arquivoDoMapa.getMapaEmChar());
        labelMensagens.setText("Mapa carregado com " + this.mapa.getLinhas() + " linhas e " + mapa.getColunas() + " colunas.");
        painelDesenho.setMapa(mapa);
        painelDesenho.repaint();

        MapaGrafo mg = new MapaGrafo(mapa);
        mapa.setMapaGrafo(mg);
    }
    private void construirMenu() {
        menuBar = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem menuItemAbrir = new JMenuItem("Abrir");
        JMenuItem menuItemSair = new JMenuItem("Sair");
        menuItemAbrir.setActionCommand("abrir");
        menuItemAbrir.addActionListener(this);

        menuItemSair.setActionCommand("sair");
        menuItemSair.addActionListener(this);

        menuArquivo.add(menuItemAbrir);
        menuArquivo.add(menuItemSair);

        menuArquivo.addActionListener(this);
        menuBar.add(menuArquivo);
        this.setJMenuBar(menuBar);
    }
    private void construirRodape() {
        painelRodape = new JPanel();
        painelRodape.setBackground(Color.LIGHT_GRAY);
        painelRodape.setPreferredSize(new Dimension(getWidth(), 30));
        JPanel painelRodapeEsquerdo = new JPanel();
        painelRodapeEsquerdo.setBackground(Color.LIGHT_GRAY);
        JPanel painelRodapeDireito = new JPanel();
        painelRodapeDireito.setBackground(Color.LIGHT_GRAY);
        labelVersao = new JLabel("Versão 1.0.0");
        JLabel labelAutor = new JLabel("PUCRS - Escola Politécnica");
        painelRodapeEsquerdo.add(labelAutor);
        painelRodapeDireito.add(labelVersao);
        painelRodape.add(painelRodapeEsquerdo, BorderLayout.CENTER);
        painelRodape.add(painelRodapeDireito, BorderLayout.CENTER);
        this.add(painelRodape, BorderLayout.SOUTH);

    }
    private void construirPainelDesenho() {
        painelDesenho = new PainelMapa();
        painelAbas =  new JTabbedPane();
        painelAbas.addTab("Mapa", null, painelDesenho,"");
        this.add(painelAbas, BorderLayout.CENTER);

        painelMensagens2 =  new JPanel();
        painelMensagens2.setBackground(Color.WHITE);
        textArea = new JTextArea();
        textArea.setRows(100);
        painelMensagens2.add(textArea, BorderLayout.NORTH);
        painelAbas.addTab("Diário de Bordo", null, painelMensagens2,"");
    }
    private void construirToolBar() {
        toolBar = new JToolBar();
        JButton botao1 = new JButton("Iniciar navegação");
        botao1.addActionListener(this);
        botao1.setActionCommand("NAVEGAR");
        toolBar.add(botao1);
        this.painelTopo.add(toolBar,BorderLayout.WEST);
    }
    private void construirPainelMensagens() {
        painelMensagens = new JPanel();
        labelMensagens = new JLabel();
        labelMensagens.setText("Bem-vindo ao app");
        painelMensagens.add(labelMensagens);
        this.painelTopo.add(painelMensagens, BorderLayout.SOUTH);
    }

    private void navegar() {
        System.out.println("Navegacao iniciada");
        textArea.setText("");
        MapaGrafo mg = this.mapa.getMapaGrafo();
        int NUMERO_PORTOS = 10;
        int[] verticesPortos = new int[NUMERO_PORTOS+1];
        for (int i = 0; i < NUMERO_PORTOS+1; i++) verticesPortos[i] = -1;

        for (int i = 1; i < NUMERO_PORTOS+1; i++) {
            verticesPortos[i] = mg.getIndiceVerticePorto(i);
        }


        int portoAtual = 1;
        int portoSeguinte = 2;
        ArrayList<MapaCelula> caminhoCompleto = new ArrayList<>();
        ArrayList<ArrayList<MapaCelula>> caminhos = new ArrayList<>();

        while(true) {
            ArrayList<MapaCelula> trecho = mg.navegarEmLargura(verticesPortos[portoAtual], verticesPortos[portoSeguinte]);
            textArea.append("Porto " + portoAtual + " para " + portoSeguinte + " --> ");
            if(trecho==null) {
                textArea.append("bloqueado");
                portoSeguinte++;
            }
            else {
                textArea.append(String.valueOf(trecho.size()));
                caminhoCompleto.addAll(trecho);

                caminhos.add(trecho);

                portoAtual = portoSeguinte;
                portoSeguinte++;
            }
            textArea.append(System.lineSeparator());
            if(verticesPortos[portoSeguinte]==-1) break;
        }
        mapa.setCaminho(caminhoCompleto);
        mapa.setCaminhos(caminhos);
        System.out.println("Navegacao encerrada");

    }

}
