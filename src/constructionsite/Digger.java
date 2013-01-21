/*
 Копатель ям (типизированный)
 */
package constructionsite;

/**
 *
 * @author eshibalkin
 */
public final class Digger {

    private CaveDigger digger;  // Тип копателя
    float digDepth;       // Глубина ямы в метрах, выкопанной за 1раз
    int digCost;          // Стоимость в USD 1раза копания
    String imageIconPath; // Расположение картинки
    String description;   // Подпись для картинки

    public Digger(CaveDigger diggerType) {
        this.digger = diggerType;
        this.digDepth = digger.getDigDepth();
        this.digCost = digger.getDigCost();
        this.imageIconPath = digger.getImageIconPath();
        this.description = digger.getDescription();
    }

    void dig(Order attributes) {
        digger.dig(attributes);
    }

    boolean isDiggingPossible(Order attributes) {
        return digger.isDiggingPossible(attributes);
    }
}
