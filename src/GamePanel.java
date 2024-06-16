import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

import Players.*;
import Cards.*;

public class GamePanel extends JPanel implements ActionListener {
    private Dealer dealer;
    private Player player;
    
    private GameTable table;
    
    // Buttons with images
    private JButton newGameButton;
    private JButton hitButton;
    private JButton doubleButton;
    private JButton standButton;
    private JButton add10Chip;
    private JButton add25Chip;
    private JButton add50Chip;
    private JButton add100Chip;
    private JButton add500Chip;
    private JButton clearBet;
    
    private JLabel currentBet = createFancyLabel("Please set your bet...");
    private JLabel playerWallet = createFancyLabel("$999.99");
    private JLabel cardsLeft = createFancyLabel("Cards left...");
    private JLabel dealerSays = new JLabel("Dealer says...");
    
    public GamePanel() {
        this.setLayout(new BorderLayout());
        
        table = new GameTable();
        add(table, BorderLayout.CENTER);
        
        JPanel betPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margin around components
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        betPanel.add(currentBet, gbc);
        
        clearBet = createImageButton("src/images/clear.png", "Clear");
        clearBet.setToolTipText("Clear your current Bet.");
        gbc.gridx = 1;
        gbc.gridy = 0;
        betPanel.add(clearBet, gbc);
    
        add10Chip = createImageButton("src/images/chip10.png", "10");
        add10Chip.setToolTipText("Add $10 to your bet.");
        gbc.gridx = 2;
        gbc.gridy = 0;
        betPanel.add(add10Chip, gbc);

        add25Chip = createImageButton("src/images/chip25.png", "25");
        add25Chip.setToolTipText("Add $25 to your bet.");
        gbc.gridx = 3;
        gbc.gridy = 0;
        betPanel.add(add25Chip, gbc);

        add50Chip = createImageButton("src/images/chip50.png", "50");
        add50Chip.setToolTipText("Add $50 to your bet.");
        gbc.gridx = 4;
        gbc.gridy = 0;
        betPanel.add(add50Chip, gbc);

        add100Chip = createImageButton("src/images/chip100.png", "100");
        add100Chip.setToolTipText("Add $100 to your bet.");
        gbc.gridx = 5;
        gbc.gridy = 0;
        betPanel.add(add100Chip, gbc);

        add500Chip = createImageButton("src/images/chip500.png", "500");
        add500Chip.setToolTipText("Add $500 to your bet.");
        gbc.gridx = 6;
        gbc.gridy = 0;
        betPanel.add(add500Chip, gbc);
        
        gbc.gridx = 7;
        gbc.gridy = 0;
        betPanel.add(playerWallet, gbc);
        
        JPanel dealerPanel = new JPanel();
        dealerPanel.add(dealerSays);
        
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        
        newGameButton = createImageButton("src/images/deal.png", "Deal");
        newGameButton.setToolTipText("Deal a new game.");
        gbc.gridx = 0;
        gbc.gridy = 0;
        optionsPanel.add(newGameButton, gbc);
        
        hitButton = createImageButton("src/images/hit.png", "Hit");
        hitButton.setToolTipText("Request another card.");
        gbc.gridx = 1;
        gbc.gridy = 0;
        optionsPanel.add(hitButton, gbc);
        
        doubleButton = createImageButton("src/images/double.png", "Double");
        doubleButton.setToolTipText("Double your bet and receive another card.");
        gbc.gridx = 2;
        gbc.gridy = 0;
        optionsPanel.add(doubleButton, gbc);
        
        standButton = createImageButton("src/images/stand.png", "Stand");
        standButton.setToolTipText("Stand with your card-hand.");
        gbc.gridx = 3;
        gbc.gridy = 0;
        optionsPanel.add(standButton, gbc);
        
        gbc.gridx = 4;
        gbc.gridy = 0;
        optionsPanel.add(cardsLeft, gbc);
        
        JPanel bottomItems = new JPanel();
        bottomItems.setLayout(new GridLayout(0, 1));
        bottomItems.add(dealerPanel);
        bottomItems.add(betPanel);
        bottomItems.add(optionsPanel);
        add(bottomItems, BorderLayout.SOUTH);
        
        // Set transparent background
        betPanel.setOpaque(false);
        dealerPanel.setOpaque(false);
        optionsPanel.setOpaque(false);
        bottomItems.setOpaque(false);
        
        // Add action listeners
        newGameButton.addActionListener(this);
        hitButton.addActionListener(this);
        doubleButton.addActionListener(this);
        standButton.addActionListener(this);
        clearBet.addActionListener(this);
        add10Chip.addActionListener(this);
        add25Chip.addActionListener(this);
        add50Chip.addActionListener(this);
        add100Chip.addActionListener(this);
        add500Chip.addActionListener(this);
        
        // Initialize dealer and player
        dealer = new Dealer();
        player = new Player("Riandhika", 20, "Male");
        player.setWallet(1000.00);
        
        // Update initial values
        updateValues();
    }
    
    public void actionPerformed(ActionEvent evt) {
        String act = evt.getActionCommand();
        
        if (act.equals("Deal")) {
            newGame();
        } else if (act.equals("Hit")) {
            hit();
        } else if (act.equals("Double")) {
            playDouble();
        } else if (act.equals("Stand")) {
            stand();
        } else if (act.equals("10") || act.equals("25") || act.equals("50") || act.equals("100") || act.equals("500")) {
            increaseBet(Integer.parseInt(act));
        } else if (act.equals("Clear")) {
            clearBet();
        }
        
        updateValues();
    }
    
