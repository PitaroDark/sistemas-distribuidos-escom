import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Airplane {

  private double x;
  private double y;
  private double speed;
  private double angle;
  private double turnRate;
  private double width;
  private double height;
  private int maxWindowWidth;
  private int maxWindowHeight;
  private List<Point> rastro;

  public Airplane(
      double x,
      double y,
      double speed,
      double turnRate,
      double width,
      double height) {
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.turnRate = turnRate;
    this.width = width;
    this.height = height;
    this.angle = 0;
    this.rastro = new ArrayList<>();
  }

  public void move() {
    x += speed * Math.cos(angle);
    y += speed * Math.sin(angle);
    rastro.add(new Point((int) x, (int) y));
  }

  public void turn() {
    double margen = 100;
    double nuevoAngulo = angle;

    if (x < margen) {
      nuevoAngulo = Math.atan2(Math.sin(angle), Math.abs(Math.cos(angle)) + 0.5);
    } else if (x > maxWindowWidth - margen) {
      nuevoAngulo = Math.atan2(Math.sin(angle), -Math.abs(Math.cos(angle)) - 0.5);
    }

    if (y < margen) {
      nuevoAngulo = Math.atan2(Math.abs(Math.sin(angle)) + 0.5, Math.cos(angle));
    } else if (y > maxWindowHeight - margen) {
      nuevoAngulo = Math.atan2(-Math.abs(Math.sin(angle)) - 0.5, Math.cos(angle));
    }

    angle = angle + turnRate * (nuevoAngulo - angle);
  }

  public void turn(double angle) {
    this.angle = angle;
  }

  public void turnAwayFromBorder() {
    if (x <= 50) {
      angle = Math.PI / 2;
    } else if (x >= maxWindowWidth) {
      angle = 3 * Math.PI / 2;
    }

    if (y <= 50) {
      angle = 0;
    } else if (y >= maxWindowHeight) {
      angle = Math.PI;
    }
  }

  public void evadePursuers(Airplane[] pursuers) {
    double avgX = 0;
    double avgY = 0;
    int count = 0;

    for (Airplane pursuer : pursuers) {
        double dist = Math.sqrt(Math.pow(pursuer.getX() - this.x, 2) + Math.pow(pursuer.getY() - this.y, 2));

        if (dist < 300) { 
            avgX += pursuer.getX();
            avgY += pursuer.getY();
            count++;
        }
    }

    if (count > 0) {
        avgX /= count;
        avgY /= count;

        double evadeAngle = Math.atan2(this.y - avgY, this.x - avgX);
        this.angle = evadeAngle + (Math.random() - 0.5) * 0.5; 
    }
}

  public void turnAwayFrom(Airplane pursuer) {
    double pursuerAngle = Math.atan2(y - pursuer.getY(), x - pursuer.getX());
    angle = pursuerAngle + Math.PI; 
  }

  public void setMaxWindow(int width, int height) {
    this.maxWindowWidth = width - 50;
    this.maxWindowHeight = height - 50;
  }

  public void stayWithinBounds() {
    if (x <= 50) {
      x = 50;
    } else if (x >= maxWindowWidth) {
      x = maxWindowWidth;
    }

    if (y <= 50) {
      y = 50;
    } else if (y >= maxWindowHeight) {
      y = maxWindowHeight;
    }
  }

  public boolean isCloseToBorder() {
    return x <= 150 || x >= maxWindowWidth - 200 || y <= 150 || y >= maxWindowHeight - 200;
  }

  public boolean isCloseTo(Airplane target, double threshold) {
    return Math.abs(x - target.getX()) < threshold && Math.abs(y - target.getY()) < threshold;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getAngle() {
    return angle;
  }

  public double getSpeed() {
    return speed;
  }

  public double getTurnRate() {
    return turnRate;
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public void setTurnRate(double turnRate) {
    this.turnRate = turnRate;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public List<Point> getRastro() {
    return rastro;
  }

  @Override
  public String toString() {
    return "Airplane[x=" + x + ", y=" + y + ", angle=" + angle + ", speed=" + speed + ", turnRate=" + turnRate
        + ", width=" + width + ", height=" + height + "]";
  }
}
