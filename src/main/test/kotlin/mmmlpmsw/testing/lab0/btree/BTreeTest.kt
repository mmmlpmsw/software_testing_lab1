package mmmlpmsw.testing.lab0.btree

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class BTreeTest {

    @Test
    fun testCreateTree() {
        val tree = BTree(3)

        tree.insert(10)
        tree.insert(20)
        tree.insert(30)
        tree.insert(40)
        tree.insert(50)
        tree.insert(25)

        assertEquals(tree.search(10)!!.keys[0],10)

        assertEquals(
                "[10, 20, 25, 30, 40, 50]",
                tree.traverse().toString())
    }

    @Test
    fun testTreeWithOneNode() {
        val tree = BTree(3)
        tree.insert(10)
        assertEquals("[10]", tree.traverse().toString())
    }

    @Test
    fun testTreeWithManyNodes() {
        val tree = BTree(3)
        for (i in 1..20) {
            tree.insert(i)
        }
        assertEquals(
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]",
                tree.traverse().toString()
        )
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

        assertEquals(
                "[10, 20, 30, 40, 50]",
                tree.traverse().toString())
    }

    @Test
    fun testDeletingAllNode() {
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

        assertEquals("[]", tree.traverse().toString())
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

        assertEquals(
                "[10, 20, 25, 30, 40]",
                tree.traverse().toString()
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

        assertEquals(
                "[20, 25, 30, 40, 50]" ,
                tree.traverse().toString()
        )
    }

    @Test
    fun testDeletingASingleNode() {
        val tree = BTree(3)

        tree.insert(10)
        tree.remove(10)

        assertEquals("[]",
                tree.traverse().toString()
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
        tree.insert(40)
        tree.remove(100)

        assertEquals(
                "[10, 20, 25, 30, 40, 40, 50]" ,
                tree.traverse().toString()
        )
    }

    @Test
    fun testRemoveFromRoot() {
        val tree = BTree(3)
        for (i in 1 .. 30)
            tree.insert(i)
        tree.remove(18)
        assertEquals(
                "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30]",
                tree.traverse().toString()
        )
    }

    @Test
    fun testDeleteWithRebuilding() {
        val tree = BTree(3)
        for (i in 1 .. 31)
            tree.insert(i)

        tree.remove(25)
        tree.print()
        assertEquals(tree, tree) //todo
    }

    @Test
    fun testDeleteWithRebuildings() {
        val tree = BTree(2)

        tree.insert(1)
        tree.insert(3)
        tree.insert(7)
        tree.insert(10)
        tree.insert(11)
        tree.insert(4)
        tree.insert(5)
        tree.insert(2)
        tree.insert(12)
        tree.insert(6)

        tree.remove(3)
        tree.print()
        assertEquals(tree, tree) //todo


    }

//    @Test
//    fun testRemoveAllValuesFromRoot() {
//        val tree = BTreeT(3)
//        for (i in 1 .. 30)
//            tree.insert(i)
//        tree.remove(9)
//        tree.remove(18)
//        assertEquals(
//                "",
//                tree.traverse().toString()
//        )
//    }
}