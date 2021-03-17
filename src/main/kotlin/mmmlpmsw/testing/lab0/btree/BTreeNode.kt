package mmmlpmsw.testing.lab0.btree

import java.util.ArrayList


class BTreeNode(private val minDegree: Int, var isLeaf: Boolean) {
    var arrayList = ArrayList<Int>()
    var keys : IntArray = IntArray(2 * minDegree - 1)
    var children : Array<BTreeNode?> = arrayOfNulls(2 * minDegree)
    var num : Int = 0

    private fun findKey(key: Int): Int {
        var idx = 0
        while (idx < num && keys[idx] < key) ++idx
        return idx
    }

    fun remove(key: Int) {
        val idx = findKey(key)
        if (idx < num && keys[idx] == key) {
            if (isLeaf) removeFromLeaf(idx) else removeFromNonLeaf(idx)
        } else {
            if (isLeaf) return
            val flag = idx == num
            if (children[idx]!!.num < minDegree) fill(idx)
            if (flag && idx > num) children[idx - 1]!!.remove(key) else children[idx]!!.remove(key)
        }
    }

    private fun removeFromLeaf(idx: Int) {
        for (i in idx + 1 until num) keys[i - 1] = keys[i]
        num--
    }

    private fun removeFromNonLeaf(idx: Int) {
        val key = keys[idx]
        if (children[idx]!!.num >= minDegree) {
            val pred = getPredcessor(idx)
            keys[idx] = pred
            children[idx]!!.remove(pred)
        } else if (children[idx + 1]!!.num >= minDegree) {
            val succ = getSubsequent(idx)
            keys[idx] = succ
            children[idx + 1]!!.remove(succ)
        } else {
            merge(idx)
            children[idx]!!.remove(key)
        }
    }

    private fun getPredcessor(idx: Int): Int {
        var cur = children[idx]
        while (!cur!!.isLeaf) cur = cur.children[cur.num]
        return cur.keys[cur.num - 1]
    }

    private fun getSubsequent(idx: Int): Int {
        var cur = children[idx + 1]
        while (!cur!!.isLeaf) cur = cur.children[0]
        return cur.keys[0]
    }

    private fun fill(idx: Int) {
        if (idx != 0 && children[idx - 1]!!.num >= minDegree) borrowFromPrev(idx) else if (idx != num && children[idx + 1]!!.num >= minDegree) borrowFromNext(idx) else {
            if (idx != num) merge(idx) else merge(idx - 1)
        }
    }

    private fun borrowFromPrev(idx: Int) {
        val child = children[idx]
        val sibling = children[idx - 1]
        for (i in child!!.num - 1 downTo 0) child.keys[i + 1] = child.keys[i]
        if (!child.isLeaf) for (i in child.num downTo 0) child.children[i + 1] = child.children[i]

        child.keys[0] = keys[idx - 1]
        if (!child.isLeaf)
            child.children[0] = sibling!!.children[sibling.num]
        keys[idx - 1] = sibling!!.keys[sibling.num - 1]
        child.num += 1
        sibling.num -= 1
    }

    private fun borrowFromNext(idx: Int) {
        val child = children[idx]
        val sibling = children[idx + 1]
        child!!.keys[child.num] = keys[idx]
        if (!child.isLeaf) child.children[child.num + 1] = sibling!!.children[0]
        keys[idx] = sibling!!.keys[0]
        for (i in 1 until sibling.num) sibling.keys[i - 1] = sibling.keys[i]
        if (!sibling.isLeaf) for (i in 1..sibling.num) sibling.children[i - 1] = sibling.children[i]

        child.num += 1
        sibling.num -= 1
    }

    private fun merge(idx: Int) {
        val child = children[idx]
        val sibling = children[idx + 1]
        child!!.keys[minDegree - 1] = keys[idx]
        for (i in 0 until sibling!!.num) child.keys[i + minDegree] = sibling.keys[i]

        if (!child.isLeaf)
            for (i in 0..sibling.num) child.children[i + minDegree] = sibling.children[i]

        for (i in idx + 1 until num) keys[i - 1] = keys[i]
        for (i in idx + 2..num) children[i - 1] = children[i]
        child.num += sibling.num + 1
        num--
    }

    fun insertNotFull(key: Int) {
        var i = num - 1
        if (isLeaf) {
            while (i >= 0 && keys[i] > key) {
                keys[i + 1] = keys[i]
                i--
            }
            keys[i + 1] = key
            num++
        } else {
            while (i >= 0 && keys[i] > key) i--
            if (children[i + 1]!!.num == 2 * minDegree - 1) {
                splitChild(i + 1, children[i + 1])
                if (keys[i + 1] < key) i++
            }
            children[i + 1]!!.insertNotFull(key)
        }
    }

    fun splitChild(i: Int, y: BTreeNode?) {
        val z = BTreeNode(y!!.minDegree, y.isLeaf)
        z.num = minDegree - 1
        for (j in 0 until minDegree - 1) z.keys[j] = y.keys[j + minDegree]
        if (!y.isLeaf) {
            for (j in 0 until minDegree) z.children[j] = y.children[j + minDegree]
        }
        y.num = minDegree - 1
        for (j in num downTo i + 1) children[j + 1] = children[j]
        children[i + 1] = z
        for (j in num - 1 downTo i) keys[j + 1] = keys[j]
        keys[i] = y.keys[minDegree - 1]
        num ++
    }

    fun traverse(): ArrayList<Int> {
        arrayList.clear()
        var i = 0
        while (i < num) {
            if (!isLeaf) {
                arrayList.addAll(children[i]!!.traverse())
            }
            arrayList.add(keys[i])
            i++
        }
        if (!isLeaf) {
            arrayList.addAll(children[i]!!.traverse())
        }
        return arrayList
    }

    fun search(key: Int): BTreeNode? {
        arrayList.clear()
        var i = 0
        while (i < num && key > keys[i]) {
            arrayList.add(keys[i])
            i++
        }
        if (i < num && keys[i] == key) {
            arrayList.add(keys[i])
            return this
        }
        if (isLeaf) return null
        val tmp: BTreeNode? = children[i]!!.search(key)
        arrayList.addAll(children[i]!!.arrayList)
        return tmp
    }
}