package ch.epfl.sweng.expression;


/**
 * A sub-type of ExpressionNode, representing the addition of two expressions.
 * @author SwengStaff
 *
 */
public class AddNode extends ExpressionNode {

    public AddNode(ExpressionNode newLeft, ExpressionNode newRight) {
        super(newLeft, newRight);
    }

    @Override
    public String getLabel() {
        return "+";
    }
}
