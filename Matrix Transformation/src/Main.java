import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Matrix Transformation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        Random random = new Random();
        double[][] matrix = new double[2][2];
        for(int i = 0; i < 2; i++) for(int j = 0; j < 2; j++) matrix[i][j] = (random.nextDouble() - .5d) * 16;/* {{4, 3},{-2, -5}}*/;

        MyPanel panel = new MyPanel(Toolkit.getDefaultToolkit().getScreenSize(), matrix, 80);
        frame.add(panel);

    }

}
