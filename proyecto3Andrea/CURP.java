/**
 * PROYECTO 3
 * ANDREA VANEGAS SUSANO
 * 7CM3
 */
import java.util.ArrayList;
import java.util.Iterator;

public class CURP {
    private String Letra = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String Numero = "0123456789";
    private String Sexo = "HM";
    private String Entidad[] = { "AS", "BC", "BS", "CC", "CS", "CH", "CL", "CM", "DF", "DG", "GT", "GR", "HG", "JC",
            "MC", "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TL", "TS", "VZ", "YN",
            "ZS" };
    private String Estados[] = { "Aguascatientes", "Baja California", "Baja California Sur", "Cmpeche", "Chiapas",
            "Chihuahua", "Coahuila", "Colima", "Distrito Federal", "Durango", "Guanajuato", "Guerrero", "Hidalgo",
            "Jalisco", "México", "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro",
            "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracurz",
            "Yucatán", "Zacatecas" };

    public boolean isWoman(String curp) {
        return curp.charAt(10) == 'M';
    }

    public String generaCurp() {
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

    public String obtenerEstado(String entidad) {
        System.out.println(entidad);
        for (int i = 0; i < Entidad.length; i++) {
            if (entidad.equals(Entidad[i]))
                return Estados[i];
        }
        return "";
    }

    public int obtenerEdad(String curp){
        int currentYear = 2024;
        int year = Integer.parseInt(curp.substring(4, 6));
        int age = currentYear - year;
        String edadStr = age + "";
        int edad = Integer.parseInt(edadStr.substring(2,4));
        return edad;
    }
}