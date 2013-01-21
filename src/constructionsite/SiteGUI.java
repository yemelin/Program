/*
 GUI для игры "Стройка".
 */
package constructionsite;

import constructionsite.Order.Stage;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 *
 * @author Vasily
 */
public final class SiteGUI extends JFrame {

    Order order = new Order();                               // Экземпляр заказа (выигранного по тендеру)
    Digger mechDigger = new Digger(new MechDigger());        // Экземпляр экскаватора, которым можно копать
    Digger nonmechDigger = new Digger(new NonmechDigger());  // Экземпляр рабочего, который может копать
    LocalTimer timer;    // Таймер обратного отсчета срока выполнения заказа
    JTextField MoneyText, DepthText, TimeText;
    JButton ShovelButton, DiggerButton;
    JProgressBar pbar;

//------------    
    SiteGUI() {
//Инициализация текстовой информации о яме и деньгах        
        JPanel TextPanel = new JPanel();
        TextPanel.setLayout(new BorderLayout());
        MoneyText = new JTextField();
        DepthText = new JTextField();
        TimeText = new JTextField();
        pbar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
        MoneyText.setEditable(false);
        DepthText.setEditable(false);
        TimeText.setEditable(false);
        setLayout(new BorderLayout());

//Инициализация кнопок        
        TextPanel.add("North", MoneyText);
        TextPanel.add("South", DepthText);
        add("North", TextPanel);
        add("South", TimeText);
        add("Center", pbar);
        ImageIcon ShovelIcon = createImageIcon(nonmechDigger.imageIconPath, "");
        ImageIcon DiggerIcon = createImageIcon(mechDigger.imageIconPath, "");
        ShovelButton = new JButton(ShovelIcon);
        DiggerButton = new JButton(DiggerIcon);

        add("East", ShovelButton);
        add("West", DiggerButton);
//Инициализация прослушивателей нажатия кнопок
        ShovelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nonmechDigger.dig(order);
                updateGUI();
            }
        });
        DiggerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mechDigger.dig(order);
                updateGUI();
            }
        });
//Инфо о стоимости и глубине разового копания отображается как ToolTip        
        DiggerButton.setToolTipText(mechDigger.description);
        ShovelButton.setToolTipText(nonmechDigger.description);
        updateGUI();
        
//Создание панели меню и отдельных меню
        JMenuBar menubar = new JMenuBar();
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");

        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {               
                modalDialog("ShYsoft inc.(c)");
            }
        });
        help.add(about);
        
        JMenu file = new JMenu("File");
        JMenuItem newgame = new JMenuItem("New tender");

        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {               
                restart();
            }
        });
        file.add(newgame);
                
        menubar.add(file);
        menubar.add(help);
        setJMenuBar(menubar);
//----------------
        pack();
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer = new LocalTimer();

    }
    
    void restart(){
        timer.stop();
        order.refresh();
        updateGUI();
        timer=new LocalTimer();
    }

//----------------------------    
//Отображение информации об атрибутах формы (деньги и глубина ямы (с отражением на прогрессбаре)) 
//установка кнопок в положение disabled при нехватке денег и по окончании работы программы
    void updateGUI() {
        MoneyText.setText("Money: " + order.money + "$");
        DepthText.setText("Cave depth: " + order.caveDepth + "m");
        DiggerButton.setEnabled(mechDigger.isDiggingPossible(order));
        ShovelButton.setEnabled(nonmechDigger.isDiggingPossible(order));
//Проверка нового значения ProgressBar и установка его         
        int BarValue = (int) (((order.startCaveJob - order.caveDepth) / order.startCaveJob) * 100);
        if ((BarValue <= pbar.getMaximum()) & (BarValue >= pbar.getMinimum())) {
            pbar.setValue(BarValue);
        }
        
        Stage orderStage = order.getStage();
        if (orderStage == Stage.PROCESSING) {
        } else if (orderStage == Stage.NEW) {
            modalDialog("Congrats, you've won a tender! Dig " + order.startCaveJob + "m cave for " + order.startMoney + "$.");
        } else {
            timer.stop();
            DiggerButton.setEnabled(false);
            ShovelButton.setEnabled(false);
            if (orderStage == Stage.WIN) {
                modalDialog("Congratulations, job's done! Your profit: " + order.money + "$.");
            } else if (orderStage == Stage.LOSE) {
                modalDialog("Job isn't done! See you in arbitrage.");
            } else {
                modalDialog("Error in " + this.getTitle());
            }
        }
    }

//-----------------
//Вывод модального окна с текстом s и кнопкой OK    
    void modalDialog(String s) {
        final JDialog jd = new JDialog(this, true);
        JButton OKButton = new JButton("OK");
        jd.add("North", new JLabel(s));
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.dispose();
            }
        });
        jd.add("Center", OKButton);
        jd.pack();
        jd.setVisible(true);
    }

    //Функция для задания иконки    
    protected ImageIcon createImageIcon(String path,
            String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
//primitive timer    

    class LocalTimer implements Runnable {

        Thread t;
        boolean running = true;

        LocalTimer() {
            t = new Thread(this);
            t.start();
        }

        public void run() {
            while (running && order.time >= 0) {
                TimeText.setText(Integer.toString(order.time));
                try {
                    t.sleep(1000);
                } catch (InterruptedException e) {
                }
                order.time--;
            }
            if (running) {
                updateGUI();
            }
        }

        void stop() {
            running = false;
        }
    }
}
