package com.sc19kb.android.chessapplication

/*
* ------ CHESSBOARD VIEW ACTIVITY -------
*
* Creates the Chessboard User Interface on
* which the two players will be able to play
* a game of chess.
*/

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ChessBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val originX = 20f
    private val originY = 200f
    private val cellSize =130f

    private final val imgResIDs = setOf(
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
    private final val bitmaps = mutableMapOf<Int, Bitmap>()
    private final val paint = Paint()

    init {
        loadBitmaps()
    }

    override fun onDraw(canvas: Canvas?) {
        drawChessboard(canvas)
        drawPieces(canvas)
    }

    private fun drawPieces(canvas: Canvas?) {

        //Kings and Queens are unique pieces, so we arrange them by one
        drawPieceAt(canvas, 3,0, R.drawable.queen_white)
        drawPieceAt(canvas, 3,7, R.drawable.queen_black)
        drawPieceAt(canvas, 4,0, R.drawable.king_white)
        drawPieceAt(canvas, 4,7, R.drawable.king_black)

        // Bishops, Knights and Rooks have come in pairs of 2 so we arrange them by two
        for (i in 0..1) {
            drawPieceAt(canvas, 2 + i * 3, 0, R.drawable.bishop_white)
            drawPieceAt(canvas, 2 + i * 3, 7, R.drawable.bishop_black)
            drawPieceAt(canvas, 1 + i * 5, 0, R.drawable.king_white)
            drawPieceAt(canvas, 1 + i * 5, 7, R.drawable.knight_black)
            drawPieceAt(canvas, 0 + i * 7, 0, R.drawable.rook_white)
            drawPieceAt(canvas, 0 + i * 7, 7, R.drawable.rook_black)
        }

        // Pawns come in two groups of 8
        for (i in 0..7){
            drawPieceAt(canvas, i,1, R.drawable.pawn_white)
            drawPieceAt(canvas, i,6, R.drawable.pawn_black)
        }
    }

    // Draws a given piece inside a specified cell's borders
    private fun drawPieceAt(canvas: Canvas?, col: Int, row: Int, resID: Int) {
        val bitmap = bitmaps[resID]!!
        canvas?.drawBitmap(bitmap, null, RectF(originX + col * cellSize,originY + (7 - row) * cellSize,originX + (col + 1) * cellSize,originY + ((7 - row) + 1) * cellSize), paint)
    }

    private fun loadBitmaps() {
        imgResIDs.forEach {
            bitmaps[it] = BitmapFactory.decodeResource(resources, it)
        }
    }

    // Draws the Chessboard background white and black squares
    private fun drawChessboard(canvas: Canvas?) {
        val paintWhite = Paint()
        // (We use the #757575 shade of Gray instead of White because of contrast with the background and the white pieces)
        paintWhite.color = Color.parseColor("#757575")

        val paintBlack = Paint()
        // (We use the #212121 Gray instead of Black because of contrast with the black pieces)
        paintBlack.color = Color.parseColor("#212121")

        /*
         We start counting from square A8 ( (0,7) in the Chessboard Console output string)
         if the sum of the column and the row number is an even number, then we paint the square white.
         If it is an odd number, we paint it black.
         */

        // draw every column
        for (i in 0..7) {
            // draw every row inside a column
            for (j in 0..7) {
                if ((i+j) % 2 == 0){
                    canvas?.drawRect(originX + i * cellSize, originY + j * cellSize, originX + (i + 1)* cellSize, originY + (j + 1) * cellSize, paintWhite)

                }else{
                    canvas?.drawRect(originX + i * cellSize, originY + j * cellSize, originX + (i + 1)* cellSize, originY + (j + 1) * cellSize, paintBlack)
                }

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