import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

import Players.*;
import Cards.*;

public class GamePanel extends JPanel implements ActionListener
{
    private Dealer dealer;
    private Player player;
    
    private GameTable table;
    
    private JButton newGameButton = new JButton("Deal");
    private JButton hitButton = new JButton("Hit");
    private JButton doubleButton = new JButton("Double");
	private JButton standButton = new JButton("Stand");
    private JButton add10Chip = new JButton("10");
    private JButton add25Chip = new JButton("25");
    private JButton add50Chip = new JButton("50");
    private JButton add100Chip = new JButton("100");
    private JButton add250Chip = new JButton("250");
    private JButton add500Chip = new JButton("500");
    private JButton clearBet =  new JButton("Clear");
    
    private JLabel currentBet = new JLabel("Please set your bet...");
    private JLabel playerWallet = new JLabel("$999.99");
    private JLabel cardsLeft = new JLabel("Cards left...");
    private JLabel dealerSays = new JLabel("Dealer says...");
    
    public GamePanel()
    {
        this.setLayout(new BorderLayout());
        
        table = new GameTable();
        add(table, BorderLayout.CENTER);
        
        JPanel betPanel = new JPanel();
        betPanel.add(currentBet);
        betPanel.add(clearBet);
        betPanel.add(add10Chip);
        betPanel.add(add25Chip);
        betPanel.add(add50Chip);
        betPanel.add(add100Chip);
        betPanel.add(add250Chip);
        betPanel.add(add500Chip);
        betPanel.add(playerWallet);
        
        JPanel dealerPanel = new JPanel();
        dealerPanel.add(dealerSays);
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.add(newGameButton);
        optionsPanel.add(hitButton);
        optionsPanel.add(doubleButton);
        optionsPanel.add(standButton);
        optionsPanel.add(cardsLeft);
        
        JPanel bottomItems = new JPanel();
        bottomItems.setLayout(new GridLayout(0,1));
        bottomItems.add(dealerPanel);
        bottomItems.add(betPanel);
        bottomItems.add(optionsPanel);
        add(bottomItems, BorderLayout.SOUTH);
        
        // opaque stuff
        //this.setBackground(new Color(6, 120, 0)); // now done in AppWindow.java
        betPanel.setOpaque(false);
        dealerPanel.setOpaque(false);
        optionsPanel.setOpaque(false);
        bottomItems.setOpaque(false);
        
        // add listeners to buttons
        newGameButton.addActionListener(this);
        hitButton.addActionListener(this);
        doubleButton.addActionListener(this);
		standButton.addActionListener(this);
		clearBet.addActionListener(this);
		add10Chip.addActionListener(this);
		add25Chip.addActionListener(this);
		add50Chip.addActionListener(this);
		add100Chip.addActionListener(this);
		add250Chip.addActionListener(this);
        add500Chip.addActionListener(this);

		// tool tips
		newGameButton.setToolTipText("Deal a new game.");
		hitButton.setToolTipText("Request another card.");
		doubleButton.setToolTipText("Double your bet, and receive another card.");
		standButton.setToolTipText("Stand with your card-hand.");
        clearBet.setToolTipText("Clear your current bet.");
        add10Chip.setToolTipText("Add a $10 chip to your current bet.");
        add25Chip.setToolTipText("Add a $25 chip to your current bet.");
        add50Chip.setToolTipText("Add a $50 chip to your current bet.");
        add100Chip.setToolTipText("Add a $100 chip to your current bet.");
        add250Chip.setToolTipText("Add a $250 chip to your current bet.");
        add500Chip.setToolTipText("Add a $500 chip to your current bet.");
		
		dealer = new Dealer();
        player = new Player("Riandhika", 20, "Male");
        player.setWallet(1000.00);
		
        updateValues();
    }
    
    public void actionPerformed(ActionEvent evt)
    {
        String act = evt.getActionCommand();
        
        if (act.equals("Deal"))
        {
            newGame();
        }
        else if (act.equals("Hit"))
        {
            hit();
        }
        else if (act.equals("Double"))
        {
            playDouble();
        }
        else if (act.equals("Stand"))
        {
            stand();
        }
        else if (act.equals("10") || act.equals("25") || act.equals("50") || act.equals("100") || act.equals("250") || act.equals("500"))
        {
            increaseBet(Integer.parseInt(act));
        }
        else if (act.equals("Clear"))
        {
            System.out.println("clear bet");
            clearBet();
        }
        
        updateValues();
    }
    
    public void newGame()
    {
        dealer.deal(player);
    }
    
    public void hit()
    {
        dealer.hit(player);
    }
    
    public void playDouble()
    {
        dealer.playDouble(player);
    }
    
    public void stand()
    {
        dealer.stand(player);
    }
    
    public void increaseBet(int amount)
    {
        dealer.acceptBetFrom(player, amount + player.getBet());
    }
    
    public void clearBet()
    {
        player.clearBet();
    }
    
