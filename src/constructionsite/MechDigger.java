/*
Механизированный копатель ям (экскаватор)
*/
package constructionsite;

/**
 *
 * @author eshibalkin
 */
public class MechDigger extends CaveDigger{

    @Override
    public float getDigDepth() {
        return 1.0f;
    }

    @Override
    public int getDigCost() {
	return 800;
    }
    
    @Override
    public String getImageIconPath() {
        return "images/excavator.jpg";
    }
}
