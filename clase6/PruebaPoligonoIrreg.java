public class PruebaPoligonoIrreg {
  public static void main(String[] args) {
    
    PoligonoIrreg poligonoIrreg = new PoligonoIrreg(7);

    System.out.println("Primera impresion: ");
    System.out.println(poligonoIrreg.toString());

    poligonoIrreg.setVertice(4, new Coordenada(4, 5));

    System.out.println("Segunda impresion: ");
    System.out.println(poligonoIrreg.toString());    

  }
}
