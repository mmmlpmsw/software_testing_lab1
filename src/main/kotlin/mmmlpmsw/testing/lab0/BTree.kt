fun main() {
    val tree = BTree(3)
    repeat(30) {
        println("---- ($it)")
        tree.insert(it+1)
        tree.print()
    }
    println(tree.search(0)) //f
    println(tree.search(1)) //t
    println(tree.search(-1)) //f
    println(tree.search(300)) //f
    println(tree.search(16)) //t
    println(tree.search(30)) //t
}

class BTree(private val minDegree: Int) {
    private var root: Node = Node(minDegree, true)

    fun insert(value: Int) {
        val target = findOutWhereToInsertValue(value)
        var inserted = false
        for (i in 0 until target.actualKeysNum) {
            if (target.keys[i] > value) {
                insertValueInNodeAt(target, i, value)
                inserted = true
                break
            }
        }
        if (!inserted)
            insertValueInNodeAt(target, target.actualKeysNum, value)
        if (target.isOverflowed())
            split(target)
    }

    fun print() {
        print(root, "")
    }

    fun delete(): Boolean {
        return TODO("Not yet implemented.")
    }

//    fun search(value: Int) : Boolean {
//        var current = root
//        outer@ do {
//            for (i in 0 until current.actualKeysNum) {
//                if (current.keys[i] == value)
//                    return true
//                if (current.keys[i] > value) {
//                    if (!current.isLeaf) {
//                        current = current.children[i]!!
//                        continue@outer
//                    } else {
//                        return false
//                    }
//                }
//            }
//            if (!current.isLeaf)
//                current = current.children[current.actualKeysNum]!!
//            else
//                return false
//        } while (true)
//    }

    fun search(value: Int) = search(root, value)

    private fun search(node: Node, key: Int): Boolean {
        var i = 0
        while (i < node.actualKeysNum && key > node.keys[i]) {
            i++
        }
        if (i <= node.actualKeysNum && key == node.keys[i]) {
            return true
        }
        return if (node.isLeaf)
            false
        else
            search(node.children[i]!!, key)
    }

    private fun print(node: Node, prefix: String) {
        for (i in 0..node.actualKeysNum) {
            if (!node.isLeaf)
                print(node.children[i]!!, "$prefix-> ")
            if (i != node.actualKeysNum)
                println("$prefix[${node.keys[i]}]")
        }
    }

    private fun findOutWhereToInsertValue(value: Int): Node {
        var current = root
        outer@ while (!current.isLeaf)  {
            for (i in 0 until current.actualKeysNum) {
                if (current.children[i]!!.parent != current)
                    throw RuntimeException("Tree is broken!")
                if (value < current.keys[i]) {
                    current = current.children[i]!!
                    continue@outer
                } else if (i == current.actualKeysNum - 1) {
                    current = current.children[current.actualKeysNum]!!
                    continue@outer
                }
            }
        }
        return current
    }

    private fun split(node: Node) {
        val raisingValueIdx = node.actualKeysNum/2
        val raisingValue = node.keys[raisingValueIdx]
        val right = Node(minDegree, node.isLeaf)
        right.actualKeysNum = raisingValueIdx
        for (i in raisingValueIdx + 1 until node.actualKeysNum)
            right.keys[i - raisingValueIdx - 1] = node.keys[i]
        for (i in raisingValueIdx + 1 until node.actualKeysNum+1) {
            right.children[i - raisingValueIdx - 1] = node.children[i]
            right.children[i - raisingValueIdx - 1]?.parent = right
        }
        node.actualKeysNum = raisingValueIdx

        // Raising value
        if (node.parent == null) { // root
            root = Node(minDegree, false)
            root.actualKeysNum = 1
            root.keys[0] = raisingValue
            root.children[0] = node // left
            root.children[1] = right
            node.parent = root
            right.parent = root
        } else {
            right.parent = node.parent
            val childIndex = getNodeChildIndex(node.parent!!, node)
            insertValueInNodeAt(node.parent!!, childIndex, raisingValue)
            insertChildInNodeAt(node.parent!!, childIndex+1, right)
            if (node.parent!!.isOverflowed())
                split(node.parent!!)
        }
    }

    private fun insertChildInNodeAt(node: Node, index: Int, child: Node) {
        for (i in node.actualKeysNum+1 downTo index + 1)
            node.children[i] = node.children[i-1]
        node.children[index] = child
    }

    private fun insertValueInNodeAt(node: Node, index: Int, value: Int) {
        for (i in node.actualKeysNum downTo index + 1)
            node.keys[i] = node.keys[i-1]
        node.keys[index] = value
        node.actualKeysNum++
    }

    private fun getNodeChildIndex(parent: Node, child: Node): Int {
        for (i in 0 until parent.actualKeysNum+1)
            if (parent.children[i] === child)
                return i
        throw RuntimeException("Could not find node child")
    }

    class Node(minDegree: Int, var isLeaf: Boolean) {
        var keys: Array<Int> = Array(2*minDegree - 1) { 0 }
        var children: Array<Node?> = Array(2*minDegree) { null }
        var parent: Node? = null
        var actualKeysNum = 0

        fun isOverflowed() = actualKeysNum + 1 >= keys.size - 1
    }
}