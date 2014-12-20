package ch.epfl.sweng.expression;

import ch.epfl.sweng.base.Node;

/**
 * An abstract node representing a mathematical expression.
 * @author SwengStaff
 *
 */
public abstract class ExpressionNode implements Node {

    private ExpressionNode left;
    private ExpressionNode right;

    public ExpressionNode(ExpressionNode newLeft, ExpressionNode newRight) {
        left = newLeft;
        right = newRight;
    }

    @Override
    public ExpressionNode getLeft() {
        return (ExpressionNode) left;
    }

    @Override
    public ExpressionNode getRight() {
        return (ExpressionNode) right;
    }
}
