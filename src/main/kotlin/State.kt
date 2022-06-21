class State(
    private val boardArr: Array<IntArray>,
    internal val moveDone: String? = null,
    internal val parent: State? = null
) {

    private val emptyBoxIndexes: Pair<Int, Int> = getEmptyBoxIndexes()

    private fun getEmptyBoxIndexes(): Pair<Int, Int> {

        for (i in boardArr.indices) {
            for (j in boardArr[i].indices) {
                if (boardArr[i][j] == 0) {
                    return Pair(i, j)
                }
            }
        }

        throw Exception("Empty box was not found!")
    }

    fun getAllSubStates(): ArrayList<State> {

        val subStates: ArrayList<State> = ArrayList()
        var newBoardArr: Array<IntArray>

        // Empty box moves up.
        if (emptyBoxIndexes.first != 0) { // Check if empty box can move up.
            newBoardArr = boardArr.copy()
            newBoardArr[emptyBoxIndexes.first][emptyBoxIndexes.second] =
                newBoardArr[emptyBoxIndexes.first - 1][emptyBoxIndexes.second]
            newBoardArr[emptyBoxIndexes.first - 1][emptyBoxIndexes.second] = 0

            subStates.add(State(newBoardArr, "up", this))
        }

        // Empty box moves down.
        if (emptyBoxIndexes.first != 3) { // Check if empty box can move down.
            newBoardArr = boardArr.copy()
            newBoardArr[emptyBoxIndexes.first][emptyBoxIndexes.second] =
                newBoardArr[emptyBoxIndexes.first + 1][emptyBoxIndexes.second]
            newBoardArr[emptyBoxIndexes.first + 1][emptyBoxIndexes.second] = 0

            subStates.add(State(newBoardArr, "down", this))
        }

        // Empty box moves left.
        if (emptyBoxIndexes.second != 0) {// Check if empty box can move left.
            newBoardArr = boardArr.copy()
            newBoardArr[emptyBoxIndexes.first][emptyBoxIndexes.second] =
                newBoardArr[emptyBoxIndexes.first][emptyBoxIndexes.second - 1]
            newBoardArr[emptyBoxIndexes.first][emptyBoxIndexes.second - 1] = 0

            subStates.add(State(newBoardArr, "left", this))
        }

        // Empty box moves right.
        if (emptyBoxIndexes.second != 3) {// Check if empty box can move right.
            newBoardArr = boardArr.copy()
            newBoardArr[emptyBoxIndexes.first][emptyBoxIndexes.second] =
                newBoardArr[emptyBoxIndexes.first][emptyBoxIndexes.second + 1]
            newBoardArr[emptyBoxIndexes.first][emptyBoxIndexes.second + 1] = 0

            subStates.add(State(newBoardArr, "right", this))
        }

        return subStates
    }

    fun isGoalState(): Boolean {

        // Goal board array.
        val goalBoardArr = arrayOf(
            intArrayOf(1, 2, 3, 4),
            intArrayOf(5, 6, 7, 8),
            intArrayOf(9, 10, 11, 12),
            intArrayOf(13, 14, 15, 0)
        )

        if (!boardArr.contentDeepEquals(goalBoardArr)) return false

        return true
    }

    private fun getEmptyBoxRowFromBottom(): Int {

        for (i in boardArr.reversedArray().indices) {
            for (j in boardArr[i].reversedArray().indices) {
                if (boardArr[i][j] == 0) {
                    return boardArr.size - i
                }
            }
        }
        throw Exception("Empty box was not found!")
    }

    private fun getInvCount(): Int {

        // Flatten boardArr to 1d array.
        val arr = boardArr.flatMap { obj -> obj.toList() }.toTypedArray()

        var invCount = 0
        for (i in arr.indices) {
            for (j in 0 until i) {
                if (arr[j] != 0 && arr[i] != 0) {
                    if (arr[j] > arr[i]) invCount++
                }
            }
        }

        return invCount
    }

    // https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/.
    fun isSolvable(): Boolean {
        val invCount = getInvCount()

        // If grid is odd, return true if inversion count is even.
        return if (boardArr.size % 2 != 0) {
            invCount % 2 == 0
        } else {  // If grid is even.
            val emptyBoxRow = getEmptyBoxRowFromBottom()
            if (emptyBoxRow % 2 != 0) {
                invCount % 2 == 0
            } else {
                invCount % 2 != 0
            }
        }

    }


    // 2d array copy https://stackoverflow.com/a/40463707.
    private fun Array<IntArray>.copy() = Array(size) { get(it).clone() }

    override fun toString(): String {
        val buffer = StringBuilder()

        for (r in boardArr) {
            for (c in r) {
                buffer.append(String.format("%5d", c))
            }
            buffer.append('\n')
        }
        return buffer.toString()
    }

    override fun hashCode(): Int {
        return boardArr.contentDeepHashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as State

        if (!boardArr.contentDeepEquals(other.boardArr)) return false

        return true
    }


}