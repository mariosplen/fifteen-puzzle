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

//     Solution for this board exists, but it requires many moves (80 moves),
//     our BFS is not efficient enough to tackle this puzzle and will time out
//     http://kociemba.org/themen/fifteen/fifteensolver.html
    val boardWithSuperFlipSolution = arrayOf(
        intArrayOf(0, 12, 9, 13),
        intArrayOf(15, 11, 10, 14),
        intArrayOf(3, 7, 2, 5),
        intArrayOf(4, 8, 6, 1)
    )

    var choice: String
    do {
        println(
            """
            0) Simple puzzle with solution (Default)
            1) Simple puzzle without solution
            2) Puzzle that requires 80 moves! (practically unsolvable with BFS)
            3) Custom input
            Select puzzle to solve. press Enter for Default.
        """.trimIndent()
        )
        choice = readLine()!!
    } while (choice != "0" && choice != "" && choice != "1" && choice != "2" && choice != "3")


    val solution: State?

    when (choice) {
        "0" -> solution = bfs(State(boardWithSolution))
        "1" -> solution = bfs(State(boardWithNoSolution))
        "2" -> solution = bfs(State(boardWithSuperFlipSolution))
        "3" -> {
            println(
                """
                    Enter custom puzzle, values separated by space
                    (example of input: 2 0 3 4 5 6 7 8 9 6 10 11 13 14 15 12)
                """.trimIndent()
            )
            try {
                val c = readLine()!!.split(" ").map { s -> s.toInt() }
                val customPuzzle = arrayOf(
                    intArrayOf(c[0], c[1], c[2], c[3]),
                    intArrayOf(c[4], c[5], c[6], c[7]),
                    intArrayOf(c[8], c[9], c[10], c[11]),
                    intArrayOf(c[12], c[13], c[14], c[15])
                )
                solution = bfs(State(customPuzzle))
            } catch (e: Exception) {
                println("Please enter valid custom puzzle")
                throw Exception("Invalid custom puzzle")
            }
        }
        else -> solution = bfs(State(boardWithSolution))

    }

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