import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Atom on 8/5/2015.
 */
public class Scene extends JPanel {

    //������ �����
    private ArrayList<Shape> shapes;

    //�����
    private MyThread myThread;
    private Thread thread;

    //�����
    private int mode = 0;

    //�������� �����������
    private int speed = 2;

    //�����
    private boolean tail;



    public Scene() {
        shapes = new ArrayList<Shape>();

        setSize(500, 500);
        setBackground(Color.white);

        myThread = new MyThread(this);
        thread = new Thread(myThread);
    }



    //���������� ��������� ����� ������
    public double getDistantion(EllipseShape shape1, EllipseShape shape2) {
        double xDist = shape1.getX() - shape2.getX();
        double yDist = shape1.getY() - shape2.getY();
        double dist = Math.sqrt(xDist * xDist + yDist * yDist);

        return dist - (shape1.getRadius() + shape2.getRadius());
    }



    //�������� ������� � �����
    public void update() {

        for (int i = 0; i < shapes.size(); i++) {
            Shape shape = shapes.get(i);

            //���������� ��� ��������� ����
            for (int j = i + 1; j < shapes.size(); j++) {
                Shape colShape = shapes.get(j);

                //���� ������������
                if (getDistantion((EllipseShape) shape, (EllipseShape) colShape) < 0) {
                    //������� ����� ���� ��� �����
                    double d = colShape.getX() - shape.getX();
                    double k = d != 0 ? (shape.getY() - colShape.getY()) / d : 0;
                    double alpha = Math.PI / 2 + Math.atan(k);
                    shape.setAngle(Math.PI + 2 * alpha - shape.getAngle());
                    colShape.setAngle(Math.PI + 2 * alpha - colShape.getAngle());

                    double dx = Math.cos(Math.PI / 2 - shape.getAngle());
                    double dy = Math.sin(Math.PI / 2 - shape.getAngle());

                    double dx1 = Math.cos(Math.PI / 2 - colShape.getAngle());
                    double dy1 = Math.sin(Math.PI / 2 - colShape.getAngle());

                    //������� ��������
                    while (getDistantion((EllipseShape) shape, (EllipseShape) colShape) <= 0) {
                        shape.setPosition(
                                shape.getX() + dx,
                                shape.getY() + dy
                        );
                        colShape.setPosition(
                                colShape.getX() + dx1,
                                colShape.getY() + dy1
                        );
                    }
                    break;
                }
            }

            //��������� �� ������������ ������
            if (shape.getX() - shape.getHalfWidth() <= 0) {

                shape.setAngle(Math.PI * 2 - shape.getAngle()); //360 - angle

                double dx = Math.cos(Math.PI / 2 - shape.getAngle());
                if (dx <= 0) {
                    shape.setAngle(Math.PI * 2 - shape.getAngle()); //360 - angle
                    dx = Math.cos(Math.PI / 2 - shape.getAngle());
                }

                while (shape.getX() - shape.getHalfWidth() <= 0)
                    shape.setX(shape.getX() + dx);

            } else if (shape.getX() + shape.getHalfWidth() >= getWidth()) {

                shape.setAngle(Math.PI * 2 - shape.getAngle()); //360 - angle

                double dx = Math.cos(Math.PI / 2 - shape.getAngle());
                if (dx >= 0) {
                    shape.setAngle(Math.PI * 2 - shape.getAngle()); //360 - angle
                    dx = Math.cos(Math.PI / 2 - shape.getAngle());
                }

                while (shape.getX() + shape.getHalfWidth() >= getWidth())
                    shape.setX(shape.getX() + dx);
            }

            //��������� �� �������������� ������
            if (shape.getY() - shape.getHalfHeight() <= 0) {

                shape.setAngle(Math.PI - shape.getAngle()); //180 - angle

                double dy = Math.sin(Math.PI / 2 - shape.getAngle());
                if (dy <= 0) {
                    shape.setAngle(Math.PI - shape.getAngle()); //180 - angle
                    dy = Math.sin(Math.PI / 2 - shape.getAngle());
                }

                while (shape.getY() - shape.getHalfHeight() <= 0)
                    shape.setY(shape.getY() + dy);

            } else if (shape.getY() + shape.getHalfHeight() >= getHeight()) {

                shape.setAngle(Math.PI - shape.getAngle()); //180 - angle

                double dy = Math.sin(Math.PI / 2 - shape.getAngle());
                if (dy >= 0) {
                    shape.setAngle(Math.PI - shape.getAngle()); //180 - angle
                    dy = Math.sin(Math.PI / 2 - shape.getAngle());
                }

                while (shape.getY() + shape.getHalfHeight() >= getHeight())
                    shape.setY(shape.getY() + dy);
            }

            shape.setPosition(
                shape.getX() + speed * Math.cos(Math.PI / 2 - shape.getAngle()),
                shape.getY() + speed * Math.sin(Math.PI / 2 - shape.getAngle())
            );
        }
    }

