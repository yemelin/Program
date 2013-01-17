/*
 GUI для "Стройки".
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

/**
 *
 * @author Vasily
 */
public class SiteGUI extends JFrame {
    JTextField MoneyText,DepthText;
    JButton ShovelButton, DiggerButton;
    JProgressBar pbar;

//Этих глобальных переменных не должно быть в этом классе, отвечающем
//только за интерфейс    
    int Money=10000, Prize=3000; 
    float CaveJob=10;  //Сколько еще нужно копать
    float MaxDepth=CaveJob;
    int ShovelCost=20, DiggerCost=800;
    float ShovelDig=0.1f,DiggerDig=1f;
//------------    
    SiteGUI(){
//Инициализация текстовой информации о яме и деньгах        
        MoneyText=new JTextField("Money:");
        DepthText=new JTextField("Cave depth:");
        pbar=new JProgressBar(JProgressBar.VERTICAL,0,100);
        MoneyText.setEditable(false);
        DepthText.setEditable(false);
        setLayout(new BorderLayout());

//Инициализация кнопок        
        add ("North",MoneyText);
        add ("South",DepthText);
        add ("Center", pbar);
        ImageIcon ShovelIcon=createImageIcon("images/shovel.jpg","");
        ImageIcon DiggerIcon=createImageIcon("images/excavator.jpg","");
        ShovelButton=new JButton(ShovelIcon);       
        DiggerButton=new JButton(DiggerIcon);
        
        add ("East", ShovelButton);
        add ("West", DiggerButton);
//Инициализация прослушивателей нажатия кнопок
        ShovelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                shovelButtonAction();                                
            }
        });
        DiggerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                diggerButtonAction();                
            }
        });
//Инфо о стоимости и глубине разового копания отображается как ToolTip        
        DiggerButton.setToolTipText("Dig 1m, cost 800");
        ShovelButton.setToolTipText("Dig 0.1m, cost 20");                        
        
        pack();
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
//-----------------------------------------------
//Реакция на кнопки
//Эти две функции должны быть переписаны. Логика должна реализовываться в другом
//классе, а здесь должна быть только ссылка на нее и обновление gui    
    void diggerButtonAction(){
        Money=Money-DiggerCost;
        CaveJob=CaveJob-DiggerDig;
        updateGUI(Money, MaxDepth-CaveJob,(int)((CaveJob/MaxDepth)*100),
                    Money<DiggerCost,Money<ShovelCost);
        if (CaveJob<=0) {
            modalDialog("Congratulations, job's done!");
            Money+=Prize;
            updateGUI(Money, 0,true,true);
        }
        else {
            if (Money<ShovelCost) {
                modalDialog("Game over");           
                updateGUI(Money, 0,true,true);
            }
        }                
    }
    void shovelButtonAction(){ 
        Money=Money-ShovelCost;
        CaveJob=CaveJob-ShovelDig;
        updateGUI(Money, MaxDepth-CaveJob,(int)((CaveJob/MaxDepth)*100),
                    Money<DiggerCost,Money<ShovelCost);        
        if (CaveJob<=0) {
            modalDialog("Congratulations, job's done!");
            Money+=Prize;
            updateGUI(Money, 0, 100, true,true);
        }
        else {
            if (Money<ShovelCost) {
                modalDialog("Game over");
                updateGUI(Money, 0, 100, true,true);                
            }
        }    
                        
    }
    
//----------------------------    
//Отображение информации о деньгах и глубине ямы в соответствии с первыми двумя 
//параметрами, установка кнопок в положения enabled/disabled в зависимости от 
//соответствующих параметров boolean
//Старая версия, без значения для ПрогрессБара

//TODO: Скопировать тело функции в перегруженную ниже, а эту - удалить    
    void updateGUI(int Money, float CaveDepth, boolean DiggerDisable, boolean ShovelDisable){
        MoneyText.setText("Money: "+Money+"$");
        DepthText.setText("Cave depth: "+CaveDepth+"m");
        if (DiggerDisable) {
            DiggerButton.setEnabled(false);
        }
        else {
            DiggerButton.setEnabled(true);
        }
        if (ShovelDisable) {
            ShovelButton.setEnabled(false);
        }
        else {
            ShovelButton.setEnabled(true);
        }
//-----------------
//Новая версия (временно перегруженная). См.выше + BarValue - значение
//для ProgressBar        
    }
    void updateGUI(int Money, float CaveDepth, int BarValue, 
            boolean DiggerDisable, boolean ShovelDisable){
        updateGUI(Money,CaveDepth,DiggerDisable,ShovelDisable);
//Проверка нового значения ProgressBar и установка его         
        if ((BarValue<=pbar.getMaximum()) & (BarValue>=pbar.getMinimum())) {
            pbar.setValue(BarValue);
        }
        //        pbar.setValue((int)(((CaveDepth)/10)*100));
    }
//Вывод модального окна с текстом s и кнопкой OK    
    void modalDialog(String s){        
        final JDialog jd=new JDialog(this,true);        
        JButton OKButton=new JButton("OK");
        jd.add("North",new JLabel(s));
        OKButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                jd.dispose();
            }
        });
        jd.add("Center",OKButton);
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