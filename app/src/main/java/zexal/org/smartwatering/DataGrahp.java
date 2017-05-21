package zexal.org.smartwatering;

/**
 * Created by Ilham on 5/19/2017.
 */

public class DataGrahp {
    String label;
    float value;

    public DataGrahp(String label, float value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
