fun main() {

    val boardWithSolution = arrayOf(
        intArrayOf(2, 0, 3, 4),
        intArrayOf(1, 5, 7, 8),
        intArrayOf(9, 6, 10, 11),
        intArrayOf(13, 14, 15, 12)
    )

    val boardWithNoSolution = arrayOf(
        intArrayOf(1, 2, 3, 4),
        intArrayOf(5, 6, 7, 8),
        intArrayOf(9, 10, 11, 12),
        intArrayOf(13, 15, 14, 0)
    )

    // Solution for this board exists, but it requires many moves (80 moves),
    // our BFS is not efficient enough to tackle this puzzle and will time out
    // http://kociemba.org/themen/fifteen/fifteensolver.html
    val boardWithSuperFlipSolution = arrayOf(
        intArrayOf(0, 12, 9, 13),
        intArrayOf(15, 11, 10, 14),
        intArrayOf(3, 7, 2, 5),
        intArrayOf(4, 8, 6, 1)
    )

    val solution = bfs(State(boardWithSolution))
//    val solution = bfs(State(boardWithNoSolution))
//    val solution = bfs(State(boardWithSuperFlipSolution))


    if (solution != null) {
        println("Solution Found!")
        val path = getPath(solution)

        println(getSolutionStatesString(path))
        println("The moves that solve the puzzle are: ")
        println(getSolutionString(path))

    }

}

private fun getPath(solution: State): Array<State> {
    val path: MutableList<State> = ArrayList()
    var s: State? = solution
    while (s != null) {
        path.add(s)
        s = s.parent
    }
    path.reverse()
    return path.toTypedArray()
}

private fun getSolutionStatesString(path: Array<State>): String {
    val buffer = StringBuilder()
    path.forEach { state ->
        run {
            buffer.append((state.moveDone ?: "Initial State") + '\n')
            buffer.append(state.toString())
        }
    }
    return buffer.toString()
}

private fun getSolutionString(path: Array<State>): String {
    val buffer = StringBuilder()
    for (i in 1 until path.size) {
        if (path[i].isGoalState()) {
            buffer.append(path[i].moveDone + ".\n")
        } else {
            buffer.append(path[i].moveDone + ", ")
        }
    }
    return buffer.toString()
}