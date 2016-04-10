/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import logica.InteligenciaArtificial;
//import logica.InteligenciaArtificial;


/**
 *
 * @author MAR
 */
public class TableroDelJuego extends javax.swing.JFrame {
protected logica.TresEnRaya3D juego;
    protected int numJugadores = 1; //1: Un Jugador   (Humano vs Máquina)
                                    //2: Dos Jugadores (Humano vs Humano)
    protected HashMap<String,JButton> hashMapTablero = new HashMap(); //Servirá para identificar los botones del tablero.
    /**
     * Creates new form TableroDelJuego
     */
    public TableroDelJuego() {
        initComponents();
        this.setLocationRelativeTo(null); //Centra la ventana.
        
        configurarBotonesTablero();
        limpiarJuego();
        configurarSliderNivel();
        
        this.juego = new logica.TresEnRaya3D();
        
        this.setTitle("3 En Raya 3D");
        this.rb1.setSelected(true);     //1 Jugador por defecto
        
        this.btnNuevoJuego.setVisible(false);
        this.btnSiguientePartida.setVisible(false);
        this.lblTurnoJugador.setVisible(false);
        
        bloquearTablero();
    }

    /**
     * Asignando el Listener a los botones del juego.
     */
    private void configurarBotonesTablero() {
        
        ListenerButton lButton = new ListenerButton();
        btn1A.addActionListener(lButton);
        btn2A.addActionListener(lButton);
        btn22A.addActionListener(lButton);
        btn3A.addActionListener(lButton);
        btn33A.addActionListener(lButton);
        btn333A.addActionListener(lButton);
        btn4A.addActionListener(lButton);
        btn44A.addActionListener(lButton);
        btn5A.addActionListener(lButton);
        btn1B.addActionListener(lButton);
        btn2B.addActionListener(lButton);
        btn22B.addActionListener(lButton);
        btn3B.addActionListener(lButton);
        btn33B.addActionListener(lButton);
        btn333B.addActionListener(lButton);
        btn4B.addActionListener(lButton);
        btn44B.addActionListener(lButton);
        btn5B.addActionListener(lButton);
        btn1C.addActionListener(lButton);
        btn2C.addActionListener(lButton);
        btn22C.addActionListener(lButton);
        btn3C.addActionListener(lButton);
        btn33C.addActionListener(lButton);
        btn333C.addActionListener(lButton);
        btn4C.addActionListener(lButton);
        btn44C.addActionListener(lButton);
        btn5C.addActionListener(lButton);
        
        this.hashMapTablero.put("020", btn1A);
        this.hashMapTablero.put("010", btn2A);
        this.hashMapTablero.put("021", btn22A);
        this.hashMapTablero.put("000", btn3A);
        this.hashMapTablero.put("011", btn33A);
        this.hashMapTablero.put("022", btn333A);
        this.hashMapTablero.put("001", btn4A);
        this.hashMapTablero.put("012", btn44A);
        this.hashMapTablero.put("002", btn5A);
        this.hashMapTablero.put("120", btn1B);
        this.hashMapTablero.put("110", btn2B);
        this.hashMapTablero.put("121", btn22B);
        this.hashMapTablero.put("100", btn3B);
        this.hashMapTablero.put("111", btn33B);
        this.hashMapTablero.put("122", btn333B);
        this.hashMapTablero.put("101", btn4B);
        this.hashMapTablero.put("112", btn44B);
        this.hashMapTablero.put("102", btn5B);
        this.hashMapTablero.put("220", btn1C);
        this.hashMapTablero.put("210", btn2C);
        this.hashMapTablero.put("221", btn22C);
        this.hashMapTablero.put("200", btn3C);
        this.hashMapTablero.put("211", btn33C);
        this.hashMapTablero.put("222", btn333C);
        this.hashMapTablero.put("201", btn4C);
        this.hashMapTablero.put("212", btn44C);
        this.hashMapTablero.put("202", btn5C);

        // -- -- --
    }

    private void configurarSliderNivel() {
        sliderNivel.setPaintLabels(true);
        Hashtable<Integer, JLabel> etiquetas = new Hashtable<Integer, JLabel>();
        etiquetas.put(3, new JLabel("Facil"));
        etiquetas.put(2, new JLabel("Normal"));
        etiquetas.put(1, new JLabel("Experto"));
        
        sliderNivel.setLabelTable(etiquetas);
    }
    
