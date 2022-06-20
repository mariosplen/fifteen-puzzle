import java.util.*


fun bfs(initialState: State): State? {

    // Timeout after 15 seconds
    val start = System.currentTimeMillis()
    val end = start + 15000

    if (initialState.isGoalState()) {
        return initialState
    }
    if (!initialState.isSolvable()) {
        println("This puzzle is not solvable!")
        return null
    }


    val frontier: Queue<State> = LinkedList()
    val visited: MutableSet<State> = HashSet()
    var currentState: State
    var subStates: ArrayList<State>

    frontier.add(initialState)

    while (frontier.size > 0 && System.currentTimeMillis() < end) {

        currentState = frontier.poll()

        visited.add(currentState)

        subStates = currentState.getAllSubStates()

        for (state in subStates) {
            if (frontier.contains(state) || visited.contains(state)) {
                continue
            }
            if (state.isGoalState()) {
                return state
            }
            frontier.add(state)

            // Lengths of all possible optimal solutions range from 0 to 80, so we can prune the others.
            // https://en.wikipedia.org/wiki/15_puzzle.
            // Practically useless,because we will never reach that solution with BFS.
            // Our code will time out before any pruning happens.
            //           frontier.removeIf { it.depth > 81 }
        }

    }

    println("Timed out!")
    println("Solution exists but BFS will take very long to find it.")
    return null
}