/*
 Копатель ям
 */
package constructionsite;

/**
 *
 * @author eshibalkin
 */
public abstract class CaveDigger {

    public abstract float getDigDepth(); // Глубина ямы в метрах, выкопанной за 1раз

    public abstract int getDigCost();    // Стоимость в USD 1раза копания

    public final boolean isDiggingPossible(Order attributes) { // Проверка наличия денег для оплаты копания
        return attributes.money >= getDigCost();
    }

    public final void dig(Order attributes) { // Функция копания
        if (isDiggingPossible(attributes)) {
            attributes.money = attributes.money - getDigCost();
            // float precision handling
            attributes.caveDepth = (float)(Math.round((attributes.caveDepth + getDigDepth())*10))/10;
            // глубина должна быть не больше заказанной, а меньше 1раза оплачивается, как 1 раз
            if (attributes.caveDepth > attributes.startCaveJob) {
                attributes.caveDepth = attributes.startCaveJob;
            }
        } else {
            System.err.println("Error: Button must been deactivated, but it didn'n!");
        }
    }

    public abstract String getImageIconPath(); // Расположение картинки

    public final String getDescription() {   // Подпись для картинки
        return "Dig " + getDigDepth() + "m, cost " + getDigCost() + "$";
    }
}
