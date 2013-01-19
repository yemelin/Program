/*
 GUI для игры "Стройка".
 */
package constructionsite;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
//import javax.swing.text.AttributeSet;

/**
 *
 * @author Vasily
 */
public final class SiteGUI extends JFrame {

    GuiAttributes attributes = new GuiAttributes();
    JTextField MoneyText, DepthText;
    JButton ShovelButton, DiggerButton;
    JProgressBar pbar;

//------------    
    SiteGUI() {
//Инициализация текстовой информации о яме и деньгах        
        MoneyText = new JTextField();
        DepthText = new JTextField();
        pbar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
        MoneyText.setEditable(false);
        DepthText.setEditable(false);
        setLayout(new BorderLayout());

//Инициализация кнопок        
        add("North", MoneyText);
        add("South", DepthText);
        add("Center", pbar);
        ImageIcon ShovelIcon = createImageIcon("images/shovel.jpg", "");
        ImageIcon DiggerIcon = createImageIcon("images/excavator.jpg", "");
        ShovelButton = new JButton(ShovelIcon);
        DiggerButton = new JButton(DiggerIcon);

        add("East", ShovelButton);
        add("West", DiggerButton);
//Инициализация прослушивателей нажатия кнопок
        ShovelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attributes.shovelButtonAction();
                updateGUI(attributes);
            }
        });
        DiggerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attributes.diggerButtonAction();
                updateGUI(attributes);
            }
        });
//Инфо о стоимости и глубине разового копания отображается как ToolTip        
        DiggerButton.setToolTipText("Dig " + attributes.mechDigDepth + "m, cost " + attributes.mechDigCost + "$");
        ShovelButton.setToolTipText("Dig " + attributes.nonmechDigDepth + "m, cost " + attributes.nonmechDigCost + "$");
        updateGUI(attributes);

        pack();
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

//----------------------------    
//Отображение информации об атрибутах формы (деньги и глубина ямы (с отражением на прогрессбаре)) 
//установка кнопок в положение disabled по окончании работы программы
    void updateGUI(GuiAttributes attributes) {
        MoneyText.setText("Money: " + attributes.money + "$");
        DepthText.setText("Cave depth: " + attributes.caveDepth + "m");
        DiggerButton.setEnabled(attributes.diggerEnable);
        ShovelButton.setEnabled(attributes.shovelEnableJ);
//Проверка нового значения ProgressBar и установка его         
        int BarValue = (int) (((attributes.startCaveJob - attributes.caveDepth) / attributes.startCaveJob) * 100);
        if ((BarValue <= pbar.getMaximum()) & (BarValue >= pbar.getMinimum())) {
            pbar.setValue(BarValue);
        }
        messenger();
    }
//-----------------------------------------------

    void messenger() {
        if (attributes.result == GuiAttributes.Result.PLAYING) {
        } else if (attributes.result == GuiAttributes.Result.NEW) {
            modalDialog("Congrats, you've won a tender! Dig " + attributes.startCaveJob + "m cave for " + attributes.startMoney + "$.");
            attributes.result = GuiAttributes.Result.PLAYING;
        } else if (attributes.result == GuiAttributes.Result.WIN) {
            modalDialog("Congratulations, job's done! Your profit: " + attributes.money + "$.");
        } else if (attributes.result == GuiAttributes.Result.LOSE) {
            modalDialog("Ran out of money! Job isn't done! See you in arbitrage.");
        } else {
            modalDialog("Error in " + this.getTitle());
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
}