    public void newGame() {
        dealer.deal(player);
    }
    
    public void hit() {
        dealer.hit(player);
    }
    
    public void playDouble() {
        dealer.playDouble(player);
    }
    
    public void stand() {
        dealer.stand(player);
    }
    
    public void increaseBet(int amount) {
        dealer.acceptBetFrom(player, amount + player.getBet());
    }
    
    public void clearBet() {
        player.clearBet();
    }
    
    public void updateValues() {
        dealerSays.setText("<html><p align=\"center\"><font face=\"Serif\" color=\"white\" style=\"font-size: 20pt\">" + dealer.says() + "</font></p></html>");
        
        if (dealer.isGameOver()) {
            if (player.betPlaced() && !player.isBankrupt()) {
                newGameButton.setEnabled(true);
            } else {
                newGameButton.setEnabled(false);
            }
            hitButton.setEnabled(false);
            doubleButton.setEnabled(false);
            standButton.setEnabled(false);
            
            if (player.betPlaced()) {
                clearBet.setEnabled(true);
            } else {
                clearBet.setEnabled(false);
            }
            
            if (player.getWallet() >= 10) {
                add10Chip.setEnabled(true);
            } else {
                add10Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 25) {
                add25Chip.setEnabled(true);
            } else {
                add25Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 50) {
                add50Chip.setEnabled(true);
            } else {
                add50Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 100) {
                add100Chip.setEnabled(true);
            } else {
                add100Chip.setEnabled(false);
            }

            if (player.getWallet() >= 500) {
                add500Chip.setEnabled(true);
            } else {
                add500Chip.setEnabled(false);
            }
        } else {
            newGameButton.setEnabled(false);
            hitButton.setEnabled(true);
            if (dealer.canPlayerDouble(player)) {
                doubleButton.setEnabled(true);
            } else {
                doubleButton.setEnabled(false);
            }
            
            standButton.setEnabled(true);
            
            clearBet.setEnabled(false);
            add10Chip.setEnabled(false);
            add25Chip.setEnabled(false);
            add50Chip.setEnabled(false);
            add100Chip.setEnabled(false);
            add500Chip.setEnabled(false);
        }
        
        // Redraw cards and totals
        table.update(dealer.getHand(), player.getHand(), dealer.areCardsFaceUp());
        table.setNames(dealer.getName(), player.getName());
        table.repaint();
        
        cardsLeft.setText("Deck: " + dealer.cardsLeftInPack() + "/" + (dealer.CARD_PACKS * Cards.CardPack.CARDS_IN_PACK));
        
        if (player.isBankrupt()) {
            moreFunds();
        }
        
        // Redraw bet
        currentBet.setText(Double.toString(player.getBet()));
        playerWallet.setText(Double.toString(player.getWallet()));
    }
    
    private void moreFunds() {
        int response = JOptionPane.showConfirmDialog(null, "Out of Funds Restocking Your Wallet with 1000.00$.", "Out of funds", JOptionPane.YES_NO_OPTION);
        
        if (response == JOptionPane.YES_OPTION) {
            player.setWallet(1000.00);
            updateValues();
        }
    }
    
    public void savePlayer() {
        if (dealer.isGameOver()) {
            JFileChooser playerSaveDialog = new JFileChooser("~");
            SimpleFileFilter fileFilter = new SimpleFileFilter(".ser", "(.ser) Serialised Files");
            playerSaveDialog.addChoosableFileFilter(fileFilter);
            int playerSaveResponse = playerSaveDialog.showSaveDialog(this);
        
            if (playerSaveResponse == playerSaveDialog.APPROVE_OPTION) {
                String filePath = playerSaveDialog.getSelectedFile().getAbsolutePath();
            
                try {
                    ObjectOutputStream playerOut = new ObjectOutputStream(new FileOutputStream(filePath));
                    playerOut.writeObject(player);
                    playerOut.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Can't save a player while a game is in progress.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void openPlayer() {
        if (dealer.isGameOver()) {
            JFileChooser playerOpenDialog = new JFileChooser("~");
            SimpleFileFilter fileFilter = new SimpleFileFilter(".ser", "(.ser) Serialised Files");
            playerOpenDialog.addChoosableFileFilter(fileFilter);
            int playerOpenResponse = playerOpenDialog.showOpenDialog(this);
        
            if (playerOpenResponse == playerOpenDialog.APPROVE_OPTION) {
                String filePath = playerOpenDialog.getSelectedFile().getAbsolutePath();
            
                try {
                    ObjectInputStream playerIn = new ObjectInputStream(new FileInputStream(filePath));
                    player = (Player) playerIn.readObject();
                    playerIn.close();
                } catch (IOException e) {
                    System.out.println(e);
                } catch (ClassNotFoundException e) {
                    System.out.println(e);
                }
            }
        
            updateValues();
        } else {
            JOptionPane.showMessageDialog(this, "Can't open a player while a game is in progress.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void updatePlayer() {
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
    
    // Method to create image button without background
    private JButton createImageButton(String imagePath, String actionCommand) {
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(imagePath);
        button.setIcon(icon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.setActionCommand(actionCommand); // Set action command
        return button;
    }
    
    // Method to create fancy label
    private JLabel createFancyLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label.setOpaque(false);
        return label;
    }
}
