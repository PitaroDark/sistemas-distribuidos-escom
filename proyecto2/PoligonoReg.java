import java.util.Random;

public class PoligonoReg extends PoligonoIrreg {

  protected double angulo;

  public PoligonoReg(int maxVertices) {
    super(maxVertices);
    this.angulo = maxVertices / 360.0;
    Random rand = new Random();
    int size = rand.nextInt(100);
    for (int i = 0; i < maxVertices; i++) {
      int x = (i == 0) ? 0 : rand.nextInt(size);
      int y = (i == 0) ? 0 : rand.nextInt(size);
      this.vertices.add(new Coordenada(x, y));
    }
  }

  public double obtieneArea() {
    return 0.0;
  }

}
