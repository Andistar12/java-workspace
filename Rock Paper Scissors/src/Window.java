import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//Main window, start of program
public class Window extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final String windowTitle = "Rock, Paper, Scissors!";

    private int wins = 0, draws = 0, losses = 0;

    private JPanel contentPane;
    private JTextPane results;

    public static final int window_width = 500, window_height = 500;
    private JPanel masterPanel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Window frame = new Window();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setTitle(windowTitle);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton playerRock = new JButton("Rock");
        playerRock.setBounds(25, 25, 100, 100);
        contentPane.add(playerRock);

        JButton playerPaper = new JButton("Paper");
        playerPaper.setBounds(150, 25, 100, 100);
        contentPane.add(playerPaper);

        JButton playerScissors = new JButton("Scissors");
        playerScissors.setBounds(275, 25, 100, 100);
        contentPane.add(playerScissors);

        results = new JTextPane();
        results.setBounds(25, 150, 350, 125);
        results.setEditable(false);
        contentPane.add(results);

        playerRock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dealWithRPS(RPS.Choices.ROCK);
            }
        });
        playerPaper.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dealWithRPS(RPS.Choices.PAPER);
            }
        });
        playerScissors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dealWithRPS(RPS.Choices.SCISSORS);
            }
        });
    }

    public void dealWithRPS(RPS.Choices player) {

        RPS.Choices cpu = RPS.doRandom();

        RPS.Results result = RPS.isPlayerWon(player, cpu);

        results.setText("You chose " + player.toString()
                + " and the CPU chose " + cpu.toString() + ".\n");

        switch (result) {
            case WIN:
                results.setText(results.getText() + "You win!\n");
                wins++;
                break;
            case DRAW:
                results.setText(results.getText() + "You drew!\n");
                draws++;
                break;
            case LOSE:
                results.setText(results.getText() + "You lost!\n");
                losses++;
                break;
        }

        results.setText(results.getText() + "\nTotal wins: " + wins
                + "\nTotal draws: " + draws
                + "\nTotal losses: " + losses);
    }
}