package com.sc19kb.android.chessapplication

/*
* ---------- CHESS CORE ------------
*
* Object that has all the functions necessary for a Chess Game written in
* the Kotlin programming language.
*
* Contains all the rules, moves and special moves implementations.
*
*/

import java.lang.Math.abs


object ChessCore {
    var moveString: String = "" // takes the coordinates of the executed move (unless the user forfeits the match, then it becomes "White/Black Forfeited" )
    var round:ChessArmy = ChessArmy.WHITE // indicates which Army is playing each round ( Whites go first, then round becomes BLACK and then WHITE again as the game progresses)
    // Special Moves Flags
    var whiteEnPassantFlag: Int = -1 // takes the number of the Column in which the passantable White Pawn is located
    var blackEnPassantFlag: Int = -1 // takes the number of the Column in which the passantable Black Pawn is located
    var whiteRightCastleFlag: Boolean = true // becomes "false" if either the Right White Rook or the White King moves
    var whiteLeftCastleFlag: Boolean = true // becomes "false" if either the Left White Rook or the White King moves
    var blackRightCastleFlag: Boolean = true // becomes "false" if either the Right Black Rook or the Black King moves
    var blackLeftCastleFlag: Boolean = true // becomes "false" if either the Left Black Rook or the Black King moves
    var whiteChecked: Boolean = false // becomes "true" when the White King gets Checked ( threatened by an enemy piece, not checkmated)
    var blackChecked: Boolean = false // becomes "true" when the Black King gets Checked ( threatened by an enemy piece, not checkmated)
    var whiteCheckMated: Boolean = false // becomes "true" when the White King gets Checkmated
    var blackCheckMated: Boolean = false // becomes "true" when the Black King gets Checkmated

    var piecesSet = mutableSetOf<ChessPiece>() // set containing all the pieces of the Chessboard ( Each piece has its Coordinates, Rank, Army and Icon )

    //Clear the Chess Board and re-arrange all the pieces to their original positions
    init {reset()}


    // ======================== CHESSBOARD MOVEMENT PATHS CHECKS : [Start] ===============================

    // Check if there is empty vertical space between the current square and the destination square.
    private fun isGapVertically(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (curColumn != destColumn) return false
        else{
            val emptySquares = abs(curRow - destRow) - 1
            if (emptySquares == 0 ) return true
            for (i in 1..emptySquares) {
                val nextRow = when {
                    destRow > curRow -> curRow + i
                    else -> curRow - i
                }
                if (pieceAt(curColumn, nextRow) != null) return false
            }
            return true
        }
    }

    // Check if there is empty horizontal space between the current square and the destination square.
    private fun isGapHorizontally(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (curRow != destRow) return false
        val emptySquares = abs(curColumn - destColumn) - 1
        if (emptySquares == 0 ) return true
        for (i in 1..emptySquares) {
            val nextColumn = when {
                destColumn > curColumn -> curColumn + i
                else -> curColumn - i
            }
            if (pieceAt(nextColumn, curRow) != null) return false
        }
        return true
    }

    // Check if there is empty diagonal space between the current square and the destination square.
    private fun isGapDiagonally(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (abs(curColumn - destColumn) != abs(curRow - destRow)) return false
        val emptySquares = abs(curColumn - destColumn) - 1
        for (i in 1..emptySquares) {
            val nextColumn = when {
                destColumn > curColumn -> curColumn + i
                else -> curColumn - i
            }
            val nextRow = when {
                destRow > curRow -> curRow + i
                else -> curRow - i
            }
            if (pieceAt(nextColumn, nextRow) != null) return false
        }
        return true
    }
    // =================== CHESSBOARD MOVEMENT PATHS CHECKS : [END] =========================

    // ===================== SPECIAL MOVES : [Start] ====================

