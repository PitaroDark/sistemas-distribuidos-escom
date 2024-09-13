public abstract class Figura {

  protected int numeroLados;

  public Figura() {
      this.numeroLados = 0;
  }

  public Figura(int numeroLados){
    this.numeroLados = numeroLados;
  }
  
  public abstract float area();

  public int getNumeroLados(){
    return this.numeroLados;
  }

  public void setNumeroLados(int numeroLados){
    this.numeroLados = numeroLados;
  }
}