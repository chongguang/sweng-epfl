package ch.epfl.sweng.test;

import ch.epfl.sweng.base.Tree;
import ch.epfl.sweng.string.StringNode;

/**
 * Wrapper for static helper functions. Currently provides functions to create binary String trees and Expression trees.
 * @author SwengStaff
 *
 */
public abstract class Util {

    private static StringNode constructStringNode(String value, StringNode left, StringNode right) {
        return new StringNode(value, left, right);
    }

    /**
     * Constructs an expression tree from parameters. The array corresponds to a
     * full (except for the bottom layer) binary tree, where the children of
     * node i are at (2i+1) and (2i+2). This tree is filled as follows:
     *         param1
     *          / \
     *         /   \
     *        /     \ 
     *       /       \
     *    param2    param3
     *     / \       / \
     *    /   \     /   \
     * param4 ...
     */
    public static Tree constructStringTree(String... expressions) {
        if (expressions.length == 0) {
            throw new IllegalArgumentException("Must have at least one node");
        }
        StringNode[] nodes = new StringNode[expressions.length];
        for (int i = nodes.length - 1; i >= 0; --i) {
            StringNode left = (2 * i + 1 < nodes.length) ? nodes[2 * i + 1] : null;
            StringNode right = (2 * i + 2 < nodes.length) ? nodes[2 * i + 2] : null;

            if (expressions[i] != null) {
                nodes[i] = constructStringNode(expressions[i], left, right);
            }
        }

        return new Tree(nodes[0]);
    }
}
