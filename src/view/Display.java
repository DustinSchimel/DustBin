package view;

import controller.Controller;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class Display extends JComponent implements MouseListener,
        MouseMotionListener, ActionListener, ChangeListener
{
    private Image image;
    private int cellSize;
    private JFrame frame;
    private int tool;
    private int numRows;
    private int numCols;
    private int[] mouseLocation;
    private JButton[] toolButtons;
    private JButton saveButton;
    private JButton loadButton;
    private JButton clearButton;
    private JSlider slider;
    private int speed;
    private Controller controller;

    public Display(String title, int numRows, int numCols, String[] toolButtonNames, Controller controller)
    {
        this.controller = controller;
        this.numRows = numRows;
        this.numCols = numCols;
        tool = 1;
        mouseLocation = null;

        //Determines cell size
        cellSize = Math.max(1, 600 / Math.max(numRows, numCols));
        image = new BufferedImage(numCols * cellSize, numRows * cellSize, BufferedImage.TYPE_INT_RGB);

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));	//Change this later for layout

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        frame.getContentPane().add(topPanel);

        setPreferredSize(new Dimension(numCols * cellSize, numRows * cellSize));
        addMouseListener(this);
        addMouseMotionListener(this);
        topPanel.add(this);

        JPanel toolButtonPanel = new JPanel();
        toolButtonPanel.setLayout(new BoxLayout(toolButtonPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(toolButtonPanel);

        toolButtons = new JButton[toolButtonNames.length];

        for (int i = 0; i < toolButtons.length; i++)
        {
            toolButtons[i] = new JButton(toolButtonNames[i]);
            toolButtons[i].setActionCommand("" + i);
            toolButtons[i].addActionListener(this);
            toolButtonPanel.add(toolButtons[i]);
        }

        toolButtons[tool].setSelected(true);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(buttonPanel);

        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        clearButton = new JButton("Clear");

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(clearButton);

        slider = new JSlider(JSlider.HORIZONTAL, 0, 25, 20);
        slider.addChangeListener(this);
        slider.setMajorTickSpacing(5);
        slider.setSnapToTicks(false);
        slider.setPaintTicks(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(new Integer(0), new JLabel("Pause"));
        labelTable.put(new Integer(25), new JLabel("Hyper Speed"));
        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);

        frame.getContentPane().add(slider);

        speed = computeSpeed(slider.getValue());

        frame.pack();
        frame.setVisible(true);

        setupListeners();
    }

    public void paintComponent(Graphics g)
    {
        g.drawImage(image, 0, 0, null);
    }

    public void pause(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public int[] getMouseLocation()
    {
        return mouseLocation;
    }

    public int getTool()
    {
        return tool;
    }

    public void setColor(int row, int col, Color color)
    {
        Graphics g = image.getGraphics();
        g.setColor(color);
        g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        mouseLocation = toLocation(e);
    }

    public void mouseReleased(MouseEvent e)
    {
        mouseLocation = null;
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseMoved(MouseEvent e)
    {
    }

    public void mouseDragged(MouseEvent e)
    {
        mouseLocation = toLocation(e);
    }

    private int[] toLocation(MouseEvent e)
    {
        int row = e.getY() / cellSize;
        int col = e.getX() / cellSize;

        if (row < 0 || row >= numRows || col < 0 || col >= numCols)
        {
            return null;
        }

        int[] location = new int[2];
        location[0] = row;
        location[1] = col;

        return location;
    }

    public void actionPerformed(ActionEvent e)
    {
        tool = Integer.parseInt(e.getActionCommand());

        for (JButton button : toolButtons)
        {
            button.setSelected(false);
        }

        ((JButton)e.getSource()).setSelected(true);
    }

    public void stateChanged(ChangeEvent e)
    {
        speed = computeSpeed(slider.getValue());
    }

    //Returns number of times to step between repainting and processing mouse input
    public int getSpeed()
    {
        return speed;
    }

    //Returns speed based on sliderValue
    private int computeSpeed(int sliderValue)
    {
        if (sliderValue == 0)
        {
            return 0;
        }
        else
        {
            return (int)Math.pow(10, 0.16 * sliderValue);
        }
    }

    private void setupListeners()
    {
        saveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent click)
            {
                controller.saveLevel();
            }
        });

        loadButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent click)
            {
                controller.loadLevel(numRows, numCols);
            }
        });
        clearButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent click)
            {
                controller.clearLevel();
            }
        });
    }
}

