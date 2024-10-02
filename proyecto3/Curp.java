
/**
 * PROYECTO 3
 * IKARI BRANDON VARGAS OSORNIO
 * 7CM3
 */
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

public class Curp {
  protected final String Letra;
  protected final String Numero;
  protected final String Sexo;
  protected final String Entidad[];
  protected final Map<String, String> Entidades;

  public Curp() {
    this.Letra = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    this.Numero = "0123456789";
    this.Sexo = "HM";
    this.Entidad = new String[] {
        "AS", "BC", "BS", "CC", "CS", "CH", "CL", "CM", "DF", "DG", "GT", "GR", "HG", "JC", "MC", "MN",
        "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TL", "TS", "VZ", "YN", "ZS"
    };
    this.Entidades = new HashMap<String, String>();
    this.Entidades.put("AS", "Aguascalientes");
    this.Entidades.put("BC", "Baja California");
    this.Entidades.put("BS", "Baja California Sur");
    this.Entidades.put("CC", "Campeche");
    this.Entidades.put("CS", "Chiapas");
    this.Entidades.put("CH", "Chihuahua");
    this.Entidades.put("CL", "Coahuila");
    this.Entidades.put("CM", "Colima");
    this.Entidades.put("DF", "Distrito Federal");
    this.Entidades.put("DG", "Durango");
    this.Entidades.put("GT", "Guanajuato");
    this.Entidades.put("GR", "Guerrero");
    this.Entidades.put("HG", "Hidalgo");
    this.Entidades.put("JC", "Jalisco");
    this.Entidades.put("MC", "México");
    this.Entidades.put("MN", "Michoacán");
    this.Entidades.put("MS", "Morelos");
    this.Entidades.put("NT", "Nayarit");
    this.Entidades.put("NL", "Nuevo León");
    this.Entidades.put("OC", "Oaxaca");
    this.Entidades.put("PL", "Puebla");
    this.Entidades.put("QT", "Querétaro");
    this.Entidades.put("QR", "Quintana Roo");
    this.Entidades.put("SP", "San Luis Potosí");
    this.Entidades.put("SL", "Sinaloa");
    this.Entidades.put("SR", "Sonora");
    this.Entidades.put("TC", "Tabasco");
    this.Entidades.put("TL", "Tamaulipas");
    this.Entidades.put("TS", "Tlaxcala");
    this.Entidades.put("VZ", "Veracruz");
    this.Entidades.put("YN", "Yucatán");
    this.Entidades.put("ZS", "Zacatecas");
  }

  public String generateRandomCURP() {
    int indice;
    StringBuilder sb = new StringBuilder(18);

    for (int i = 1; i < 5; i++) {
      indice = (int) (Letra.length() * Math.random());
      sb.append(Letra.charAt(indice));
    }

    for (int i = 5; i < 11; i++) {
      indice = (int) (Numero.length() * Math.random());
      sb.append(Numero.charAt(indice));
    }
    indice = (int) (Sexo.length() * Math.random());
    sb.append(Sexo.charAt(indice));

    sb.append(Entidad[(int) (Math.random() * 32)]);
    for (int i = 14; i < 17; i++) {
      indice = (int) (Letra.length() * Math.random());
      sb.append(Letra.charAt(indice));
    }
    for (int i = 17; i < 19; i++) {
      indice = (int) (Numero.length() * Math.random());
      sb.append(Numero.charAt(indice));
    }

    return sb.toString();
  }

  public String getGender(String curp) {
    return curp.substring(10, 11).equals("H") ? "Hombre" : "Mujer";
  }

  public String getState(String curp) {
    return Entidades.get(curp.substring(11, 13));
  }

  public int getAge(String curp) {
    int year = Integer.parseInt(curp.substring(4, 6));
    int month = Integer.parseInt(curp.substring(6, 8));
    int day = Integer.parseInt(curp.substring(8, 10));
    int currentYear = new Date().getYear();
    int currentMonth = new Date().getMonth() + 1;
    int currentDay = new Date().getDate();
    int age = currentYear - year;
    if (month > currentMonth)
      age--;
    else if (month == currentMonth && day > currentDay)
      age--;
    return age;
  }

}
