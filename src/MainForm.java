import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Atom on 8/6/2015.
 */
public class MainForm extends JFrame {

    private JPanel panel;
    //Коллизия
    private JCheckBox chb1;
    //Поинтереснее
    private JCheckBox chb2;
    //Частицы
    private JCheckBox chb3;
    //Хвост
    private JCheckBox chb4;

    //Запрет выполнения события изменения состояний чекбоксов
    private boolean lockChange;



    public MainForm() {
        //Панель с чекбоксами
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        add(panel, BorderLayout.SOUTH);

        //Панель с шарами
        Scene scene = new Scene();
        scene.setSize(500, 500);
        scene.setMode(0);
        add(scene, BorderLayout.CENTER);

        lockChange = false;

        //Коллизия
        chb1 = new JCheckBox("Collision");
        chb1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (lockChange) return;;

                if (chb1.isSelected()) {
                    lockChange = true;
                    chb2.setSelected(false);
                    chb3.setSelected(false);
                    scene.setMode(1);
                    lockChange = false;
                } else scene.setMode(0);
            }
        });
        panel.add(chb1);

        //Поинтереснее
        chb2 = new JCheckBox("Поинтереснее");
        chb2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (lockChange) return;

                if (chb2.isSelected()) {
                    lockChange = true;
                    chb1.setSelected(false);
                    chb3.setSelected(false);
                    scene.setMode(2);
                    lockChange = false;
                } else scene.setMode(0);
            }
        });
        panel.add(chb2);

        //Частицы
        chb3 = new JCheckBox("Частицы");
        chb3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (lockChange) return;

                if (chb3.isSelected()) {
                    lockChange = true;
                    chb1.setSelected(false);
                    chb2.setSelected(false);
                    scene.setMode(3);
                    lockChange = false;
                } else scene.setMode(0);
            }
        });
        panel.add(chb3);

        //Хвост
        chb4 = new JCheckBox("Хвост");
        chb4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                scene.setTail(chb4.isSelected());
            }
        });
        panel.add(chb4);
    }

}
