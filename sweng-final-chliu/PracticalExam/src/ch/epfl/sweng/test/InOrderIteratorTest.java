package ch.epfl.sweng.test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;
import ch.epfl.sweng.base.Node;
import ch.epfl.sweng.base.Tree;
import ch.epfl.sweng.printer.PrettyPrinter;

/**
 * Unit tests for ch.epfl.sweng.iterator.InOrderOterator
 * @author SwengStaff
 *
 */
public class InOrderIteratorTest extends TestCase {

    public void testSimpleIteration() {
        Tree t = Util.constructStringTree("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o");

        PrettyPrinter.printNode(t.getRoot());

        Iterator<Node> it = t.iterator();

        assertEquals("h", it.next().getLabel());
        assertEquals("d", it.next().getLabel());
        assertEquals("i", it.next().getLabel());
        assertEquals("b", it.next().getLabel());
        assertEquals("j", it.next().getLabel());
        assertEquals("e", it.next().getLabel());
        assertEquals("k", it.next().getLabel());
        assertEquals("a", it.next().getLabel());
        assertEquals("l", it.next().getLabel());
        assertEquals("f", it.next().getLabel());
        assertEquals("m", it.next().getLabel());
        assertEquals("c", it.next().getLabel());
        assertEquals("n", it.next().getLabel());
        assertEquals("g", it.next().getLabel());
        assertEquals("o", it.next().getLabel());
    }
    
    public void test() {
    	
	    boolean thrown = false;

	    try {
	    	Tree t = Util.constructStringTree("a");
	    	Iterator<Node> it = t.iterator();
	    	assertEquals("a", it.next().getLabel());
	    	it.next();
	    } catch (NoSuchElementException e) {
	    	thrown = true;
	    }

	    assertTrue(thrown);
    }
}
