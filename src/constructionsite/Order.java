/*
 Заказ (выигранный по тендеру)
 */
package constructionsite;

/**
 *
 * @author eshibalkin
 */
public class Order {
    // Константы для инициализации

    final int startMoney = 5000;  // Сумма тендера, USD (сколько заплатят после выполнения работы)
    final int startCaveJob = 10;  // Объем работы, м (требуемая глубина ямы)
    final int startTime = 30;    // Время на выполнение заказа, мин
    // Переменные
    int money = startMoney;     // Текущее количество денег
    float caveDepth = 0;        // Текущая глубина ямы
    int time = startTime;       // Оставшееся время на выполнение заказа

    enum Stage {
        // статус в котором находится заказ

        NEW, // Новый зааз
        PROCESSING, // Заказ в процессе выполнения
        WIN, // Заказ выполнен
        LOSE        // Выполнение заказа сорвано
    }

    Order.Stage getStage() {

        if (startCaveJob - caveDepth <= 0) {
            return Order.Stage.WIN;
        } else {
            if ((money <= 0) || (time <= 0)) {
                return Order.Stage.LOSE;
            } else {
                if (caveDepth == 0) {
                    return Order.Stage.NEW;
                } else {
                    return Order.Stage.PROCESSING;
                }
            }
        }
    }

    void refresh() {

        money = startMoney;     // Текущее количество денег
        caveDepth = 0;        // Текущая глубина ямы
        time = startTime;       // Оставшееся время на выполнение заказа

    }
}
