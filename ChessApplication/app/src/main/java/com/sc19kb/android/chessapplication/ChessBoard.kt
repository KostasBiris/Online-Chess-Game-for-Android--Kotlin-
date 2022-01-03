package com.sc19kb.android.chessapplication

/*
* ------ CHESSBOARD VIEW ACTIVITY -------
*
* Creates the Chessboard User Interface on
* which the two players will be able to play
* a game of chess.
*/

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class ChessBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val imgResIDs = setOf(
        R.drawable.bishop_black,
        R.drawable.bishop_white,
        R.drawable.king_black,
        R.drawable.king_white,
        R.drawable.queen_black,
        R.drawable.queen_white,
        R.drawable.rook_black,
        R.drawable.rook_white,
        R.drawable.knight_black,
        R.drawable.knight_white,
        R.drawable.pawn_black,
        R.drawable.pawn_white,
    )
    private val scaleFactor = .9f

    // Coordinates of the first square on the Chessboard
    private var startingX = 20f
    private var startingY = 200f

    private var cellSize = 130f
    
    // (We use the #757575 shade of Gray instead of White because of contrast with the background and the white pieces)
    private val paintWhite = Color.parseColor("#757575")

    // (We use the #212121 shade of Gray instead of Black because of contrast with the black pieces)
    private val paintBlack = Color.parseColor("#212121")

    private val bitmaps = mutableMapOf<Int, Bitmap>()
    private val paint = Paint()
    private var selectedPieceBitmap: Bitmap? = null
    private var selectedPiece: ChessPiece? = null
    private var curColumn: Int = -1
    private var curRow: Int = -1

    // Selected Piece's coordinates
    private var selectedPieceX = -1f
    private var selectedPieceY = -1f

    var chessInterface: ChessInterface? = null

    init {
        loadBitmaps()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        val chessBoardSize = min(width, height) * scaleFactor
        cellSize = chessBoardSize / 8f
        startingX = (width - chessBoardSize) / 2f
        startingY = (height - chessBoardSize) / 2f

        drawChessboard(canvas)
        drawPieces(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        // When User touches the screen
        when (event.action) {

            // When user's finger is on the screen
            MotionEvent.ACTION_DOWN -> {
                curColumn = ((event.x - startingX) / cellSize).toInt()
                curRow = 7 - ((event.y - startingY) / cellSize).toInt()
            }

            // When the user lifts their finger from the screen
            MotionEvent.ACTION_UP -> {
                val column = ((event.x - startingX) / cellSize).toInt()
                val row = 7 - ((event.y - startingY) / cellSize).toInt()
                Log.d(TAG, "from ($curColumn, $curRow) to ($column, $row)")
                chessInterface?.movePiece(curColumn, curRow, column, row)
            }

            // When they move their finger on the screen
            MotionEvent.ACTION_MOVE -> {
                selectedPieceX = event.x
                selectedPieceY = event.y
                invalidate() // resets the movement variables
            }
        }
        return true
    }

    private fun drawPieces(canvas: Canvas) {
        // draw every piece in each row
        for (row in 0..7) {
            //draw every piece in each column in a row
            for (column in 0..7) {
                if (row != curRow || column != curColumn){
                    chessInterface?.pieceAt(column, row)?.let { drawPieceAt(canvas,column, row, it.resID) }
                }
            }
        }

        chessInterface?.pieceAt(curColumn, curRow)?.let {
            val bitmap = bitmaps[it.resID]!!
            canvas.drawBitmap(bitmap, null, RectF(selectedPieceX - cellSize/2, selectedPieceY - cellSize/2, selectedPieceX + cellSize/2,selectedPieceY + cellSize/2), paint)
        }
        
    }

    // Draws a given piece inside a specified cell's borders
    private fun drawPieceAt(canvas: Canvas, column: Int, row: Int, resID: Int) {
        val bitmap = bitmaps[resID]!!
        canvas.drawBitmap(bitmap, null, RectF(startingX + column * cellSize,startingY + (7 - row) * cellSize,startingX + (column + 1) * cellSize,startingY + ((7 - row) + 1) * cellSize), paint)
    }

    private fun loadBitmaps() {
        imgResIDs.forEach {
            bitmaps[it] = BitmapFactory.decodeResource(resources, it)
        }
    }

    // Draws a given piece inside a specified cell's borders
    private fun drawSquareAt(canvas: Canvas, column: Int, row: Int, isBlack: Boolean) {
        paint.color = if (isBlack) paintBlack else paintWhite
        canvas.drawRect(startingX + column * cellSize, startingY + row * cellSize, startingX + (column + 1)* cellSize, startingY + (row + 1) * cellSize, paint)
    }

    // Draws the Chessboard background white and black squares
    private fun drawChessboard(canvas: Canvas) {
        // draw every square in each row
        for (row in 0..7) {
            // draw every square in column in a row
            for (column in 0..7) {
                drawSquareAt(canvas, column, row, (column + row) % 2 == 1)
            }
        }
    }
}

/*
*                               White = 0 , Black = *
* =====================================================================================
*        CHESSBOARD OFFICIAL OUTLINE      ||           CHESSBOARD CONSOLE OUTLINE
* ________________________________________||________________________________________
*   8 | O   *   O   *   O   *   O   *     ||      7 | O   *   O   *   O   *   O   *
*   7 | *   O   *   O   *   O   *   0     ||      6 | *   O   *   O   *   O   *   0
*   6 | O   *   O   *   O   *   O   *     ||      5 | O   *   O   *   O   *   O   *
*   5 | *   O   *   O   *   O   *   0     ||      4 | *   O   *   O   *   O   *   0
*   4 | O   *   O   *   O   *   O   *     ||      3 | O   *   O   *   O   *   O   *
*   3 | *   O   *   O   *   O   *   0     ||      2 | *   O   *   O   *   O   *   0
*   2 | O   *   O   *   O   *   O   *     ||      1 | O   *   O   *   O   *   O   *
*   1 |_*___O___*___O___*___O___*___0_    ||      0 |_*___O___*___O___*___O___*___0_
*       A   B   C   D   E   F   G   H     ||          0   1   2   3   4   5   6   7
*
 */