    // Upgrade the pawn to a Queen if it reaches the other end of the chess board.
    fun pawnUpgrade(army: ChessArmy, destColumn: Int, destRow:Int){
        // turn White Pawn into a White Queen
        if(army == ChessArmy.WHITE && destRow == 7){
            piecesSet.remove(pieceAt(destColumn,destRow))
            piecesSet.add(ChessPiece(destColumn, destRow, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        }
        // turn Black Pawn into a Black Queen
        if(army == ChessArmy.BLACK && destRow == 0){
            piecesSet.remove(pieceAt(destColumn,destRow))
            piecesSet.add(ChessPiece(destColumn, destRow, ChessArmy.BLACK, ChessRank.QUEEN, R.drawable.queen_black))
        }
    }

    // En-passant is a move executed by pawns, where they capture the enemy pawn by going behind it diagonally.
    fun enPassant(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean{
        if (army==ChessArmy.WHITE && blackEnPassantFlag!=destColumn || army==ChessArmy.BLACK && whiteEnPassantFlag!=destColumn) return false
        if ((curRow != 4 && army == ChessArmy.WHITE) || (curRow != 3 && army == ChessArmy.BLACK)) return false
        if (pieceAt(destColumn, destRow) == null) {
            var enemyPiece = pieceAt(destColumn, destRow - 1)
            if (army == ChessArmy.BLACK) enemyPiece = pieceAt(destColumn, destRow + 1)
            if (abs(destColumn - curColumn) == 1 && enemyPiece != null) {
                if (enemyPiece.army == army) return false
                if (enemyPiece.rank != ChessRank.PAWN) return false
                piecesSet.remove(enemyPiece)
                return true
            }
            return false
        }
        return false
    }

    // Castling function for the King pieces ( King hides behind a rook )
    fun castle(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int, leftCastle: Boolean): Boolean{
        piecesSet.remove(pieceAt(curColumn, curRow))
        piecesSet.remove(pieceAt(destColumn, destRow))
        var newKingCol = 0
        var newKingRow = 0
        var newRookCol = 0
        var newRookRow = 0
        var enemy: ChessArmy

        // Left Castle
        if (leftCastle) {
            if(army==ChessArmy.WHITE) {
                piecesSet.add(ChessPiece(2, 0, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
                piecesSet.add(ChessPiece(3, 0, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))
                newKingCol = 2; newKingRow = 0; newRookCol = 3; newRookRow = 0; enemy = ChessArmy.BLACK
            }else{
                piecesSet.add(ChessPiece(2, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
                piecesSet.add(ChessPiece(3, 7, ChessArmy.BLACK, ChessRank.ROOK, R.drawable.rook_black))
                newKingCol = 2; newKingRow = 7; newRookCol = 3; newRookRow = 7; enemy = ChessArmy.WHITE
            }
        // Right Castle
        }else{
            if(army==ChessArmy.WHITE) {
                piecesSet.add(ChessPiece(6, 0, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
                piecesSet.add(ChessPiece(5, 0, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))
                newKingCol = 6; newKingRow = 0; newRookCol = 5; newRookRow = 0; enemy = ChessArmy.BLACK
            }else{
                piecesSet.add(ChessPiece(6, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))
                piecesSet.add(ChessPiece(5, 7, ChessArmy.BLACK, ChessRank.ROOK, R.drawable.rook_black))
                newKingCol = 6; newKingRow = 7; newRookCol = 5; newRookRow = 7; enemy = ChessArmy.WHITE
            }
        }
        var pic: Int = if(army==ChessArmy.WHITE)  R.drawable.king_white else R.drawable.king_black
        var pic2: Int = if(army==ChessArmy.WHITE)  R.drawable.rook_white else R.drawable.rook_black
        if(isKingChecked(enemy)){//rollback
            piecesSet.remove(pieceAt(newKingCol, newKingRow)); piecesSet.remove(pieceAt(newRookCol, newRookRow))
            piecesSet.add(ChessPiece(curColumn, curRow, army, ChessRank.KING, pic))
            piecesSet.add(ChessPiece(destColumn, destRow, army, ChessRank.ROOK, pic2))
            return false
        }
        return true
    }
    // ===================== SPECIAL MOVES : [End] ====================

    // ===================== PIECES' MOVEMENT RULES : [Start] =========================

    // Pawns can only move one Square forward but are able to move two Squares on their first move.
    fun canPawnMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (curColumn == destColumn) {
            // val p = pieceAt(curColumn, curRow)
            if (curRow == 1 && army == ChessArmy.WHITE && pieceAt(curColumn, destRow) == null) { // First move of a White pawn.
                if(destRow == 3) whiteEnPassantFlag = destColumn //passantable
                return destRow == 2 || destRow == 3
            }
            if (curRow == 6 && army == ChessArmy.BLACK && pieceAt(curColumn, destRow) == null) { // First move of a Black pawn.
                if(destRow == 4) blackEnPassantFlag = destColumn //passantable
                return destRow == 5 || destRow == 4
            }
            if ((army == ChessArmy.WHITE && destRow == curRow+1 && pieceAt(destColumn,destRow) == null) //move one square forward.
                || (army == ChessArmy.BLACK && destRow == curRow-1) && pieceAt(destColumn,destRow) == null) return true

            // Captures with White pawn.
        }else if (army == ChessArmy.WHITE && abs(destColumn - curColumn) ==1 && destRow == curRow+1) {
            if (pieceAt(destColumn, destRow) != null) return true // Simple diagonal capture
            else if (enPassant(army, curColumn, curRow, destColumn, destRow)) return true // En-passant

            // Captures with Black pawn.
        }else if(army == ChessArmy.BLACK && abs(destColumn - curColumn) ==1 && destRow == curRow-1) {
            if (pieceAt(destColumn, destRow) != null) return true // Simple diagonal capture
            else if (enPassant(army, curColumn, curRow, destColumn, destRow)) return true // En-passant
        }
        return false
    }

    // Rooks can only move vertically in the same column or horizontally in the same row as their current square.
    private fun canRookMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        var y:Boolean =  curColumn == destColumn && isGapVertically(curColumn, curRow, destColumn, destRow) ||
                curRow == destRow && isGapHorizontally(curColumn, curRow, destColumn, destRow)
        if(y && army == ChessArmy.WHITE && curColumn == 0 && curRow==0) whiteLeftCastleFlag = false
        if(y && army == ChessArmy.WHITE && curColumn == 7 && curRow==0) whiteRightCastleFlag = false
        if(y && army == ChessArmy.BLACK && curColumn == 0 && curRow==7) blackLeftCastleFlag = false
        if(y && army == ChessArmy.BLACK && curColumn == 7 && curRow==7) blackRightCastleFlag = false
        return y
    }

    // Knights can only move in an L from their current square.
    private fun canKnightMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        return abs(curColumn - destColumn) == 2 && abs(curRow - destRow) == 1 ||
                abs(curColumn - destColumn) == 1 && abs(curRow - destRow) == 2
    }

    // Bishops can only move diagonally.
    private fun canBishopMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        if (abs(curColumn - destColumn) == abs(curRow - destRow)) return isGapDiagonally(curColumn, curRow, destColumn, destRow)
        else return false
    }

    // Queens can move vertically, horizontally and diagonally.
    private fun canQueenMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        return canRookMove(army, curColumn, curRow, destColumn, destRow) || canBishopMove(army, curColumn, curRow, destColumn, destRow)
    }

    // Kings can only move one square vertically, horizontally or diagonally.
    private fun canKingMove(army: ChessArmy, curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): Boolean {
        //The Enemy Army
        val enemy: ChessArmy = if(army == ChessArmy.WHITE) ChessArmy.BLACK else ChessArmy.WHITE

        // White Left Castling
        if (army == ChessArmy.WHITE && whiteLeftCastleFlag){
            if (destColumn == 0 && destRow == 0 && isGapHorizontally(curColumn, curRow, destColumn, destRow)){
                if(castle(army, curColumn, curRow, destColumn, destRow, true)){
                    whiteLeftCastleFlag = false
                    whiteRightCastleFlag = false
                }
                return false
            }
        }
        // White Right Castling
        if (army == ChessArmy.WHITE && whiteRightCastleFlag){
            if (destColumn == 7 && destRow == 0 && isGapHorizontally(curColumn, curRow, destColumn, destRow)){
                if(castle(army, curColumn, curRow, destColumn, destRow, false)) {
                    whiteRightCastleFlag = false
                    whiteLeftCastleFlag = false
                }
                return false
            }
        }
        // Black Left Castling
        if (army == ChessArmy.BLACK && blackLeftCastleFlag){
            if (destColumn == 0 && destRow == 7 && isGapHorizontally(curColumn, curRow, destColumn, destRow)){
                if(castle(army, curColumn, curRow, destColumn, destRow, true)) {
                    blackLeftCastleFlag = false
                    blackRightCastleFlag = false
                }
                return false
            }
        }
        // Black Right Castling
        if (army == ChessArmy.BLACK && blackRightCastleFlag){
            if (destColumn == 7 && destRow == 7 && isGapHorizontally(curColumn, curRow, destColumn, destRow)){
                if(castle(army, curColumn, curRow, destColumn, destRow, false)) {
                    blackRightCastleFlag = false
                    blackLeftCastleFlag = false
                }
                return false
            }
        }
        // Kings can move only by one (1) square towards any direction
        if (canQueenMove(army, curColumn, curRow, destColumn, destRow)) {
            var b: Boolean  = !isThreatened(destColumn,destRow,enemy) &&
                    (abs(curColumn - destColumn) == 1 && abs(curRow - destRow) == 1 || abs(curColumn - destColumn) + abs(curRow - destRow) == 1)

            if(b && army == ChessArmy.WHITE) {
                whiteRightCastleFlag = false
                whiteLeftCastleFlag = false
            }
            if(b && army == ChessArmy.BLACK) {
                blackRightCastleFlag = false
                blackLeftCastleFlag = false
            }
            return b
        }else return false
    }

    // Check if a piece is able to move to a given square in its given state
    fun canMove(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int, curRound: ChessArmy = round, doRoundCheck: Boolean = true): Boolean {
        if (curColumn == destColumn && curRow == destRow) return false // Can't move to the same square you're already located in
        val movingPiece = pieceAt(curColumn, curRow) ?: return false // If an empty square is selected (no piece inside it), return false
        if (movingPiece.army != curRound) if (doRoundCheck) return false // doRoundCheck is false when we want to check if
                                                                         // a piece from the enemy army can make a move
                                                                         // ( Used when trying to see if the enemy king has been checkmated )
        return when(movingPiece.rank) {
            ChessRank.PAWN -> canPawnMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
            ChessRank.ROOK -> canRookMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
            ChessRank.KNIGHT -> canKnightMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
            ChessRank.BISHOP -> canBishopMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
            ChessRank.QUEEN -> canQueenMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
            ChessRank.KING -> canKingMove(movingPiece.army, curColumn, curRow, destColumn, destRow)
        }
    }
    // ========================= PIECES' MOVEMENT RULES : [End] ===============================


    // ========================= THREAT CHECKS : [Start] ===========================

    // Check if given square is threatened by some piece of the Enemy Army ( Used by isKingChecked() and isKingCheckMated() )
    fun isThreatened(column: Int, row: Int, enemy: ChessArmy): Boolean{
        for (piece in piecesSet){
            if (piece.army == enemy && piece.rank != ChessRank.KING)
                if (canMove(piece.column, piece.row, column, row, enemy)) return true
        }
        return false
    }

    // Check if the Enemy King is threatened ( Checked by a friendly piece )
    fun isKingChecked(curRound: ChessArmy): Boolean{
        for (piece in piecesSet){
            if (piece.rank == ChessRank.KING && piece.army != curRound)
                if (isThreatened(piece.column, piece.row, curRound)) return true
        }
        return false
    }

    // Checks if king is Check-mated
    fun isKingCheckMated(curRound: ChessArmy): Boolean{
        for (piece in piecesSet){
            if (piece.rank == ChessRank.KING && piece.army != curRound){
                val column = piece.column
                val row = piece.row
                val army = piece.army// We pass the king piece's army to isThreatened instead of the current round,
                                     // to see if the king is checkmated, without needing to wait for the next round
                val moveUp: Boolean = canMove(column, row, column, row+1, army, false)
                val moveDown: Boolean = canMove(column, row, column, row-1, army, false)
                val moveLeft: Boolean = canMove(column, row, column-1, row, army, false)
                val moveRight: Boolean = canMove(column, row, column+1, row, army, false)
                val moveUpLeft: Boolean = canMove(column, row, column-1, row+1, army, false)
                val moveUpRight: Boolean = canMove(column, row, column+1, row+1, army, false)
                val moveDownLeft: Boolean = canMove(column, row, column-1, row-1, army, false)
                val moveDownRight: Boolean = canMove(column, row, column+1, row-1, army, false)

                // Check if the King can move if depending on his current position:
                // 1. Top Left Corner
                if (column == 0 && row == 7) return ( !moveDown && !moveRight && !moveDownRight)
                // 2. Top Right Corner
                else if (column == 7 && row == 7) return ( !moveDown && !moveLeft && !moveDownLeft)
                // 3. Bottom Left Corner
                else if (column == 0 && row == 0) return ( !moveUp && !moveRight && !moveUpRight)
                // 4. Bottom Right Corner
                else if (column == 7 && row == 0) return ( !moveUp && !moveLeft && !moveUpLeft)
                // 5. Top Row (not corner)
                else if (column != 0 && column != 7 && row == 7) return ( !moveDown && !moveRight && !moveLeft && !moveDownRight && !moveDownLeft)
                // 6. Bottom Row (not corner)
                else if (column != 0 && column != 7 && row == 0) return ( !moveUp && !moveRight && !moveLeft && !moveUpLeft && !moveUpRight)
                // 7. Leftmost Column (not corner)
                else if (column == 0 && row != 0 && row != 7) return ( !moveUp && !moveDown && !moveRight && !moveUpRight && !moveDownRight)
                // 8. Rightmost Column (not corner)
                else if (column == 7 && row != 0 && row != 7) return ( !moveUp && !moveDown && !moveLeft && !moveUpLeft && !moveDownLeft)
                // 9. Rest of the Board
                else if (column != 0 && row != 0 && column != 7 && row != 7)
                    return (!moveUp && !moveDown && !moveLeft && !moveRight && !moveUpLeft && !moveUpRight && !moveDownLeft && !moveDownRight)
                // 10. King is able to move (not checkmated)
                else return false
            }
        }
        return false
    }
    // ===================== THREAT CHECKS [End] =============================



//================================== M A I N - C O R E - F U N C T I O N S ====================================================

    // Returns the rank of the piece at a certain square, if there is a piece on that square
    fun pieceAt(column: Int, row: Int) : ChessPiece? {
        for (piece in piecesSet) if (column == piece.column && row == piece.row) return piece
        return null
    }

    // Carries out the movement of pieces on the Chess Board and the Changing of the Rounds
    fun movePiece(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int) {
        if (canMove(curColumn, curRow, destColumn, destRow)){
            // If we try to move the piece to square where it is already located
            if (curColumn == destColumn && curRow == destRow) return

            // Select the piece you want to move is located by getting it from its square
            val selectedPiece = pieceAt(curColumn, curRow) ?: return

            val enemyPiece = pieceAt(destColumn, destRow)
            if (enemyPiece != null && enemyPiece.army == selectedPiece.army) return

            // If there is an enemy piece in that square, remove it and put the selected piece in that square
            if (enemyPiece != null) piecesSet.remove(enemyPiece)
            piecesSet.remove(selectedPiece)
            piecesSet.add(ChessPiece(destColumn, destRow, selectedPiece.army, selectedPiece.rank, selectedPiece.resID))

            // If Pawn reaches the opposite side. Upgrade it into a Queen
            if(selectedPiece.rank == ChessRank.PAWN && ( destRow == 7 || destRow == 0)) pawnUpgrade(selectedPiece.army,destColumn,destRow)

            val enemy = if(round==ChessArmy.WHITE) ChessArmy.BLACK else ChessArmy.WHITE
            if(isKingChecked(enemy)){//invalid move, rollback and return
                val selectedPiece = pieceAt(destColumn, destRow) ?: return
                piecesSet.remove(selectedPiece)
                piecesSet.add(ChessPiece(curColumn, curRow, selectedPiece.army, selectedPiece.rank, selectedPiece.resID))
                if(enemyPiece!=null) //resurrect it
                    piecesSet.add(ChessPiece(destColumn, destRow, enemyPiece.army, enemyPiece.rank, enemyPiece.resID))
                return
            }
            else{
                var roundString: String = if(round==ChessArmy.WHITE) "White" else "Black"
                println(roundString +", "+ isKingChecked(enemy) +", "+ (selectedPiece.rank != ChessRank.KING))
            }
            var roundString: String = if(round==ChessArmy.WHITE) "White" else "Black"
            var rankString: String = when(selectedPiece.rank){
                ChessRank.PAWN-> "Pawn"
                ChessRank.ROOK-> "Rook"
                ChessRank.KNIGHT-> "Knight"
                ChessRank.BISHOP-> "Bishop"
                ChessRank.QUEEN-> "Queen"
                ChessRank.KING-> "King"
            }
            // Check the Enemy King has been Checked by a Friendly Piece moved in this Round
            if (isKingChecked(round)){
                if (round == ChessArmy.WHITE) blackChecked = true
                else whiteChecked = true
            }
            // Check if the Enemy King has been Checkmated before changing Round
            if (isKingCheckMated(round)){
                if (round == ChessArmy.WHITE) blackCheckMated = true
                else whiteChecked = true
            }
            // Change Round
            moveString = moveToString(curColumn, curRow, destColumn, destRow) // update the move string to send it to the Database (applies to online games)
            for (p in piecesSet) if (p.army != round && p.rank == ChessRank.PAWN) p.hasMoved = false // set enemy pawns to "not moved" state so that they now cannot be captured through en-passant

            if(round == ChessArmy.WHITE) {
                round = ChessArmy.BLACK
                blackEnPassantFlag = -1
                whiteChecked = false
            }
            else if (round == ChessArmy.BLACK) {
                round = ChessArmy.WHITE
                whiteEnPassantFlag = -1
                blackChecked = false
            }
        }else return
    }

    // Reset the Chess Board into its original state
    fun reset() {
        moveString = ""
        round = ChessArmy.WHITE
        whiteEnPassantFlag = -1
        blackEnPassantFlag = -1
        whiteRightCastleFlag = true
        whiteLeftCastleFlag = true
        blackRightCastleFlag = true
        blackLeftCastleFlag = true
        whiteChecked = false
        blackChecked = false
        whiteCheckMated = false
        blackCheckMated = false

        // Clear the Chessboard before re-arranging the pieces
        piecesSet.removeAll(piecesSet)

        //Kings and Queens are unique pieces, so we arrange them by one
        piecesSet.add(ChessPiece(3, 0, ChessArmy.WHITE, ChessRank.QUEEN, R.drawable.queen_white))
        piecesSet.add(ChessPiece(3, 7, ChessArmy.BLACK, ChessRank.QUEEN, R.drawable.queen_black))
        piecesSet.add(ChessPiece(4, 0, ChessArmy.WHITE, ChessRank.KING, R.drawable.king_white))
        piecesSet.add(ChessPiece(4, 7, ChessArmy.BLACK, ChessRank.KING, R.drawable.king_black))

        // Bishops, Knights and Rooks have come in pairs of 2 so we arrange them by two
        for (i in 0..1) {
            piecesSet.add(ChessPiece(2 + i * 3, 0, ChessArmy.WHITE, ChessRank.BISHOP, R.drawable.bishop_white))
            piecesSet.add(ChessPiece(2 + i * 3, 7, ChessArmy.BLACK, ChessRank.BISHOP, R.drawable.bishop_black))

            piecesSet.add(ChessPiece(1 + i * 5, 0, ChessArmy.WHITE, ChessRank.KNIGHT, R.drawable.knight_white))
            piecesSet.add(ChessPiece(1 + i * 5, 7, ChessArmy.BLACK, ChessRank.KNIGHT, R.drawable.knight_black))

            piecesSet.add(ChessPiece(0 + i * 7, 0, ChessArmy.WHITE, ChessRank.ROOK, R.drawable.rook_white))
            piecesSet.add(ChessPiece(0 + i * 7, 7, ChessArmy.BLACK, ChessRank.ROOK, R.drawable.rook_black))
        }
        // Pawns come in two groups of 8
        for (i in 0..7) {
            piecesSet.add(ChessPiece(i, 1, ChessArmy.WHITE, ChessRank.PAWN, R.drawable.pawn_white))
            piecesSet.add(ChessPiece(i, 6, ChessArmy.BLACK, ChessRank.PAWN, R.drawable.pawn_black))
        }
    }

    // Take the executed move's coordinates and turn them into a string
    fun moveToString(curColumn: Int, curRow: Int, destColumn: Int, destRow: Int): String {
        val curColumnString = curColumn.toString()
        val curRowString = curRow.toString()
        val destColumnString = destColumn.toString()
        val destRowString = destRow.toString()
        val string: String = "$curColumnString,$curRowString,$destColumnString,$destRowString"
        return string
    }

    // Get the move made by the enemy player and execute it locally on this player's device
    fun update( moveString: String){
        if(moveString == ""){
            println("Opponent made an invalid move")
        }else{
            val data = moveString.split(",").map { it.toInt() }
            movePiece(data[0],data[1],data[2],data[3])
        }
    }
}
