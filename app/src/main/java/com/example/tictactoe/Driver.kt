package com.example.tictactoe

const val sizeOfGrid = 3
const val signForFirstTurn = "X"
const val defaultSign = "-"
fun main() {

    val players = getPlayers()
    val grid = getGrid(players)
    while (true) {
        printGrid(grid)

        val input = readLine()!!

        if (playerWantsToExit(input))
            break

        val (row, col) = input.split(' ').map { it.toInt() - 1 }

        if (isValidMove(row, col, grid)) {
            markCell(row, col, grid)

            if (didPlayerWin(grid)) {
                println(grid.turn.name + "player won")
                break
            } else if (isValidMovePossible(grid = grid)) {
                grid.turn = getNextTurn(grid)
            } else {
                println("Game over")
                return
            }
        } else {
            println("Invalid move")
        }

    }

}

fun markCell(row: Int, col: Int, grid: Grid) {
    grid.board[row][col] = grid.turn.sign
}

fun getNextTurn(grid: Grid): Player = grid.players[(grid.turn.index + 1) % grid.players.size]

fun playerWantsToExit(input: String): Boolean = input.equals("exit", true)

fun printGrid(grid: Grid) {
    grid.board.forEach { rows ->
        rows.forEach {
            print("$it ")
        }
        println()
    }
}

fun isValidMovePossible(grid: Grid): Boolean {
    for (col in 0 until sizeOfGrid) {
        for (row in 0 until sizeOfGrid) {
            if (grid.board[row][col] == defaultSign) {
                return true
            }
        }
    }
    return false
}

fun isValidMove(row: Int, col: Int, grid: Grid): Boolean {
    return row < sizeOfGrid && col < sizeOfGrid && grid.board[row][col] == defaultSign
}

fun didPlayerWin(grid: Grid): Boolean {

    //check all the rows
    for (row in 0 until sizeOfGrid) {
        val sign = grid.board[row][0]
        if (sign != defaultSign) {
            var flagForEqual = false
            for (col in 1 until sizeOfGrid) {
                if (grid.board[row][col] != sign) {
                    flagForEqual = true
                    break
                }
            }
            if (!flagForEqual) {
                return true
            }
        }
    }

    //check all the columns
    for (col in 0 until sizeOfGrid) {
        val sign = grid.board[0][col]
        if (sign != defaultSign) {
            var flagForEqual = false
            for (row in 1 until sizeOfGrid) {
                if (grid.board[row][col] != sign) {
                    flagForEqual = true
                    break
                }
            }
            if (!flagForEqual) {
                return true
            }
        }
    }

    //check diagonal
    var sign = grid.board[0][0]
    if (sign != defaultSign) {
        var flagForEqual = false
        for (row in 1 until sizeOfGrid) {
            if (grid.board[row][row] != sign) {
                flagForEqual = true
                break
            }
        }
        if (!flagForEqual) {
            return true
        }
    }

    //check anti-diagonal
    sign = grid.board[sizeOfGrid - 1][sizeOfGrid - 1]
    if (sign != defaultSign) {
        var flagForEqual = false
        for (row in sizeOfGrid - 1 downTo 0) {
            if (grid.board[row][row] != sign) {
                flagForEqual = true
                break
            }
        }
        if (!flagForEqual) {
            return true
        }
    }

    return false
}

fun getGrid(players: List<Player>): Grid {
    val grid = Array(sizeOfGrid) { Array(sizeOfGrid) { defaultSign } }
    val turn = getTurn(players)
    return Grid(
        size = sizeOfGrid,
        board = grid,
        turn = turn,
        players = players
    )
}

fun getTurn(players: List<Player>): Player {
    for (range in players.indices) {
        if (players[range].sign.equals(signForFirstTurn, true))
            return players[range]
    }
    return players[0]
}

fun getPlayers(): List<Player> {
    val (name1, sign1) = readLine()!!.split(' ')
    val (name2, sign2) = readLine()!!.split(' ')
    return listOf(
        Player(name = name1, sign = sign1, index = 0),
        Player(name = name2, sign = sign2, index = 1)
    )
}