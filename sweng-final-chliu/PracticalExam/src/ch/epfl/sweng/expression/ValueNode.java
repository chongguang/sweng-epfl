package ch.epfl.sweng.expression;


/**
 * A sub-type of ExpressionNode, representing a constant value.
 * @author SwengStaff
 *
 */
public class ValueNode extends ExpressionNode {

    private final double value;

    public ValueNode(double newValue) {
        super(null, null);
        value = newValue;
    }

    @Override
    public String getLabel() {
        return Double.valueOf(value).toString();
    }

    public double getValue() {
        return value;
    }
}
