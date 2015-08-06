import java.awt.*;

/**
 * Created by Atom on 8/5/2015.
 */
public class EllipseShape extends Shape {

    //���������
    public void render(Graphics g) {
        int hW = width / 2;
        int hH = height / 2;
        g.setColor(color);
        g.fillOval((int)(x - hW), (int)(y - hH), width, height);
    }

    //������
    public double getRadius() {
        return getHalfWidth();
    }
}
