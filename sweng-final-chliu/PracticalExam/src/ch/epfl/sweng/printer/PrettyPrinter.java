package ch.epfl.sweng.printer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.sweng.base.Node;

/**
 * PrettyPrinter for binary trees. Prints an ASCII art representation of a binary tree.
 * For simplicity, this printer only prints the first character in each node's label.
 * Adapted from http://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 * @author SwengStaff
 * 
 */
public class PrettyPrinter {
    public static void printNode(Node root) {
        int maxLevel = PrettyPrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || PrettyPrinter.isAllElementsNull(nodes)) {
            return;
        }
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, Math.max(floor - 1, 0));
        int firstSpaces = (int) Math.pow(2, floor) - 1;
        int betweenSpaces = (int) Math.pow(2, floor + 1) - 1;

        PrettyPrinter.printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.getLabel().charAt(0));
                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            PrettyPrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                PrettyPrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    PrettyPrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeft() != null) {
                    System.out.print("/");
                } else {
                    PrettyPrinter.printWhitespaces(1);
                }

                PrettyPrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).getRight() != null) {
                    System.out.print("\\");
                } else {
                    PrettyPrinter.printWhitespaces(1);
                }

                PrettyPrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    private static int maxLevel(Node node) {
        if (node == null) {
            return 0;
        }

        return Math.max(PrettyPrinter.maxLevel(node.getLeft()), PrettyPrinter.maxLevel(node.getRight())) + 1;
    }

    private static boolean isAllElementsNull(List<Node> list) {
        for (Node node : list) {
            if (node != null) {
                return false;
            }
        }

        return true;
    }
}
