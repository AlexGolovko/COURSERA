import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NonSharedSubstringTest {

    @Test
    void buildTreeTest() {
        final NonSharedSubstring impl = new NonSharedSubstring();
        final ArrayList<String> strings = new ArrayList<>();
        strings.add("ATA");
        final NonSharedSubstring.Node root = impl.buildTree(strings);
        assertEquals(1, root.getLeaf().size());
        impl.printTree(root);
        strings.clear();
        System.out.println("Second case:");
        strings.add("AT");
        strings.add("AG");
        strings.add("AC");
        final NonSharedSubstring.Node node = impl.buildTree(strings);
        impl.printTree(node);
    }

}