import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyPanel extends JPanel implements MouseListener, ActionListener {

    private Dimension dimension;
    private Color topGridColor, bottomGridColor, bgColor;
    private int lineWidth, gridWidth, frames;
    private boolean showVectors;
    private double[][] matrix;
    private double iXDiff, iYDiff, jXDiff, jYDiff;
    private Timer timer;

    public MyPanel(Dimension dimension, double[][] m, int frames) {

        super();
        this.dimension = dimension;
        topGridColor = new Color(1, 1, 1, 0.5f); bottomGridColor = new Color(1, 1, 1, 0.15f); bgColor = Color.BLACK;
        lineWidth = 1; gridWidth = 40;
        showVectors = true;

        this.matrix = new double[][]{{1, 0}, {0, 1}};
        iXDiff = (m[0][0] - 1) / frames; iYDiff = m[0][1] / frames;
        jXDiff = m[1][0] / frames; jYDiff = (m[1][1] - 1) / frames;
        this.frames = frames;

        this.setBackground(bgColor);
        this.setPreferredSize(dimension);
        this.setVisible(true);

        addMouseListener(this);

    }

    private void animateTransform() {

        timer = new Timer(500 / frames, this);
        timer.start();

    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        int[] center = {dimension.width / 2, dimension.height / 2};
        double diagM = -center[1] / (double) center[0];

        // bg grid
        g2d.setPaint(bottomGridColor);
        g2d.setStroke(new BasicStroke(lineWidth));
        for(int i = center[1] % gridWidth; i < dimension.height; i += gridWidth) g2d.drawLine(0, i, dimension.width, i);
        for(int i = center[0] % gridWidth; i < dimension.width; i += gridWidth) g2d.drawLine(i, 0, i, dimension.height);

//        // origin
//        g2d.setPaint(Color.RED);
//        g2d.fillOval(dimension.width / 2 - 5, dimension.height / 2 - 5, 10, 10);

        // transformed grid
        g2d.setPaint(topGridColor);
        g2d.setStroke(new BasicStroke(lineWidth));
        int a, b, dist;
        double iM = -matrix[0][1] / matrix[0][0], jM = -matrix[1][1] / matrix[1][0];
        // i-lines
        if(iM >= diagM && iM <= -diagM) {
            a = (int) (center[1] - iM * center[0]); b = (int) (center[1] + iM * center[0]);
            dist = (int) (Math.abs(matrix[1][1] - matrix[0][1] * matrix[1][0] / matrix[0][0]) * gridWidth);
            if(dist == 0) g2d.drawLine(0, a, dimension.width, b);
            else {
                int temp = Math.max(a, b) / dist;
                a -= temp * dist; b -= temp * dist;
                while(Math.min(a, b) < dimension.height) {
                    g2d.drawLine(0, a, dimension.width, b);
                    a += dist; b += dist;
                }
            }
        } else {
            a = (int) (center[0] - center[1] / iM); b = (int) (center[0] + center[1] / iM);
            dist = (int) (Math.abs(matrix[1][0] - matrix[0][0] * matrix[1][1] / matrix[0][1]) * gridWidth);
            if(dist == 0) g2d.drawLine(a, 0, b, dimension.height);
            else {
                int temp = Math.max(a, b) / dist;
                a -= temp * dist; b -= temp * dist;
                while (Math.min(a, b) < dimension.width) {
                    g2d.drawLine(a, 0, b, dimension.height);
                    a += dist; b += dist;
                }
            }
        }
        // j-lines
        if(jM >= diagM && jM <= -diagM) {
            a = (int) (center[1] - jM * center[0]); b = (int) (center[1] + jM * center[0]);
            dist = (int) (Math.abs(matrix[0][1] - matrix[1][1] * matrix[0][0] / matrix[1][0]) * gridWidth);
            if(dist == 0) g2d.drawLine(0, a, dimension.width, b);
            else {
                int temp = Math.max(a, b) / dist;
                a -= temp * dist; b -= temp * dist;
                while(Math.min(a, b) < dimension.height) {
                    g2d.drawLine(0, a, dimension.width, b);
                    a += dist; b += dist;
                }
            }
        } else {
            a = (int) (center[0] - center[1] / jM); b = (int) (center[0] + center[1] / jM);
            dist = (int) (Math.abs(matrix[0][0] - matrix[1][0] * matrix[0][1] / matrix[1][1]) * gridWidth);
            if(dist == 0) g2d.drawLine(a, 0, b, dimension.height);
            else {
                int temp = Math.max(a, b) / dist;
                a -= temp * dist; b -= temp * dist;
                while (Math.min(a, b) < dimension.width) {
                    g2d.drawLine(a, 0, b, dimension.height);
                    a += dist; b += dist;
                }
            }
        }

        // unit vectors
        if(showVectors) {
            g2d.setStroke(new BasicStroke(lineWidth * 2));
            // i
            g2d.setPaint(Color.RED);
            g2d.drawLine(center[0], center[1], (int) (center[0] + matrix[0][0] * gridWidth), (int) (center[1] - matrix[0][1] * gridWidth));
            // j
            g2d.setPaint(Color.GREEN);
            g2d.drawLine(center[0], center[1], (int) (center[0] + matrix[1][0] * gridWidth), (int) (center[1] - matrix[1][1] * gridWidth));
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        animateTransform();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        matrix[0][0] += iXDiff; matrix[0][1] += iYDiff;
        matrix[1][0] += jXDiff; matrix[1][1] += jYDiff;
        this.repaint();
        frames--;
        if(frames == 0) timer.stop();

    }

}
