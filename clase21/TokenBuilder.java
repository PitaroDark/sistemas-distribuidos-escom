import java.util.Random;

public class TokenBuilder {

    public TokenBuilder() {
    }

    public StringBuilder generateString(int times) {
        StringBuilder chain = new StringBuilder(times * 4);

        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < times; i++) {
            for (int j = 0; j < 3; j++) {
                chain.append(alphabet.charAt(random.nextInt(26)));
            }
            if (i < times - 1) {
                chain.append(' ');
            }
        }

        return chain;
    }

    public int timesFoundString(StringBuilder builder, String toFound) {
        int count = 0;
        int position = 0;

        while ((position = builder.indexOf(toFound, position)) != -1) {
            count++;
            position += toFound.length();
        }

        return count;
    }

}
