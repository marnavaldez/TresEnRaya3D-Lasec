/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;


/**
 *
 * @author mar
 */
public class TresEnRaya3D {
    private int[][][] tablero = new int[3][3][3];
    private int juegosGanadosJ1 = 0,
                 juegosGanadosJ2 = 0,
                 juegosEmpatados = 0;
    private int ganador = 0;   // 1 > Jugador 1, 2 > Jugador 2 y 3 > Empate
    private int turno = 1;     //1 > Jugador 1, 2 > Jugador 2

    public TresEnRaya3D (){
         
    }
    
    //Getters y Setters
    
    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
    
    public int getGanador() {
        return ganador;
    }

    public void setGanador(int ganador) {
        this.ganador = ganador;
    }
    
    public int[][][] getTablero() {
        return tablero;
    }

    public int getJuegosGanadosJ1() {
        return juegosGanadosJ1;
    }

    public void setJuegosGanadosJ1(int juegosGanadosJ1) {
        this.juegosGanadosJ1 = juegosGanadosJ1;
    }

    public int getJuegosGanadosJ2() {
        return juegosGanadosJ2;
    }

    public void setJuegosGanadosJ2(byte juegosGanadosJ2) {
        this.juegosGanadosJ2 = juegosGanadosJ2;
    }

    public int getJuegosEmpatados() {
        return juegosEmpatados;
    }

    public void setJuegosEmpatados(byte juegosEmpatados) {
        this.juegosEmpatados = juegosEmpatados;
    } 
    //_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
    
    /**
    * Limpia el juego para una siguiente partida.
    */
    public void siguienteJuego() {
        this.turno = (this.ganador == 1)? 1:2; // determina quien inicia la sifuiente partida

        this.ganador = 0;
    
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                for (int k=0; k<3; k++){
                    tablero[i][j][k] = 0;
                }
            }   
        }
    }

    /**
     * Resetea los valores necesarios para iniciar un juego desde cero.
     */
    public void nuevoJuego() {
        this.juegosGanadosJ1 = 0;
        this.juegosGanadosJ2 = 0;
        this.juegosEmpatados = 0;
        
        //Se manda llamar siguienteJuego para reutilizar codigo.
        siguienteJuego();
        
        this.turno = 1;
        
    }
    
    /**
    * ingresa un los valores a las casillas del tablero.
     * @param posicion
    */
    public void ingresarValor(int x, int y, int z) {
        tablero[x][y][z] = turno;   //Ingresa en la casilla el valor de 1 o 2 según el turno.
        
        verificarJuegoGanado();
    }
    
    /**
     * Valida que la casilla se encuentre libre.
     * @param x
     * @return 
     */
    public boolean casillaLibre(int x, int y, int z) {    
        if(tablero[x][y][z] == 0){
            return true;
        }
        return false;
    }
    
    /**
     * En caso de que se haya completado una linea, configura las variables
     * preparandolas para el siguiente juego.
     */
    public void verificarJuegoGanado(){
        
        if(validacionDeLinea()){
                ganador = turno;
                if(turno == 1) 
                    juegosGanadosJ1++;
                else
                    juegosGanadosJ2++ ;
                
            }else if (empate()){
                juegosEmpatados++; 
                ganador = 3;
            }
    }
    
    /**
     * Hace un conteo en todo el tablero buscando una linea ganadora.
     * @return En caso de que haya alguna linea formada retorna true.
     */
    public boolean validacionDeLinea(){
        int conteo = 0;
        // validaciones en filas con respecto de j
        for (int k=0; k<3; k++){
            for (int i=0; i<3; i++){
                conteo = 0;
            for (int j=0; j<3; j++){
                if(tablero[i][j][k] == turno) conteo++;
                if (conteo == 3) return true;   
            }
        }
    }
        
        // validaciones en filas con respecto de k
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                conteo = 0;
            for (int k=0; k<3; k++){
                if(tablero[i][j][k] == turno) conteo++;
                if (conteo == 3) return true;   
            }
        }
    }

    // validaciones en columnas con respecto de i
        for (int k=0; k<3; k++){
            for (int j=0; j<3; j++){
                conteo = 0;
            for (int i=0; i<3; i++){
                if(tablero[i][j][k] == turno) conteo++;
                if (conteo == 3) return true;
            }
        }
    }
        
        //               Validación de diagonales
        // -- Con respecto de i --
        for (int i=0; i<3; i++){
            conteo = 0;
            for(int j=0; j<3; j++){
                if(tablero[i][j][j] == turno) conteo++;
                if(conteo == 3) return true;
            }
        }
        
        for (int i=0; i<3; i++){
            conteo = 0;
            int k = 2;
            for(int j=0; j<3; j++){
                if(tablero[i][j][k] == turno) conteo++;
                k--;
                if(conteo == 3) return true;    
            }
        } // - - - - - - - - - -
        
        // -- Con respecto de j --
        for(int j=0; j<3; j++){
            conteo = 0;
            for (int i=0; i<3; i++){
                if(tablero[i][j][i] == turno) conteo++;
                if(conteo == 3) return true;
            }
        }
        
        for(int j=0; j<3; j++){
            conteo = 0;
            int k = 2;
            for (int i=0; i<3; i++){
                if(tablero[i][j][k] == turno) conteo++;
                k--;
                if(conteo == 3) return true;    
            }
        } // - - - - - - - - - -
        
      
        // -- Con respecto de k --
        for(int k=0; k<3; k++){
            conteo = 0;
            for (int i=0; i<3; i++){
                if(tablero[i][i][k] == turno) conteo++;
                if(conteo == 3) return true;
            }
        }
        
        for(int k=0; k<3; k++){
            conteo = 0;
            int j = 2;
            for (int i=0; i<3; i++){
                if(tablero[i][j][k] == turno) conteo++;
                j--;
                if(conteo == 3) return true;    
            }
        } // - - - - - - - - - -
        
        // Validación de esquinas
        if(tablero[0][0][0] == turno && tablero[1][1][1] == turno && tablero[2][2][2] == turno)
            return true;
        if(tablero[0][2][2] == turno && tablero[1][1][1] == turno && tablero[2][0][0] == turno)
            return true;
        if(tablero[0][2][0] == turno && tablero[1][1][1] == turno && tablero[2][0][2] == turno)
            return true;
        if(tablero[0][0][2] == turno && tablero[1][1][1] == turno && tablero[2][2][0] == turno)
            return true;
        
        return false;
    }

    private boolean empate() {
        for (int k=0; k<3; k++){
            for (int i=0; i<3; i++){
                for (int j=0; j<3; j++){
                    if (tablero[i][j][k] == 0)
                        return false;               
                }
            }
        }    
        return true;
    }

    // Cambiando de turno
    public void cambioDeTurno() {
            turno = (turno == 1)? 2:1;
    }
}