    public boolean hayGanador() {
        switch (juego.getGanador()) {
            case 1:
                juegoTerminado("Jugador 1 gana la partida!");
                lblPartidasGanadasJ1.setText("Jugador 1: " + juego.getJuegosGanadosJ1());
                return true;
            case 2:
                juegoTerminado("Jugador 2 gana la partida!");
                lblPartidasGanadasJ2.setText("Jugador 2: " + juego.getJuegosGanadosJ2());
                return true;
            case 3:
                juegoTerminado("Es un empate!");
                lblPartidasEmpatadas.setText("Empates: " + juego.getJuegosEmpatados());
                return true;
        }
        return false;
    }
    
    public void juegoTerminado(String resultado) {
        JOptionPane.showMessageDialog(null, resultado);
        if(juego.getGanador() != 0) { //Si ya hay un ganador se bloquea el tablero.
            bloquearTablero();
        }
        btnSiguientePartida.setVisible(true);

   }
    
    private void turnoDeMaquina() {
        if(numJugadores == 1){
            int[] pos = (new InteligenciaArtificial()).buscarMovimiento(juego.getTablero(),sliderNivel.getValue());
            String posicion = pos[0] + "" +  pos[1] + "" +  pos[2];
            String i = hashMapTablero.get(posicion).getName(); //Identificador del boton para escoger la imagen.
            
            if (juego.casillaLibre(pos[0],pos[1],pos[2])) {
                juego.ingresarValor(pos[0],pos[1],pos[2]);
                cambiarIcono(hashMapTablero.get(posicion), "jugador2/", "ball_" + i + "_default.png", "ball_" + i + "_hover.png", "ball_" + i + "_pressed.png");
                juego.cambioDeTurno();
            }
        }
    }
    
    private void bloquearTablero() {
        for(Component c : jpJuego.getComponents()){ // Bloqueando Juego
            c.setEnabled(false);
        }
    }

    private void habilitarTablero() {
        for(Component c : jpJuego.getComponents()){ // Bloqueando Juego
            c.setEnabled(true);
        }
    }
    
    private void limpiarJuego(){
        this.bloquearTablero();
        
        this.btnJugar.setVisible(true);
        
        // Reiniciando tablero.
        this.btn1A.setText("");this.btn2A.setText("");this.btn22A.setText("");
        this.btn3A.setText("");this.btn33A.setText("");this.btn333A.setText("");
        this.btn4A.setText("");this.btn44A.setText("");this.btn5A.setText("");
        
        this.btn1B.setText("");this.btn2B.setText("");this.btn22B.setText("");
        this.btn3B.setText("");this.btn33B.setText("");this.btn333B.setText("");
        this.btn4B.setText("");this.btn44B.setText("");this.btn5B.setText("");
        
        this.btn1C.setText("");this.btn2C.setText("");this.btn22C.setText("");
        this.btn3C.setText("");this.btn33C.setText("");this.btn333C.setText("");
        this.btn4C.setText("");this.btn44C.setText("");this.btn5C.setText("");
        
        cambiarIcono(btn3A, "empty/", "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png");
        cambiarIcono(btn4A, "empty/", "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png");
        cambiarIcono(btn5A, "empty/", "ball_5_default.png", "ball_5_hover.png", "ball_5_pressed.png");
        cambiarIcono(btn2A, "empty/", "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png");
        cambiarIcono(btn33A, "empty/", "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png");
        cambiarIcono(btn44A, "empty/", "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png");
        cambiarIcono(btn1A, "empty/", "ball_1_default.png", "ball_1_hover.png", "ball_1_pressed.png");
        cambiarIcono(btn22A, "empty/", "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png");
        cambiarIcono(btn333A, "empty/", "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png");
        cambiarIcono(btn3B, "empty/", "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png");
        cambiarIcono(btn4B, "empty/", "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png");
        cambiarIcono(btn5B, "empty/", "ball_5_default.png", "ball_5_hover.png", "ball_5_pressed.png");
        cambiarIcono(btn2B, "empty/", "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png");
        cambiarIcono(btn33B, "empty/", "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png");
        cambiarIcono(btn44B, "empty/", "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png");
        cambiarIcono(btn1B, "empty/", "ball_1_default.png", "ball_1_hover.png", "ball_1_pressed.png");
        cambiarIcono(btn22B, "empty/", "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png");
        cambiarIcono(btn333B, "empty/", "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png");
        cambiarIcono(btn3C, "empty/", "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png");
        cambiarIcono(btn4C, "empty/", "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png");
        cambiarIcono(btn5C, "empty/", "ball_5_default.png", "ball_5_hover.png", "ball_5_pressed.png");
        cambiarIcono(btn2C, "empty/", "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png");
        cambiarIcono(btn33C, "empty/", "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png");
        cambiarIcono(btn44C, "empty/", "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png");
        cambiarIcono(btn1C, "empty/", "ball_1_default.png", "ball_1_hover.png", "ball_1_pressed.png");
        cambiarIcono(btn22C, "empty/", "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png");
        cambiarIcono(btn333C, "empty/", "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png");
    }
    
