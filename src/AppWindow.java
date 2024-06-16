import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Application window.
 * Holds the menu-bar etc.
 *
 * @author Daffa Riandhika
 */
public class AppWindow extends JFrame 
    implements ActionListener, ComponentListener
{
    private GamePanel gamePanel;
    private Color defaultTableColour = new Color(6, 120, 0);
    
    private JMenuItem savePlayer = new JMenuItem("Save Current Player");
    private JMenuItem openPlayer = new JMenuItem("Open Existing Player");
    
    final int WIDTH = 614;
    final int HEIGHT = 511;

    public AppWindow()
    {
        super("Blackjack");
        
        addComponentListener(this);
        
        Dimension windowSize = new Dimension(WIDTH, HEIGHT);
        setSize(windowSize);
        setLocationRelativeTo(null); // put game in centre of screen
        
        this.setBackground(defaultTableColour);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Disable window resizing
        
        // Remove maximize button
        setMaximizeButtonDisabled();
        
        // menu bar
        JMenuBar menuBar = new JMenuBar();
        
        JMenu playerMenu = new JMenu("Player");
        JMenuItem updatePlayerDetails = new JMenuItem("Update Player Details");
        playerMenu.add(updatePlayerDetails);
        playerMenu.addSeparator();
        playerMenu.add(savePlayer);
        playerMenu.add(openPlayer);
        menuBar.add(playerMenu);
        
        JMenu actionMenu = new JMenu("Actions");
        JMenuItem dealAction = new JMenuItem("Deal");
        JMenuItem hitAction = new JMenuItem("Hit");
        JMenuItem doubleAction = new JMenuItem("Double");
        JMenuItem standAction = new JMenuItem("Stand");
        actionMenu.add(dealAction);
        actionMenu.add(hitAction);
        actionMenu.add(doubleAction);
        actionMenu.add(standAction);
        menuBar.add(actionMenu);
        
        JMenu betMenu = new JMenu("Bet");
        JMenuItem tenChip = new JMenuItem("$10");
        JMenuItem twentyfiveChip = new JMenuItem("$25");
        JMenuItem fiftyChip = new JMenuItem("$50");
        JMenuItem hundredChip = new JMenuItem("$100");
        JMenuItem fivehundredChip = new JMenuItem("$500");
        betMenu.add(tenChip);
        betMenu.add(twentyfiveChip);
        betMenu.add(fiftyChip);
        betMenu.add(hundredChip);
        betMenu.add(fivehundredChip);
        menuBar.add(betMenu);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpBlackjackRulesMenu = new JMenuItem("Blackjack Rules");
        JMenuItem helpAboutMenu = new JMenuItem("About Blackjack");
        helpMenu.add(helpBlackjackRulesMenu);
        helpMenu.addSeparator();
        helpMenu.add(helpAboutMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
        
        // keyboard shortcuts
        
        updatePlayerDetails.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_U,                                            
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        savePlayer.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_S,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        openPlayer.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_O,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));   
        dealAction.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_N,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        hitAction.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_C,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        doubleAction.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_D,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        standAction.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_F,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        tenChip.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_1,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        twentyfiveChip.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_2,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fiftyChip.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_3,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        hundredChip.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_4,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fivehundredChip.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_5,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        
        // action listeners
        dealAction.addActionListener(this);
        hitAction.addActionListener(this);
        doubleAction.addActionListener(this);
        standAction.addActionListener(this);
        updatePlayerDetails.addActionListener(this);
        savePlayer.addActionListener(this);
        openPlayer.addActionListener(this);
        helpAboutMenu.addActionListener(this);
        helpBlackjackRulesMenu.addActionListener(this);
        tenChip.addActionListener(this);
        twentyfiveChip.addActionListener(this);
        fiftyChip.addActionListener(this);
        hundredChip.addActionListener(this);
        fivehundredChip.addActionListener(this);
                
        gamePanel = new GamePanel();
        gamePanel.setBackground(defaultTableColour);
        add(gamePanel);
        
        setVisible(true);
    }

    private void setMaximizeButtonDisabled() {
        // Disable maximize button
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        // Reapply decorations to disable the maximize button
        setUndecorated(false);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
    }

    public void actionPerformed(ActionEvent evt)
    {
        String act = evt.getActionCommand();
        
        if (act.equals("$10"))
        {
            gamePanel.increaseBet(10);
        }
        else if (act.equals("$25"))
        {
            gamePanel.increaseBet(25);
        }
        else if (act.equals("$50"))
        {
            gamePanel.increaseBet(50);
        }
        else if (act.equals("$100"))
        {
            gamePanel.increaseBet(100);
        }
        else if (act.equals("$500"))
        {
            gamePanel.increaseBet(500);
        }
        else if (act.equals("Deal"))
        {
            gamePanel.newGame();
        }
        else if (act.equals("Hit"))
        {
            gamePanel.hit();
        }
        else if (act.equals("Double"))
        {
            gamePanel.playDouble();
        }
        else if (act.equals("Stand"))
        {
            gamePanel.stand();
        }
        else if (act.equals("Update Player Details"))
        {
            gamePanel.updatePlayer();
        }
        else if (act.equals("Save Current Player"))
        {
            gamePanel.savePlayer();
        }
        else if (act.equals("Open Existing Player"))
        {
            gamePanel.openPlayer();
        }
        else if (act.equals("About Blackjack"))
        {
            String aboutText = "<html><div style='width: 300px; text-align: center;'>" +
                   "<h2>About Blackjack</h2>" +
                   "<p><b>Written by:</b> Daffa Riandhika 2024</p>" +
                   "<p><b>Version:</b> 1.0</p>" +
                   "<p style='padding-bottom: 10px;'><small>This program was developed as part of my college assignment in Information Systems.</small></p>" +
                   "<p><b>Contact:</b> Email: muhammaddaffariandhika@gmail.com</p></div></html>";
            JOptionPane.showMessageDialog(this, aboutText, "About Blackjack", JOptionPane.PLAIN_MESSAGE);

        }
        else if (act.equals("Blackjack Rules"))
        {
            String rulesText = "<html><div style='width: 300px; text-align: center;'>" +
                   "<h2>Blackjack Rules</h2>" +
                   "<p><b>Goal:</b> Beat the dealer's hand without going over 21.</p>" +
                   "<p><b>Card Values:</b> Face cards are worth 10. Aces are worth 1 or 11.</p>" +
                   "<p><b>Starting:</b> Each player gets two cards; one of the dealer's is hidden.</p>" +
                   "<p><b>Actions:</b> 'Hit' to get another card, 'Stand' to keep your total.</p>" +
                   "<p><b>Bust:</b> Going over 21 means you lose, regardless of the dealer.</p>" +
                   "<p><b>Blackjack:</b> An Ace and a 10-value card; usually pays 1.5x your bet.</p>" +
                   "<p><b>Dealer:</b> Hits until reaching 17 or higher.</p></div></html>";
            JOptionPane.showMessageDialog(this, rulesText, "Blackjack Rules", JOptionPane.PLAIN_MESSAGE);

        }
        
        gamePanel.updateValues();
    }
    
    public void componentResized(ComponentEvent e)
    {
        int currentWidth = getWidth();
        int currentHeight = getHeight();
        
        boolean resize = false;
        
        if (currentWidth < WIDTH)
        {
            resize = true;
            currentWidth = WIDTH;
        }
        
        if (currentHeight < HEIGHT)
        {
            resize = true;
            currentHeight = HEIGHT;
        }
        
        if (resize)
        {
            setSize(currentWidth, currentHeight);
        }
    }
    
    public void componentMoved(ComponentEvent e) { }
    public void componentShown(ComponentEvent e) { }
    public void componentHidden(ComponentEvent e) { }
}
