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

    final int startMoney = 10000; // Сумма тендера (сколько заплатят после выполнения работы)
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
        money = money - mechDigCost;
        caveDepth = caveDepth + mechDigDepth;
        diggerEnable = (money >= mechDigCost);
        //ShovelEnableJ=(money>=nonmechDigCost);
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

    void shovelButtonAction() {
        money = money - nonmechDigCost;
        caveDepth = caveDepth + nonmechDigDepth;
        //DiggerEnableJ=(money>=mechDigCost);
        shovelEnableJ = (money >= nonmechDigCost);
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