    //����������
    public void paintComponent(Graphics g) {
        //���� ����� ����� ����������� �������������� �����
        if (tail) {
            g.setColor(new Color(1, 1, 1, 0.1f));
            g.fillRect(0, 0, getWidth(), getHeight());
        } else
            super.paintComponent(g);

        //�����������
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //��������� �����
        for (Shape shape : shapes)
            shape.render(g);
    }



    //��������� ������
    public void setTail(boolean tail) {
        this.tail = tail;
    }

    //��������� ������
    public void setMode(int mode) {
        Random r = new Random();

        //������������� ������� �����
        myThread.terminate();
        while (!myThread.isStopped()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

            }
        }

        //������ �����
        switch (mode) {
            case 0: {
                speed = 2;

                if (this.mode == 1) {
                    Shape temp = shapes.get(0);
                    shapes.clear();
                    shapes.add(temp);
                    break;
                }

                shapes.clear();

                Shape shape = new EllipseShape();
                shape.setSize(40, 40);

                shape.setPosition(
                        shape.getHalfWidth() + 5 + r.nextInt(getWidth() - shape.getWidth() - 10),
                        100
                );
                shape.setColor(Color.red);
                shape.setAngle(Math.PI / 2);

                shapes.add(shape);
            }
                break;

            case 1: {
                speed = 2;

                if (this.mode != 0) {
                    shapes.clear();

                    Shape shape = new EllipseShape();
                    shape.setSize(40, 40);

                    shape.setPosition(
                            shape.getHalfWidth() + 5 + r.nextInt(getWidth() - shape.getWidth() - 10),
                            100
                    );
                    shape.setColor(Color.red);
                    shape.setAngle(Math.PI / 2);
                    shapes.add(shape);
                }

                Shape blueShape = new EllipseShape();
                blueShape.setSize(40, 40);

                blueShape.setPosition(
                        blueShape.getHalfWidth() + 5 + r.nextInt(getWidth() - blueShape.getWidth() - 10),
                        100
                );
                blueShape.setColor(Color.blue);
                blueShape.setAngle(Math.PI / 2);
                blueShape.setFixed(true);
                shapes.add(blueShape);
            }
                break;

            case 2:
            case 3:
                shapes.clear();

                int count = mode == 2 ? 6 : 1000;
                int size = mode == 2 ? 40 : 4;
                speed = mode == 2 ? 2 : 1;

                for (int i = 0; i < count; i++) {
                    Shape shape = new EllipseShape();
                    shape.setSize(size, size);

                    shape.setPosition(
                            shape.getHalfWidth() + 5 + r.nextInt(getWidth() - shape.getWidth() - 10),
                            shape.getHalfHeight() + 5 + r.nextInt(getHeight() - shape.getHeight() - 10)
                    );
                    shape.setColor(new Color(r.nextFloat(), r.nextFloat(), r.nextFloat()));
                    shape.setAngle(r.nextFloat() * Math.PI);
                    shapes.add(shape);
                }
                break;
        }

        this.mode = mode;

        //������� ����� �����
        myThread = new MyThread(this);
        thread = new Thread(myThread);
        thread.start();
    }

}