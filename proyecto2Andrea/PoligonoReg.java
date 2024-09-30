/*
 * Proyecto 2
 * Andrea Vanegas Susano
 * 7CM3
 */

import java.util.Random;

public class PoligonoReg extends PoligonoIrreg {

    protected int angulo;

    public PoligonoReg(int numVertices) {
        super(numVertices);
        Random random = new Random();
        this.angulo = 360 / numVertices;
        int radioMaximo = 600 / 8; 
        int radio = random.nextInt(radioMaximo) + 3;
        int posXRandom = random.nextInt(800 - (2 * radioMaximo)) + radioMaximo; 
        int posYRandom = random.nextInt(600 - (2 * radioMaximo)) + radioMaximo; 
        for (int i = 0; i < numVertices; i++) {
            double x = radio * Math.cos(Math.toRadians(this.angulo * i)) + posXRandom;
            double y = radio * Math.sin(Math.toRadians(this.angulo * i)) + posYRandom;
            this.agregarVertice(new Coordenada(x, y));
        }
    }

    public double obtieneArea() {
        double area = 0;
        for (int i = 0; i < this.nVertices; i++) {
            Coordenada verticeActual = this.vertices[i];
            Coordenada verticeSiguiente = this.vertices[(i + 1) % this.nVertices];
            area += verticeActual.abcisa() * verticeSiguiente.ordenada()
                    - verticeActual.ordenada() * verticeSiguiente.abcisa();
        }
        return Math.abs(area) / 2;
    }
}
