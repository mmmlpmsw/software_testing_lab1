import java.util.Stack

class BTree(private val minDegree: Int) {
    inner class Node {
        var actualKeysNum = 0
        var keys = IntArray(2 * minDegree - 1)
        var children = arrayOfNulls<Node>(2 * minDegree)
        var isLeaf = true
        fun find(k: Int): Int {
            for (i in 0 until actualKeysNum)
                if (keys[i] == k)
                    return i
            return -1
        }
    }

    private var root: Node?

    fun remove(key: Int): Boolean {
        val x = search(root, key) ?: return false
        remove(x, key)
        return true
    }

    fun insert(key: Int) {
        val r = root
        if (r!!.actualKeysNum == 2 * minDegree - 1) {
            val s = Node()
            root = s
            s.isLeaf = false
            s.actualKeysNum = 0
            s.children[0] = r
            split(s, 0, r)
            insertNode(s, key)
        } else {
            insertNode(r, key)
        }
    }


    private fun search(x: Node?, key: Int): Node? {
        var i = 0
        if (x == null) return x
        while (i < x.actualKeysNum) {
            if (key < x.keys[i]) break
            if (key == x.keys[i]) return x
            i++
        }
        return if (x.isLeaf) null else search(x.children[i], key)
    }

    private fun split(x: Node?, pos: Int, y: Node?) {
        val z = Node()
        z.isLeaf = y!!.isLeaf
        z.actualKeysNum = minDegree - 1
        for (j in 0 until minDegree - 1)
            z.keys[j] = y.keys[j + minDegree]

        if (!y.isLeaf)

            for (j in 0 until minDegree)
                z.children[j] = y.children[j + minDegree]

        y.actualKeysNum = minDegree - 1

        for (j in x!!.actualKeysNum downTo pos + 1) x.children[j + 1] = x.children[j]

        x.children[pos + 1] = z
        for (j in x.actualKeysNum - 1 downTo pos) x.keys[j + 1] = x.keys[j]

        x.keys[pos] = y.keys[minDegree - 1]
        x.actualKeysNum = x.actualKeysNum + 1
    }

    private fun insertNode(x: Node?, k: Int) {
        if (x!!.isLeaf) {
            var i = x.actualKeysNum - 1
            while (i >= 0 && k < x.keys[i]) {
                x.keys[i + 1] = x.keys[i]
                i--
            }

            x.keys[i + 1] = k
            x.actualKeysNum = x.actualKeysNum + 1
        } else {
            var i = x.actualKeysNum - 1
            while (i >= 0 && k < x.keys[i])
                i--

            i++
            val tmp = x.children[i]
            if (tmp!!.actualKeysNum == 2 * minDegree - 1) {
                split(x, i, tmp)
                if (k > x.keys[i])
                    i++

            }
            insertNode(x.children[i], k)
        }
    }

