import java.awt.*;

/**
 * Created by Atom on 8/5/2015.
 */
public abstract class Shape {
    //Позиции
    protected double x;
    protected double y;
    protected boolean fixed;

    //Размер
    protected int width;
    protected int height;
    protected double hWidth;
    protected double hHeight;

    //Угол
    protected double angle;

    //Цвет
    protected Color color;



    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        if (fixed) return;
        this.x = x;
    }

    public void setY(double y) {
        if (fixed) return;
        this.y = y;
    }

    public void setPosition(double x, double y) {
        if (fixed) return;
        this.x = x;
        this.y = y;
    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getHalfWidth() {
        return hWidth;
    }

    public double getHalfHeight() {
        return hHeight;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        hWidth = width / 2.0;
        hHeight = height / 2.0;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }



    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setColor(Color color) {
        this.color = color;
    }



    public abstract void render(Graphics g);

}
