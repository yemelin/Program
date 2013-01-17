package constructionsite;
/** Пример работы с функциями SiteGUI
 *
 * @author Vasily
 */

public class ConstructionSite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SiteGUI gui=new SiteGUI();
        gui.modalDialog("Congrats, you've won a tender!");
//Переменные в вызове следующей функции должны извлекаться не из класса
//SiteGUI, а из реализуещего логику класса        
        gui.updateGUI(gui.Money, gui.MaxDepth-gui.CaveJob, 
                (int)((gui.CaveJob/gui.MaxDepth)*100),false, false);
    }
}
