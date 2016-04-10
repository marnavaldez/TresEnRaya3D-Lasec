/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;

/**
 *
 * @author MAR
 */
public class InteligenciaArtificial {
    
    /**
     * Busca una posición libre en el tablero de manera aleatoria.
     * @param tablero del juego.
     * @return posicion aleatoria.
     */
    public int[] movimientoRandom(int[][][] tablero) {
        //Movimiento aleatorio
        boolean celdaLibre = false;
        int x = 0, y = 0, z = 0;
        
        while(celdaLibre == false) {
            x = (int) Math.round(Math.random() * 2);
            y = (int) Math.round(Math.random() * 2);
            z = (int) Math.round(Math.random() * 2);
            
            if(tablero[x][y][z] == 0)
                celdaLibre = true;
        }
        
        int[] posicion = {x,y,z};
        return posicion;
    }
    
    /**
     * Busca una posición libre en el tablero de manera más inteligente
     * dependiendo del nivel seleccionado por el usuario.
     * Éste método sirve tanto para bloquear una linea del humano, como para
     * ganar una partida de parte de la máquina.
     * @param tablero 
     * @param nivel
     * @return psición de la casilla disponible.
     */
    public int[] buscarMovimiento(int[][][] tablero, int nivel) {
        int[] posLibre = {-1,-1,-1}; //Se inicializa con valores negativos para 
                                     //indicar que no ha sido una casilla libre.

        if(tablero[1][1][1] == 0 && nivel == 1) { //En caso de que la posición 1,1,1 este libre
            return new int[]{1,1,1};              // y el nivel sea " 1 Experto".
        } else {
            int x = 0, y = 0, z = 0;

            //Buscar dos en linea para gane de la maquina.
            posLibre = buscarDosEnLinea(tablero,2,nivel);

            //Si encuentra 2 y una casilla vacia
            if(posLibre[0] != -1) {
    //            //Insertar la ficha faltante para ganar.
                return posLibre;
            } else {
    //            //Buscar dos en linea del humano
                posLibre = buscarDosEnLinea(tablero,1,nivel);
    //                //Si encuentra 2 y una casilla vacia
                    if(posLibre[0] != -1) {
    //                    //Insertar la ficha faltante para bloquear.
                        return posLibre;
                    } else {//Si no, insertar valor aleatorio
                        return movimientoRandom(tablero);
                    }
            }
        }
    }
    
    /**
     * Valida la lista pasada por parametro y en caso de que algún
     * areglo contenga una posición libre, regresa ése arreglo.
     * @param lista
     * @param tablero
     * @return arreglo posición libre en el tablero.
     */
    public int[] validarLinea(ArrayList<int[]> lista, int[][][] tablero) {
        int x = lista.get(0)[0], 
            y = lista.get(0)[1], 
            z = lista.get(0)[2];

        if(tablero[x][y][z] == 0)
            return lista.get(0);

        x = lista.get(1)[0];
        y = lista.get(1)[1]; 
        z = lista.get(1)[2];
        
        if(tablero[x][y][z] == 0)
            return lista.get(1);

        x = lista.get(2)[0];
        y = lista.get(2)[1]; 
        z = lista.get(2)[2];

        if(tablero[x][y][z] == 0)
            return lista.get(2);

        int[] casillaNoVacia = {-1,-1,-1};

        return casillaNoVacia;

    }
   
