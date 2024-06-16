import javax.swing.UIManager;

public class Blackjack
{
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        
        new AppWindow();
    }
}