    private void cambiarIcono(JButton btn,String carpeta, String def ,String rollover, String pressed){
        btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/" + carpeta + def)));
        btn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/" + carpeta + rollover)));
        btn.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/"+ carpeta + pressed)));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jpConfiguracion = new javax.swing.JPanel();
        lblNivel = new javax.swing.JLabel();
        rb1 = new javax.swing.JRadioButton();
        rb2 = new javax.swing.JRadioButton();
        btnJugar = new javax.swing.JButton();
        sliderNivel = new javax.swing.JSlider();
        jpPartidasGanadas = new javax.swing.JPanel();
        lblPartidasGanadas = new javax.swing.JLabel();
        btnNuevoJuego = new javax.swing.JButton();
        lblPartidasGanadasJ1 = new javax.swing.JLabel();
        lblPartidasGanadasJ2 = new javax.swing.JLabel();
        lblPartidasEmpatadas = new javax.swing.JLabel();
        btnSiguientePartida = new javax.swing.JButton();
        lblTurnoJugador = new javax.swing.JLabel();
        jpJuego = new javax.swing.JPanel();
        btn22C = new javax.swing.JButton();
        btn2C = new javax.swing.JButton();
        btn44C = new javax.swing.JButton();
        btn4C = new javax.swing.JButton();
        btn5C = new javax.swing.JButton();
        btn33C = new javax.swing.JButton();
        btn3C = new javax.swing.JButton();
        btn333C = new javax.swing.JButton();
        btn3A = new javax.swing.JButton();
        btn333A = new javax.swing.JButton();
        btn1B = new javax.swing.JButton();
        btn22B = new javax.swing.JButton();
        btn2B = new javax.swing.JButton();
        btn44B = new javax.swing.JButton();
        btn4B = new javax.swing.JButton();
        btn5B = new javax.swing.JButton();
        btn33B = new javax.swing.JButton();
        btn3B = new javax.swing.JButton();
        btn22A = new javax.swing.JButton();
        btn1A = new javax.swing.JButton();
        btn2A = new javax.swing.JButton();
        btn44A = new javax.swing.JButton();
        btn4A = new javax.swing.JButton();
        btn33A = new javax.swing.JButton();
        btn5A = new javax.swing.JButton();
        btn333B = new javax.swing.JButton();
        btn1C = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(836, 763));
        setPreferredSize(new java.awt.Dimension(836, 763));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Futura", 1, 27)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Tres en Raya 3D");
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 304, 45));

        jpConfiguracion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNivel.setFont(new java.awt.Font("Futura", 1, 18)); // NOI18N
        lblNivel.setText("Nivel del Juego:");
        jpConfiguracion.add(lblNivel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 204, -1));

        rb1.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        rb1.setText("1 Jugador");
        rb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb1ActionPerformed(evt);
            }
        });
        jpConfiguracion.add(rb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, -1, -1));

        rb2.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        rb2.setText("2 Jugadores");
        rb2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb2ActionPerformed(evt);
            }
        });
        jpConfiguracion.add(rb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, -1, -1));

        btnJugar.setFont(new java.awt.Font("Futura", 0, 15)); // NOI18N
        btnJugar.setText("Jugar!");
        btnJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJugarActionPerformed(evt);
            }
        });
        jpConfiguracion.add(btnJugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 136, 40));

        sliderNivel.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        sliderNivel.setMaximum(3);
        sliderNivel.setMinimum(1);
        sliderNivel.setOrientation(javax.swing.JSlider.VERTICAL);
        sliderNivel.setPaintTicks(true);
        sliderNivel.setValue(2);
        sliderNivel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sliderNivel.setFocusable(false);
        jpConfiguracion.add(sliderNivel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 130, 100));

        getContentPane().add(jpConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 107, 420, 150));

        lblPartidasGanadas.setFont(new java.awt.Font("Futura", 1, 18)); // NOI18N
        lblPartidasGanadas.setText("Partidas Ganadas:");

        btnNuevoJuego.setFont(new java.awt.Font("Futura", 0, 15)); // NOI18N
        btnNuevoJuego.setText("Nuevo Juego");
        btnNuevoJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoJuegoActionPerformed(evt);
            }
        });

        lblPartidasGanadasJ1.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        lblPartidasGanadasJ1.setText("Jugador 1: 0");

        lblPartidasGanadasJ2.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        lblPartidasGanadasJ2.setText("Jugador 2: 0");

        lblPartidasEmpatadas.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        lblPartidasEmpatadas.setText("Empates: 0");

        btnSiguientePartida.setFont(new java.awt.Font("Futura", 0, 15)); // NOI18N
        btnSiguientePartida.setText("Siguiente Partida");
        btnSiguientePartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguientePartidaActionPerformed(evt);
            }
        });

        lblTurnoJugador.setBackground(new java.awt.Color(204, 255, 51));
        lblTurnoJugador.setFont(new java.awt.Font("Futura", 2, 24)); // NOI18N
        lblTurnoJugador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTurnoJugador.setText("Turno Jugador 1");

        javax.swing.GroupLayout jpPartidasGanadasLayout = new javax.swing.GroupLayout(jpPartidasGanadas);
        jpPartidasGanadas.setLayout(jpPartidasGanadasLayout);
        jpPartidasGanadasLayout.setHorizontalGroup(
            jpPartidasGanadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPartidasGanadasLayout.createSequentialGroup()
                .addGroup(jpPartidasGanadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPartidasGanadasLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblPartidasGanadas, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpPartidasGanadasLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jpPartidasGanadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPartidasGanadasJ1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPartidasGanadasJ2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPartidasEmpatadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(77, 77, 77))
            .addGroup(jpPartidasGanadasLayout.createSequentialGroup()
                .addGroup(jpPartidasGanadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPartidasGanadasLayout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addGroup(jpPartidasGanadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNuevoJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSiguientePartida, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpPartidasGanadasLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(lblTurnoJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jpPartidasGanadasLayout.setVerticalGroup(
            jpPartidasGanadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPartidasGanadasLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(lblPartidasGanadas)
                .addGap(18, 18, 18)
                .addGroup(jpPartidasGanadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPartidasGanadasJ1)
                    .addComponent(lblPartidasEmpatadas))
                .addGap(18, 18, 18)
                .addComponent(lblPartidasGanadasJ2)
                .addGap(18, 18, 18)
                .addComponent(btnNuevoJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSiguientePartida, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(lblTurnoJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jpPartidasGanadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 275, -1, -1));

        jpJuego.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn22C.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn22C.setActionCommand("221");
        btn22C.setBorderPainted(false);
        btn22C.setContentAreaFilled(false);
        btn22C.setFocusable(false);
        btn22C.setName("2"); // NOI18N
        btn22C.setPreferredSize(new java.awt.Dimension(64, 64));
        btn22C.setRolloverEnabled(true);
        jpJuego.add(btn22C, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 470, -1, -1));

        btn2C.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn2C.setActionCommand("210");
        btn2C.setBorderPainted(false);
        btn2C.setContentAreaFilled(false);
        btn2C.setFocusable(false);
        btn2C.setName("2"); // NOI18N
        btn2C.setPreferredSize(new java.awt.Dimension(64, 64));
        btn2C.setRolloverEnabled(true);
        jpJuego.add(btn2C, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, -1, -1));

        btn44C.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn44C.setActionCommand("212");
        btn44C.setBorderPainted(false);
        btn44C.setContentAreaFilled(false);
        btn44C.setFocusable(false);
        btn44C.setName("4"); // NOI18N
        btn44C.setPreferredSize(new java.awt.Dimension(64, 64));
        btn44C.setRolloverEnabled(true);
        jpJuego.add(btn44C, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 580, -1, -1));

        btn4C.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn4C.setActionCommand("201");
        btn4C.setBorderPainted(false);
        btn4C.setContentAreaFilled(false);
        btn4C.setFocusable(false);
        btn4C.setName("4"); // NOI18N
        btn4C.setPreferredSize(new java.awt.Dimension(64, 64));
        btn4C.setRolloverEnabled(true);
        jpJuego.add(btn4C, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 580, -1, -1));

        btn5C.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn5C.setActionCommand("202");
        btn5C.setBorderPainted(false);
        btn5C.setContentAreaFilled(false);
        btn5C.setFocusable(false);
        btn5C.setName("5"); // NOI18N
        btn5C.setPreferredSize(new java.awt.Dimension(64, 64));
        btn5C.setRolloverEnabled(true);
        jpJuego.add(btn5C, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 620, -1, -1));

        btn33C.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn33C.setActionCommand("211");
        btn33C.setBorderPainted(false);
        btn33C.setContentAreaFilled(false);
        btn33C.setFocusable(false);
        btn33C.setName("3"); // NOI18N
        btn33C.setPreferredSize(new java.awt.Dimension(64, 64));
        btn33C.setRolloverEnabled(true);
        jpJuego.add(btn33C, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 520, -1, -1));

        btn3C.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn3C.setActionCommand("200");
        btn3C.setBorderPainted(false);
        btn3C.setContentAreaFilled(false);
        btn3C.setFocusable(false);
        btn3C.setName("3"); // NOI18N
        btn3C.setPreferredSize(new java.awt.Dimension(64, 64));
        btn3C.setRolloverEnabled(true);
        jpJuego.add(btn3C, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, -1, -1));

        btn333C.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn333C.setActionCommand("222");
        btn333C.setBorderPainted(false);
        btn333C.setContentAreaFilled(false);
        btn333C.setFocusable(false);
        btn333C.setName("3"); // NOI18N
        btn333C.setPreferredSize(new java.awt.Dimension(64, 64));
        btn333C.setRolloverEnabled(true);
        jpJuego.add(btn333C, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 520, -1, -1));

        btn3A.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn3A.setActionCommand("000");
        btn3A.setBorderPainted(false);
        btn3A.setContentAreaFilled(false);
        btn3A.setFocusable(false);
        btn3A.setName("3"); // NOI18N
        btn3A.setPreferredSize(new java.awt.Dimension(64, 64));
        btn3A.setRolloverEnabled(true);
        jpJuego.add(btn3A, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        btn333A.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn333A.setActionCommand("022");
        btn333A.setBorderPainted(false);
        btn333A.setContentAreaFilled(false);
        btn333A.setFocusable(false);
        btn333A.setName("3"); // NOI18N
        btn333A.setPreferredSize(new java.awt.Dimension(64, 64));
        btn333A.setRolloverEnabled(true);
        jpJuego.add(btn333A, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, -1, -1));

        btn1B.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn1B.setActionCommand("120");
        btn1B.setBorderPainted(false);
        btn1B.setContentAreaFilled(false);
        btn1B.setFocusable(false);
        btn1B.setName("1"); // NOI18N
        btn1B.setRolloverEnabled(true);
        jpJuego.add(btn1B, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 64, 64));

        btn22B.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn22B.setActionCommand("121");
        btn22B.setBorderPainted(false);
        btn22B.setContentAreaFilled(false);
        btn22B.setFocusable(false);
        btn22B.setName("2"); // NOI18N
        btn22B.setPreferredSize(new java.awt.Dimension(64, 64));
        btn22B.setRolloverEnabled(true);
        jpJuego.add(btn22B, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, -1, -1));

        btn2B.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn2B.setActionCommand("110");
        btn2B.setBorderPainted(false);
        btn2B.setContentAreaFilled(false);
        btn2B.setFocusable(false);
        btn2B.setName("2"); // NOI18N
        btn2B.setPreferredSize(new java.awt.Dimension(64, 64));
        btn2B.setRolloverEnabled(true);
        jpJuego.add(btn2B, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, -1, -1));

        btn44B.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn44B.setActionCommand("112");
        btn44B.setBorderPainted(false);
        btn44B.setContentAreaFilled(false);
        btn44B.setFocusable(false);
        btn44B.setName("4"); // NOI18N
        btn44B.setPreferredSize(new java.awt.Dimension(64, 64));
        btn44B.setRolloverEnabled(true);
        jpJuego.add(btn44B, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, -1, -1));

        btn4B.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn4B.setActionCommand("101");
        btn4B.setBorderPainted(false);
        btn4B.setContentAreaFilled(false);
        btn4B.setFocusable(false);
        btn4B.setName("4"); // NOI18N
        btn4B.setPreferredSize(new java.awt.Dimension(64, 64));
        btn4B.setRolloverEnabled(true);
        jpJuego.add(btn4B, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, -1, -1));

        btn5B.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn5B.setActionCommand("102");
        btn5B.setBorderPainted(false);
        btn5B.setContentAreaFilled(false);
        btn5B.setFocusable(false);
        btn5B.setName("5"); // NOI18N
        btn5B.setPreferredSize(new java.awt.Dimension(64, 64));
        btn5B.setRolloverEnabled(true);
        jpJuego.add(btn5B, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, -1, -1));

        btn33B.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn33B.setActionCommand("111");
        btn33B.setBorderPainted(false);
        btn33B.setContentAreaFilled(false);
        btn33B.setFocusable(false);
        btn33B.setName("3"); // NOI18N
        btn33B.setPreferredSize(new java.awt.Dimension(64, 64));
        btn33B.setRolloverEnabled(true);
        jpJuego.add(btn33B, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, -1, -1));

        btn3B.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn3B.setActionCommand("100");
        btn3B.setBorderPainted(false);
        btn3B.setContentAreaFilled(false);
        btn3B.setFocusable(false);
        btn3B.setName("3"); // NOI18N
        btn3B.setPreferredSize(new java.awt.Dimension(64, 64));
        btn3B.setRolloverEnabled(true);
        jpJuego.add(btn3B, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        btn22A.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn22A.setActionCommand("021");
        btn22A.setBorderPainted(false);
        btn22A.setContentAreaFilled(false);
        btn22A.setFocusable(false);
        btn22A.setName("2"); // NOI18N
        btn22A.setPreferredSize(new java.awt.Dimension(64, 64));
        btn22A.setRolloverEnabled(true);
        jpJuego.add(btn22A, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, -1, -1));

        btn1A.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn1A.setActionCommand("020");
        btn1A.setBorderPainted(false);
        btn1A.setContentAreaFilled(false);
        btn1A.setFocusable(false);
        btn1A.setName("1"); // NOI18N
        btn1A.setRolloverEnabled(true);
        jpJuego.add(btn1A, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 64, 64));

        btn2A.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn2A.setActionCommand("010");
        btn2A.setBorderPainted(false);
        btn2A.setContentAreaFilled(false);
        btn2A.setFocusable(false);
        btn2A.setName("2"); // NOI18N
        btn2A.setPreferredSize(new java.awt.Dimension(64, 64));
        btn2A.setRolloverEnabled(true);
        jpJuego.add(btn2A, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));

        btn44A.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn44A.setActionCommand("012");
        btn44A.setBorderPainted(false);
        btn44A.setContentAreaFilled(false);
        btn44A.setFocusable(false);
        btn44A.setName("4"); // NOI18N
        btn44A.setPreferredSize(new java.awt.Dimension(64, 64));
        btn44A.setRolloverEnabled(true);
        jpJuego.add(btn44A, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, -1, -1));

        btn4A.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn4A.setActionCommand("001");
        btn4A.setBorderPainted(false);
        btn4A.setContentAreaFilled(false);
        btn4A.setFocusable(false);
        btn4A.setName("4"); // NOI18N
        btn4A.setPreferredSize(new java.awt.Dimension(64, 64));
        btn4A.setRolloverEnabled(true);
        jpJuego.add(btn4A, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, -1));

        btn33A.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn33A.setActionCommand("011");
        btn33A.setBorderPainted(false);
        btn33A.setContentAreaFilled(false);
        btn33A.setFocusable(false);
        btn33A.setName("3"); // NOI18N
        btn33A.setPreferredSize(new java.awt.Dimension(64, 64));
        btn33A.setRolloverEnabled(true);
        jpJuego.add(btn33A, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        btn5A.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn5A.setActionCommand("002");
        btn5A.setBorderPainted(false);
        btn5A.setContentAreaFilled(false);
        btn5A.setFocusable(false);
        btn5A.setName("5"); // NOI18N
        btn5A.setPreferredSize(new java.awt.Dimension(64, 64));
        btn5A.setRolloverEnabled(true);
        jpJuego.add(btn5A, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, -1, -1));

        btn333B.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn333B.setActionCommand("122");
        btn333B.setBorderPainted(false);
        btn333B.setContentAreaFilled(false);
        btn333B.setFocusable(false);
        btn333B.setName("3"); // NOI18N
        btn333B.setPreferredSize(new java.awt.Dimension(64, 64));
        btn333B.setRolloverEnabled(true);
        jpJuego.add(btn333B, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, -1, -1));

        btn1C.setFont(new java.awt.Font("Futura", 1, 15)); // NOI18N
        btn1C.setActionCommand("220");
        btn1C.setBorderPainted(false);
        btn1C.setContentAreaFilled(false);
        btn1C.setFocusable(false);
        btn1C.setName("1"); // NOI18N
        btn1C.setRolloverEnabled(true);
        jpJuego.add(btn1C, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, 64, 64));

        getContentPane().add(jpJuego, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 330, 690));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb1ActionPerformed
        this.numJugadores = 1;
        this.rb1.setSelected(true);
        this.rb2.setSelected(false);
        this.btnJugar.setEnabled(true);
    }//GEN-LAST:event_rb1ActionPerformed

    private void rb2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb2ActionPerformed
        this.numJugadores = 2;
        this.rb2.setSelected(true);
        this.rb1.setSelected(false);
        this.btnJugar.setEnabled(true);
    }//GEN-LAST:event_rb2ActionPerformed

    private void btnJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJugarActionPerformed
        this.btnJugar.setVisible(false);
        this.btnNuevoJuego.setVisible(true);
        this.sliderNivel.setEnabled(false);
        this.rb1.setEnabled(false);
        this.rb2.setEnabled(false);
        if(numJugadores == 2) this.lblTurnoJugador.setVisible(true);
        this.habilitarTablero();

        ArrayList<int[]> casillas = new ArrayList();

    }//GEN-LAST:event_btnJugarActionPerformed

    private void btnNuevoJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoJuegoActionPerformed
        int confirmado = JOptionPane.showConfirmDialog(null, "¿Comenzar un juego nuevo?");

        if (JOptionPane.OK_OPTION == confirmado) {
            this.juego.nuevoJuego();

            this.btnNuevoJuego.setVisible(false);
            this.btnSiguientePartida.setVisible(false);
            this.rb1.setEnabled(true);
            this.rb2.setEnabled(true);
            this.lblPartidasGanadasJ1.setText("Jugador 1: 0");
            this.lblPartidasGanadasJ1.setText("Jugador 1: 0");
            this.lblPartidasEmpatadas.setText("Empates: 0");

            this.limpiarJuego();
            this.sliderNivel.setEnabled(true);
        }
    }//GEN-LAST:event_btnNuevoJuegoActionPerformed

    private void btnSiguientePartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguientePartidaActionPerformed
        int ganador = juego.getGanador();

        this.limpiarJuego();
        this.habilitarTablero();
        this.btnSiguientePartida.setVisible(false);
        this.btnJugar.setVisible(false);
        this.juego.siguienteJuego();

        //Validando que la máquina haya ganado la partida pasada.
        if(numJugadores == 1 && ganador == 2){
            this.turnoDeMaquina();
        }
    }//GEN-LAST:event_btnSiguientePartidaActionPerformed

    /**
     * Se encarga de manejar los botones del tablero.
     */
    class ListenerButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(juego.getGanador() == 0){
                String posicion = e.getActionCommand();
                
                int x = Integer.valueOf(posicion.substring(0,1)),
                    y = Integer.valueOf(posicion.substring(1,2)),
                    z = Integer.valueOf(posicion.substring(2));
                
                if(juego.casillaLibre(x, y, z)){
                    // La variable jugador servira para determinar en que carpeta se encuentra el icono deseado.
                    String jugador = (juego.getTurno() == 1)? "jugador1/":"jugador2/";
                    switch (posicion)
                    {
                        case "000": juego.ingresarValor(x,y,z); cambiarIcono(btn3A, jugador, "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png"); break;
                        case "001": juego.ingresarValor(x,y,z); cambiarIcono(btn4A, jugador, "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png"); break;
                        case "002": juego.ingresarValor(x,y,z); cambiarIcono(btn5A, jugador, "ball_5_default.png", "ball_5_hover.png", "ball_5_pressed.png"); break;
                        case "010": juego.ingresarValor(x,y,z); cambiarIcono(btn2A, jugador, "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png"); break;
                        case "011": juego.ingresarValor(x,y,z); cambiarIcono(btn33A, jugador, "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png"); break;
                        case "012": juego.ingresarValor(x,y,z); cambiarIcono(btn44A, jugador, "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png"); break;
                        case "020": juego.ingresarValor(x,y,z); cambiarIcono(btn1A, jugador, "ball_1_default.png", "ball_1_hover.png", "ball_1_pressed.png"); break;
                        case "021": juego.ingresarValor(x,y,z); cambiarIcono(btn22A, jugador, "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png"); break;
                        case "022": juego.ingresarValor(x,y,z); cambiarIcono(btn333A, jugador, "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png"); break;
                        case "100": juego.ingresarValor(x,y,z); cambiarIcono(btn3B, jugador, "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png"); break;
                        case "101": juego.ingresarValor(x,y,z); cambiarIcono(btn4B, jugador, "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png"); break;
                        case "102": juego.ingresarValor(x,y,z); cambiarIcono(btn5B, jugador, "ball_5_default.png", "ball_5_hover.png", "ball_5_pressed.png"); break;
                        case "110": juego.ingresarValor(x,y,z); cambiarIcono(btn2B, jugador, "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png"); break;
                        case "111": juego.ingresarValor(x,y,z); cambiarIcono(btn33B, jugador, "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png"); break;
                        case "112": juego.ingresarValor(x,y,z); cambiarIcono(btn44B, jugador, "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png"); break;
                        case "120": juego.ingresarValor(x,y,z); cambiarIcono(btn1B, jugador, "ball_1_default.png", "ball_1_hover.png", "ball_1_pressed.png"); break;
                        case "121": juego.ingresarValor(x,y,z); cambiarIcono(btn22B, jugador, "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png"); break;
                        case "122": juego.ingresarValor(x,y,z); cambiarIcono(btn333B, jugador, "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png"); break;
                        case "200": juego.ingresarValor(x,y,z); cambiarIcono(btn3C, jugador, "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png"); break;
                        case "201": juego.ingresarValor(x,y,z); cambiarIcono(btn4C, jugador, "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png"); break;
                        case "202": juego.ingresarValor(x,y,z); cambiarIcono(btn5C, jugador, "ball_5_default.png", "ball_5_hover.png", "ball_5_pressed.png"); break;
                        case "210": juego.ingresarValor(x,y,z); cambiarIcono(btn2C, jugador, "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png"); break;
                        case "211": juego.ingresarValor(x,y,z); cambiarIcono(btn33C, jugador, "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png"); break;
                        case "212": juego.ingresarValor(x,y,z); cambiarIcono(btn44C, jugador, "ball_4_default.png", "ball_4_hover.png", "ball_4_pressed.png"); break;
                        case "220": juego.ingresarValor(x,y,z); cambiarIcono(btn1C, jugador, "ball_1_default.png", "ball_1_hover.png", "ball_1_pressed.png"); break;
                        case "221": juego.ingresarValor(x,y,z); cambiarIcono(btn22C, jugador, "ball_2_default.png", "ball_2_hover.png", "ball_2_pressed.png"); break;
                        case "222": juego.ingresarValor(x,y,z); cambiarIcono(btn333C, jugador, "ball_3_default.png", "ball_3_hover.png", "ball_3_pressed.png"); break;   
                    }   

                    // Cambio de turno
                    juego.cambioDeTurno();
                    lblTurnoJugador.setText("Turno Jugador " + juego.getTurno());

                    if(juego.getGanador() == 0)
                        turnoDeMaquina();
                }
            } // if
            
            hayGanador();
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn1A;
    private javax.swing.JButton btn1B;
    private javax.swing.JButton btn1C;
    private javax.swing.JButton btn22A;
    private javax.swing.JButton btn22B;
    private javax.swing.JButton btn22C;
    private javax.swing.JButton btn2A;
    private javax.swing.JButton btn2B;
    private javax.swing.JButton btn2C;
    private javax.swing.JButton btn333A;
    private javax.swing.JButton btn333B;
    private javax.swing.JButton btn333C;
    private javax.swing.JButton btn33A;
    private javax.swing.JButton btn33B;
    private javax.swing.JButton btn33C;
    private javax.swing.JButton btn3A;
    private javax.swing.JButton btn3B;
    private javax.swing.JButton btn3C;
    private javax.swing.JButton btn44A;
    private javax.swing.JButton btn44B;
    private javax.swing.JButton btn44C;
    private javax.swing.JButton btn4A;
    private javax.swing.JButton btn4B;
    private javax.swing.JButton btn4C;
    private javax.swing.JButton btn5A;
    private javax.swing.JButton btn5B;
    private javax.swing.JButton btn5C;
    private javax.swing.JButton btnJugar;
    private javax.swing.JButton btnNuevoJuego;
    private javax.swing.JButton btnSiguientePartida;
    private javax.swing.JPanel jpConfiguracion;
    private javax.swing.JPanel jpJuego;
    private javax.swing.JPanel jpPartidasGanadas;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblPartidasEmpatadas;
    private javax.swing.JLabel lblPartidasGanadas;
    private javax.swing.JLabel lblPartidasGanadasJ1;
    private javax.swing.JLabel lblPartidasGanadasJ2;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTurnoJugador;
    private javax.swing.JRadioButton rb1;
    private javax.swing.JRadioButton rb2;
    private javax.swing.JSlider sliderNivel;
    // End of variables declaration//GEN-END:variables

}