    public void updateValues()
    {
        dealerSays.setText("<html><p align=\"center\"><font face=\"Serif\" color=\"white\" style=\"font-size: 20pt\">" + dealer.says() + "</font></p></html>");
        
        if (dealer.isGameOver())
        {
            if (player.betPlaced() && !player.isBankrupt())
            {
                newGameButton.setEnabled(true);
            }
            else
            {
                newGameButton.setEnabled(false);
            }
            hitButton.setEnabled(false);
            doubleButton.setEnabled(false);
            standButton.setEnabled(false);
            
            if (player.betPlaced())
            {
                clearBet.setEnabled(true);
            }
            else
            {
                clearBet.setEnabled(false);
            }
            
            if (player.getWallet() >= 10)
            {
                add10Chip.setEnabled(true);
            }
            else
            {
                add10Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 25)
            {
                add25Chip.setEnabled(true);
            }
            else
            {
                add25Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 50)
            {
                add50Chip.setEnabled(true);
            }
            else
            {
                add50Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 100)
            {
                add100Chip.setEnabled(true);
            }
            else
            {
                add100Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 250)
            {
                add250Chip.setEnabled(true);
            }
            else
            {
                add250Chip.setEnabled(false);
            }

            if (player.getWallet() >= 500)
            {
                add500Chip.setEnabled(true);
            }
            else
            {
                add500Chip.setEnabled(false);
            }
        }
        else
        {
            newGameButton.setEnabled(false);
            hitButton.setEnabled(true);
            if (dealer.canPlayerDouble(player))
            {
                doubleButton.setEnabled(true);
            }
            else
            {
                doubleButton.setEnabled(false);
            }
            
            standButton.setEnabled(true);
            
            clearBet.setEnabled(false);
            add10Chip.setEnabled(false);
            add25Chip.setEnabled(false);
            add50Chip.setEnabled(false);
            add100Chip.setEnabled(false);
            add250Chip.setEnabled(false);
            add500Chip.setEnabled(false);
        }
        
        // redraw cards and totals
        table.update(dealer.getHand(), player.getHand(), (dealer.areCardsFaceUp()) ? true : false);
		table.setNames(dealer.getName(), player.getName());
        table.repaint();
        
        cardsLeft.setText("Deck: " + dealer.cardsLeftInPack() + "/" + (dealer.CARD_PACKS * Cards.CardPack.CARDS_IN_PACK));
        
        if (player.isBankrupt())
        {
            moreFunds();
        }
        
        // redraw bet
        currentBet.setText(Double.toString(player.getBet()));
        playerWallet.setText(Double.toString(player.getWallet()));
    }
    
    private void moreFunds()
    {
        int response = JOptionPane.showConfirmDialog(null, "Out of Funds Restocking Your Wallet with 1000.00$.", "Out of funds", JOptionPane.YES_NO_OPTION);
        
        if (response == JOptionPane.YES_OPTION)
        {
            player.setWallet(1000.00);
            updateValues();
        }
    }
    
    public void savePlayer()
	{
	    if (dealer.isGameOver())
	    {
	        JFileChooser playerSaveDialog = new JFileChooser("~");
	        SimpleFileFilter fileFilter = new SimpleFileFilter(".ser", "(.ser) Serialised Files");
	        playerSaveDialog.addChoosableFileFilter(fileFilter);
            int playerSaveResponse = playerSaveDialog.showSaveDialog(this);
        
            if (playerSaveResponse == playerSaveDialog.APPROVE_OPTION)
            {
                String filePath = playerSaveDialog.getSelectedFile().getAbsolutePath();
            
                try
                {
                    ObjectOutputStream playerOut = new ObjectOutputStream(new FileOutputStream(filePath));
                    playerOut.writeObject(player);
                    playerOut.close();
                }
                catch (IOException e)
                {
                    System.out.println(e);
                }
            }
	    }
	    else
	    {
	        JOptionPane.showMessageDialog(this, "Can't save a player while a game is in progress.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    
	}
	
	public void openPlayer()
	{
	    if (dealer.isGameOver())
	    {
	        JFileChooser playerOpenDialog = new JFileChooser("~");
	        SimpleFileFilter fileFilter = new SimpleFileFilter(".ser", "(.ser) Serialised Files");
	        playerOpenDialog.addChoosableFileFilter(fileFilter);
            int playerOpenResponse = playerOpenDialog.showOpenDialog(this);
        
            if (playerOpenResponse == playerOpenDialog.APPROVE_OPTION)
            {
                String filePath = playerOpenDialog.getSelectedFile().getAbsolutePath();
            
                try
                {
                    ObjectInputStream playerIn = new ObjectInputStream(new FileInputStream(filePath));
                    Player openedPlayer = (Player) playerIn.readObject();
                    openedPlayer.hand = new PlayerCardHand();
                    player = openedPlayer;
                    playerIn.close();
                    System.out.println(openedPlayer.getName());
                }
                catch (ClassNotFoundException e)
                {
                    System.err.println(e);
                }
                catch (IOException e)
                {
                    System.err.println(e);
                }
            }
	    }
	    else
	    {
	        JOptionPane.showMessageDialog(this, "Can't open an existing player while a game is in progress.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void updatePlayer()
	{
	    PlayerDialog playerDetails = new PlayerDialog(null, "Player Details", true, player);
        playerDetails.setVisible(true);
        
        player = playerDetails.getPlayer();
	}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        Image bgImage = new ImageIcon("src/images/background.jpg").getImage();
        g.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}