package ch.epfl.sweng.expression;

/**
 * A sub-type of ExpressionNode, representing the addition of two expressions.
 * @author SwengStaff
 *
 */
public class MultiplyNode extends ExpressionNode {

    public MultiplyNode(ExpressionNode newLeft, ExpressionNode newRight) {
        super(newLeft, newRight);
    }

    @Override
    public String getLabel() {
        return "*";
    }
}
