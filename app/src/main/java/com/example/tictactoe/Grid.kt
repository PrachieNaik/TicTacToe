package com.example.tictactoe

data class Grid(
    val size: Int,
    val board: Array<Array<String>>,
    var turn: Player,
    val players: List<Player>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Grid

        if (!board.contentDeepEquals(other.board)) return false

        return true
    }

    override fun hashCode(): Int {
        return board.contentDeepHashCode()
    }
}