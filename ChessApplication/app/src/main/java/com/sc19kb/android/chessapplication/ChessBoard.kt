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
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChessBoard(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    
    private val originX = 20f
    private val originY = 200f
    private val cellSize =130f

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        val paintWhite = Paint()
        // (we use Light Gray instead of White because of contrast with the background and the white pieces)
        paintWhite.color = Color.LTGRAY

        val paintBlack = Paint()
        // (we use Dark Gray instead of Black because of contrast with the black pieces)
        paintBlack.color = Color.DKGRAY

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