    /**
     * Dependiendo del nivel, hace una busqueda en el tablero de dos fichas
     * del mismo valor en linea, si las encuentra valida la linea completa con 
     * el método validarLinea(), ahora en busca de una casilla libre en ésa linea.
     * @param tablero
     * @param turno
     * @param nivel
     * @return arreglo con coordenadas de casilla libre 
     *         o un arreglo con valores negativos.
     */
    public int[] buscarDosEnLinea(int[][][] tablero, int turno, int nivel) {
        int posicion[] = new int[3];
        ArrayList<int[]> casillasList;
        
        int conteo = 0;

        // Busqueda en horizontal con respecto de j
        for (int k=0; k<3; k++){
            for (int i=0; i<3; i++){
                conteo = 0;
                casillasList = new ArrayList();
                for (int j=0; j<3; j++){
                    casillasList.add(new int[]{i,j,k});
                    
                    if(tablero[i][j][k] == turno) conteo++;
                    
                    if(conteo == 2){
                        //En caso de que valla en la posición 1 de j, 
                        //es necesario guardar la siguiente posicion en la lista.
                        if(j == 1) casillasList.add(new int[]{i,2,k});
                        
                        posicion = validarLinea(casillasList,tablero);
                        if(posicion[0] != -1) return posicion;
                    }
                }
            }
        }
        
        // Busqueda en horizontal con respecto de k
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                conteo = 0;
                casillasList = new ArrayList();
                for (int k=0; k<3; k++){
                    casillasList.add(new int[]{i,j,k});
                    
                    if(tablero[i][j][k] == turno) conteo++;
                    
                    if(conteo == 2){
                        
                        if(k == 1) casillasList.add(new int[]{i,j,2});
                        
                        posicion = validarLinea(casillasList,tablero);
                        
                        if(posicion[0] != -1) return posicion;
                    }
                }
            }
        }
        
        // Busqueda en vertical con respecto de i
        for (int k=0; k<3; k++){
            for (int j=0; j<3; j++){
                conteo = 0;
                casillasList = new ArrayList();
                for (int i=0; i<3; i++){
                    casillasList.add(new int[]{i,j,k});
                    
                    if(tablero[i][j][k] == turno) conteo++;
                    
                    if(conteo == 2){
                        
                        if(i == 1) casillasList.add(new int[]{2,j,k});
                        
                        posicion = validarLinea(casillasList,tablero);
                        
                        if(posicion[0] != -1) return posicion;
                    }
                }
            }
        }
        
        //               Busqueda en diagonales
        if(nivel == 2 || nivel == 1) { //Si el nivel es 2 Normal o 1 Experto
            // -- Con respecto de i --
            for (int i=0; i<3; i++){
                conteo = 0;
                casillasList = new ArrayList();
                for(int j=0; j<3; j++){
                    casillasList.add(new int[]{i,j,j});
                    if(tablero[i][j][j] == turno) conteo++;
                    if(conteo == 2){
                            if(j == 1) casillasList.add(new int[]{i,2,2});
                            posicion = validarLinea(casillasList,tablero);
                            if(posicion[0] != -1) return posicion;
                        }
                }
            }

            for (int i=0; i<3; i++){
                int k = 2;
                conteo = 0;
                casillasList = new ArrayList();
                for(int j=0; j<3; j++){
                    casillasList.add(new int[]{i,j,k});
                    if(tablero[i][j][k] == turno) conteo++;
                    k--;
                    if(conteo == 2){
                            if(j == 1) casillasList.add(new int[]{i,2,0});
                            posicion = validarLinea(casillasList,tablero);
                            if(posicion[0] != -1) return posicion;
                        }
                }
            }

            // -- Con respecto de j --
            for (int j=0; j<3; j++){
                conteo = 0;
                casillasList = new ArrayList();
                for(int i=0; i<3; i++){
                    casillasList.add(new int[]{i,j,i});
                    if(tablero[i][j][i] == turno) conteo++;
                    if(conteo == 2){
                            if(i == 1) casillasList.add(new int[]{2,j,2});
                            posicion = validarLinea(casillasList,tablero);
                            if(posicion[0] != -1) return posicion;
                        }
                }
            }

            for(int j=0; j<3; j++){
                conteo = 0;
                int k = 2;
                casillasList = new ArrayList();
                for (int i=0; i<3; i++){
                    casillasList.add(new int[]{i,j,k});
                    if(tablero[i][j][k] == turno) conteo++;
                    k--;
                    if(conteo == 2){
                            if(i == 1) casillasList.add(new int[]{2,j,0});
                            posicion = validarLinea(casillasList,tablero);
                            if(posicion[0] != -1) return posicion;
                        }
                }
            }

            // -- Con respecto de k --
            for(int k=0; k<3; k++){
                conteo = 0;
                casillasList = new ArrayList();
                for (int i=0; i<3; i++){
                    casillasList.add(new int[]{i,i,k});
                    if(tablero[i][i][k] == turno) conteo++;
                    if(conteo == 2){
                            if(i == 1) casillasList.add(new int[]{2,2,k});
                            posicion = validarLinea(casillasList,tablero);
                            if(posicion[0] != -1) return posicion;
                        }
                }
            }

            for(int k=0; k<3; k++){
                int j = 2;
                conteo = 0;
                casillasList = new ArrayList();
                for (int i=0; i<3; i++){
                    casillasList.add(new int[]{i,j,k});
                    if(tablero[i][j][k] == turno) conteo++;
                    j--;
                    if(conteo == 2){
                            if(i == 1) casillasList.add(new int[]{2,0,k});
                            posicion = validarLinea(casillasList,tablero);
                            if(posicion[0] != -1) return posicion;
                        }
                }
            }
        }
    
        // Busqueda de Esquinas en diagonal
        if(nivel == 1){ //Si el nivel es 1 Experto
            // Validación de esquinas
            conteo = 0;
            if(tablero[0][0][0] == turno) conteo++; if(tablero[1][1][1] == turno) conteo++; if(tablero[2][2][2] == turno)  conteo++;
            if(conteo == 2) {
                casillasList = new ArrayList();
                for(int i=0;i<3;i++)
                    casillasList.add(new int[]{i,i,i});

                posicion = validarLinea(casillasList,tablero);
                if(posicion[0] != -1) return posicion;
            }

            conteo = 0;
            if(tablero[0][2][2] == turno) conteo++; if(tablero[1][1][1] == turno) conteo++; if(tablero[2][0][0] == turno)  conteo++;
            if(conteo == 2) {
                casillasList = new ArrayList();
                casillasList.add(new int[]{0,2,2});
                casillasList.add(new int[]{1,1,1});
                casillasList.add(new int[]{2,0,0});
                posicion = validarLinea(casillasList,tablero);
                if(posicion[0] != -1) return posicion;
            }

            conteo = 0;
            if(tablero[0][2][0] == turno) conteo++; if(tablero[1][1][1] == turno) conteo++; if(tablero[2][0][2] == turno)  conteo++;
            if(conteo == 2) {
                casillasList = new ArrayList();
                casillasList.add(new int[]{0,2,0});
                casillasList.add(new int[]{1,1,1});
                casillasList.add(new int[]{2,0,2});
                posicion = validarLinea(casillasList,tablero);
                if(posicion[0] != -1) return posicion;
            }

            conteo = 0;
            if(tablero[0][0][2] == turno) conteo++; if(tablero[1][1][1] == turno) conteo++; if(tablero[2][2][0] == turno)  conteo++;
            if(conteo == 2) {
                casillasList = new ArrayList();
                casillasList.add(new int[]{0,0,2});
                casillasList.add(new int[]{1,1,1});
                casillasList.add(new int[]{2,2,0});
                posicion = validarLinea(casillasList,tablero);
                if(posicion[0] != -1) return posicion;    
            }
        }
        
        //En caso de que no haya encontrado 2 en linea y una casilla libre,
        // regresa un arreglo con valores negativos.
        posicion[0] = -1; posicion[1] = -1; posicion[2] = -1;
        return posicion;
    }      
}
