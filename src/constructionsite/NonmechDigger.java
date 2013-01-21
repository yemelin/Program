/*
 Не механизированный копатель ям (рабочий)
 */
package constructionsite;

/**
 *
 * @author eshibalkin
 */
public class NonmechDigger extends CaveDigger {

    @Override
    public float getDigDepth() {
        return 0.1f;
    }

    @Override
    public int getDigCost() {
        return 20;
    }

    @Override
    public String getImageIconPath() {
        return "images/shovel.jpg";
    }
}
