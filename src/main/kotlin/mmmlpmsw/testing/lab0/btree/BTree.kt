package mmmlpmsw.testing.lab0.btree

import java.util.ArrayList


class BTree (private val minDegree: Int) {
    var arrayList = ArrayList<Int>()
    private var root: BTreeNode? = null
    fun traverse(): ArrayList<Int> {
        arrayList.clear()
        if (root != null) {
            arrayList.addAll(root!!.traverse())
        }
        return arrayList
    }

    fun search(key: Int): BTreeNode? {
        arrayList.clear()
        val tmp: BTreeNode? = if (root == null) null else root!!.search(key)
        arrayList.addAll(root!!.arrayList)
        return tmp
    }

    fun insert(key: Int) {
        if (root == null) {
            root = BTreeNode(minDegree, true)
            root!!.keys[0] = key
            root!!.num = 1
        } else {
            if (root!!.num == 2 * minDegree - 1) {
                val s = BTreeNode(minDegree, false)
                s.children[0] = root
                s.splitChild(0, root)
                var i = 0
                if (s.keys[0] < key) i++
                s.children[i]!!.insertNotFull(key)
                root = s
            } else root!!.insertNotFull(key)
        }
    }

    fun remove(key: Int) {
        if (root == null) {
            return
        }
        root!!.remove(key)
        if (root!!.num == 0) {
            root = if (root!!.isLeaf) null else root!!.children[0]
        }
    }

    fun print() {
        print(root, "")
    }

    private fun print(node: BTreeNode?, prefix: String) {
        for (i in 0..node!!.num) {
            if (!node.isLeaf) print(node.children[i], "$prefix -> ")
            if (i != node.num) println(prefix + "[" + node.keys[i] + "]")
        }
    }
}