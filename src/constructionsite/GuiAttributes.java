/*
Логика для игры "Стройка"
*/
package constructionsite;

/**
 *
 * @author eshibalkin
 */
public class GuiAttributes {
    // Константы для инициализации

    final int startMoney = 3000; // Сумма тендера (сколько заплатят после выполнения работы)
    final int startCaveJob = 10;  // Объем работы (требуемая глубина ямы)
    final float mechDigDepth = 1.0f; // Глубина ямы в метрах, выкопанной за 1раз экскаватором
    final int mechDigCost = 800;     // Стоимость в USD 1раза копания экскаватором
    final float nonmechDigDepth = 0.1f; // Глубина ямы в метрах, выкопанной за 1раз вручную
    final int nonmechDigCost = 20;      // Стоимость в USD 1раза копания вручную
    // Переменные
    int money = startMoney;     // Текущее количество денег
    float caveDepth = 0; // Текущая глубина ямы
    boolean diggerEnable = true; // Управление кнопкой с экскаватором (true - заблокировать)
    boolean shovelEnableJ = true;

    enum Result {

        NEW, PLAYING, WIN, LOSE
    }
    Result result = Result.NEW;

    void diggerButtonAction() {
        genericButtonAction(mechDigCost,mechDigDepth);
    }

    void shovelButtonAction() {
        genericButtonAction(nonmechDigCost,nonmechDigDepth);
    }
    
    void genericButtonAction(int DigCost, float DigDepth){
        money = money - DigCost;
//float precision handling        
        caveDepth = (float)(Math.round((caveDepth + DigDepth)*10))/10;
        diggerEnable=(money>=mechDigCost);
        shovelEnableJ = (money >= nonmechDigCost);
        checkResult();
    }
    
    void checkResult(){
        if (startCaveJob - caveDepth <= 0) {
            caveDepth = startCaveJob; // потому, что глубина должна быть не больше заказанной, а меньше 1раза оплачивается, как 1 раз
            diggerEnable = false;
            shovelEnableJ = false;
            result = Result.WIN;
        } else {
            if (money < nonmechDigCost) {
                diggerEnable = false;
                shovelEnableJ = false;
                result = Result.LOSE;
            }
        }
    }
}