    private fun remove(x: Node?, key: Int) {
        val x_ = x!!
        var pos = x_.find(key)
        if (pos != -1) {
            if (x_.isLeaf) {
                var i = 0
                while (i < x_.actualKeysNum && x_.keys[i] != key)
                    i++

                while (i < x_.actualKeysNum) {
                    if (i != 2 * minDegree - 2)
                        x_.keys[i] = x_.keys[i + 1]
                    i++
                }
                x_.actualKeysNum--
                return
            }
            if (!x_.isLeaf) {
                var pred = x_.children[pos]
                val predKey: Int
                if (pred!!.actualKeysNum >= minDegree) {
                    while (true) {
                        if (pred!!.isLeaf) {
                            predKey = pred.keys[pred.actualKeysNum - 1]
                            break
                        } else pred = pred.children[pred.actualKeysNum]

                    }
                    remove(pred, predKey)
                    x_.keys[pos] = predKey
                    return
                }
                var nextNode = x_.children[pos + 1]
                if (nextNode!!.actualKeysNum >= minDegree) {
                    var nextKey = nextNode.keys[0]
                    if (!nextNode.isLeaf) {
                        nextNode = nextNode.children[0]
                        while (true) {
                            if (nextNode!!.isLeaf) {
                                nextKey = nextNode.keys[nextNode.actualKeysNum - 1]
                                break
                            } else
                                nextNode = nextNode.children[nextNode.actualKeysNum]
                        }
                    }
                    remove(nextNode, nextKey)
                    x_.keys[pos] = nextKey
                    return
                }
                var temp = pred.actualKeysNum + 1
                pred.keys[pred.actualKeysNum++] = x_.keys[pos]
                run {
                    var i = 0
                    var j = pred.actualKeysNum
                    while (i < nextNode.actualKeysNum) {
                        pred.keys[j++] = nextNode.keys[i]
                        pred.actualKeysNum++
                        i++
                    }
                }
                for (i in 0 until nextNode.actualKeysNum + 1)
                    pred.children[temp++] = nextNode.children[i]

                x_.children[pos] = pred
                for (i in pos until x_.actualKeysNum)
                    if (i != 2 * minDegree - 2) {
                        x_.keys[i] = x_.keys[i + 1]

                        for (j in pos + 1 until x_.actualKeysNum + 1)
                            if (j != 2 * minDegree - 1)
                                x_.children[j] = x_.children[j + 1]

                        x_.actualKeysNum--
                        if (x_.actualKeysNum == 0)
                            if (x_ === root)
                                root = x_.children[0]

                        remove(pred, key)
                        return
                    }
            } else {
                pos = 0
                while (pos < x_.actualKeysNum) {
                    if (x_.keys[pos] > key) break
                    pos++
                }
                val tmp = x_.children[pos]
                if (tmp!!.actualKeysNum >= minDegree) {
                    remove(tmp, key)
                    return
                }
                val nb: Node?
                val devider: Int
                if (pos != x_.actualKeysNum && x_.children[pos + 1]!!.actualKeysNum >= minDegree) {
                    devider = x_.keys[pos]
                    nb = x_.children[pos + 1]
                    x_.keys[pos] = nb!!.keys[0]
                    tmp.keys[tmp.actualKeysNum++] = devider
                    tmp.children[tmp.actualKeysNum] = nb.children[0]
                    for (i in 1 until nb.actualKeysNum)
                        nb.keys[i - 1] = nb.keys[i]

                    for (i in 1..nb.actualKeysNum)
                        nb.children[i - 1] = nb.children[i]

                    nb.actualKeysNum--
                    remove(tmp, key)
                    return
                } else if (pos != 0 && x_.children[pos - 1]!!.actualKeysNum >= minDegree) {
                    devider = x_.keys[pos - 1]
                    nb = x_.children[pos - 1]
                    x_.keys[pos - 1] = nb!!.keys[nb.actualKeysNum - 1]
                    val child = nb.children[nb.actualKeysNum]
                    nb.actualKeysNum--
                    for (i in tmp.actualKeysNum downTo 1)
                        tmp.keys[i] = tmp.keys[i - 1]

                    tmp.keys[0] = devider
                    for (i in tmp.actualKeysNum + 1 downTo 1)
                        tmp.children[i] = tmp.children[i - 1]

                    tmp.children[0] = child
                    tmp.actualKeysNum++
                    remove(tmp, key)
                    return
                } else {
                    val lt: Node?
                    val rt: Node?

                    if (pos != x_.actualKeysNum) {
                        devider = x_.keys[pos]
                        lt = x_.children[pos]
                        rt = x_.children[pos + 1]
                    } else {
                        devider = x_.keys[pos - 1]
                        rt = x_.children[pos]
                        lt = x_.children[pos - 1]

                        pos--
                    }
                    for (i in pos until x_.actualKeysNum - 1)
                        x_.keys[i] = x_.keys[i + 1]

                    for (i in pos + 1 until x_.actualKeysNum)
                        x_.children[i] = x_.children[i + 1]

                    x_.actualKeysNum--
                    lt!!.keys[lt.actualKeysNum++] = devider
                    var i = 0
                    var j = lt.actualKeysNum
                    while (i < rt!!.actualKeysNum + 1) {
                        if (i < rt.actualKeysNum) {
                            lt.keys[j] = rt.keys[i]
                        }
                        lt.children[j] = rt.children[i]
                        i++
                        j++
                    }
                    lt.actualKeysNum += rt.actualKeysNum
                    if (x_.actualKeysNum == 0)
                        if (x_ === root)
                            root = x_.children[0]
                    remove(lt, key)
                    return
                }
            }
        }
    }

    fun print() {
        print(root!!, "")
    }

    private fun print(node: Node, prefix: String) {
        for (i in 0..node.actualKeysNum) {
            if (!node.isLeaf)
                print(node.children[i]!!, "$prefix-> ")
            if (i != node.actualKeysNum)
                println("$prefix[${node.keys[i]}]")
        }
    }


//    private fun traverse(x: Node?) {
//        for (i in 0 until x!!.actualKeysNum)
//            print(x.keys[i].toString() + " ")
//        if (!x.isLeaf)
//            for (i in 0 until x.actualKeysNum + 1)
//                traverse(x.children[i])
//    }

    init {
        root = Node()
        root!!.actualKeysNum = 0
        root!!.isLeaf = true
    }
}