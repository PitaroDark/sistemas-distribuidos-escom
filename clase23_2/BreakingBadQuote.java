public class BreakingBadQuote {
  String quote;
  String author;
  String translated_text;

  public BreakingBadQuote(String quote, String author) {
    this.quote = quote;
    this.author = author;
  }

  public String getQuote() {
    return quote;
  }

  public void setQuote(String quote) {
    this.quote = quote;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTranslatedText() {
    return translated_text;
  }

  public void setTranslatedText(String translated_text) {
    this.translated_text = translated_text;
  }

  @Override
  public String toString() {
    return "Autor: " + author + "\n" +
        "Cita: " + quote + "\n" +
        "Texto traducido: " + translated_text;
  }

}
