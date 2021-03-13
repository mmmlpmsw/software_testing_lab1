import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertFalse

class BTreeTest {

    val standardOut = System.out
    val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun testCreateTree() {
        val tree = BTree(3)

        tree.insert(10)
        tree.insert(20)
        tree.insert(30)
        tree.insert(40)
        tree.insert(50)
        tree.insert(25)

        tree.print()
        assertEquals(
                "-> [10]" + "\n" +
                         "-> [20]" + "\n" +
                         "-> [25]" + "\n" +
                         "[30]" + "\n" +
                         "-> [40]" + "\n" +
                         "-> [50]" + "\n" ,
                outputStreamCaptor.toString())
    }

    @Test
    fun testTreeWithOneNode() {
        val tree = BTree(3)
        var root: BTree.Node? = null
        tree.insert(10)
        tree.print()
        assertEquals("[10]\n", outputStreamCaptor.toString())
    }

    @Test
    fun testTreeWithManyNodes() {
        val tree = BTree(3)
        for (i in 1..20) {
            tree.insert(i)
        }
        tree.print()
        assertEquals(
        "-> -> [1]" + "\n" +
                 "-> -> [2]" + "\n" +
                 "-> [3]" + "\n" +
                 "-> -> [4]" + "\n" +
                 "-> -> [5]" + "\n" +
                 "-> [6]" + "\n" +
                 "-> -> [7]" + "\n" +
                 "-> -> [8]" + "\n" +
                 "[9]" + "\n" +
                 "-> -> [10]" + "\n" +
                 "-> -> [11]" + "\n" +
                 "-> [12]" + "\n" +
                 "-> -> [13]" + "\n" +
                 "-> -> [14]" + "\n" +
                 "-> [15]" + "\n" +
                 "-> -> [16]" + "\n" +
                 "-> -> [17]" + "\n" +
                 "-> -> [18]" + "\n" +
                 "-> -> [19]" + "\n" +
                 "-> -> [20]" + "\n" ,
                outputStreamCaptor.toString() )
    }

    @Test
    fun testDeletingNode() {
        val tree = BTree(3)
        tree.insert(10)
        tree.insert(20)
        tree.insert(30)
        tree.insert(40)
        tree.insert(50)
        tree.insert(25)
        tree.remove(25)
        tree.print()

        assertEquals(
                "-> [10]" + "\n" +
                         "-> [20]" + "\n" +
                         "[30]" + "\n" +
                         "-> [40]" + "\n" +
                         "-> [50]" + "\n",
                outputStreamCaptor.toString())
    }

    @Test
    fun testDelitingAllNode() {
        val tree = BTree(3)

        tree.insert(10)
        tree.insert(20)
        tree.insert(30)
        tree.insert(40)
        tree.insert(50)
        tree.insert(25)

        tree.remove(25)
        tree.remove(50)
        tree.remove(40)
        tree.remove(30)
        tree.remove(20)
        tree.remove(10)

        tree.print()
        assertEquals("", outputStreamCaptor.toString() )
    }

    @Test
    fun testDeletingTheHigherNode() {
        val tree = BTree(3)

        tree.insert(10)
        tree.insert(20)
        tree.insert(30)
        tree.insert(40)
        tree.insert(50)
        tree.insert(25)
        tree.remove(50)
        tree.print()

        assertEquals(
                "-> [10]" + "\n" +
                         "-> [20]" + "\n" +
                         "-> [25]" + "\n" +
                         "[30]" + "\n" +
                         "-> [40]" + "\n" ,
                outputStreamCaptor.toString()
        )
    }

    @Test
    fun testDeletingTheSmallestNode() {
        val tree = BTree(3)

        tree.insert(10)
        tree.insert(20)
        tree.insert(30)
        tree.insert(40)
        tree.insert(50)
        tree.insert(25)

        tree.remove(10)
        tree.print()

        assertEquals(
                "-> [20]" + "\n" +
                         "-> [25]" + "\n" +
                         "[30]" + "\n" +
                         "-> [40]" + "\n" +
                         "-> [50]" + "\n" ,
                outputStreamCaptor.toString()
        )
    }

    @Test
    fun testDeletingASingleNode() {
        val tree = BTree(3)

        tree.insert(10)
        tree.remove(10)
        tree.print()

        assertEquals("",
                outputStreamCaptor.toString()
        )
    }

    @Test
    fun testDeletingANonexistentNode() {
        val tree = BTree(3)

        tree.insert(10)
        tree.insert(20)
        tree.insert(30)
        tree.insert(40)
        tree.insert(50)
        tree.insert(25)

        assertFalse(tree.remove(100))
        tree.print()

        assertEquals(
                "-> [10]" + "\n" +
                         "-> [20]" + "\n" +
                         "-> [25]" + "\n" +
                         "[30]" + "\n" +
                         "-> [40]" + "\n" +
                         "-> [50]" + "\n" ,
                outputStreamCaptor.toString()
        )
    }


}