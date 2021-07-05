package com.github.imbackt.arkanoid.screen

import com.badlogic.gdx.graphics.g2d.Batch
import com.github.imbackt.arkanoid.Arkanoid
import ktx.app.KtxScreen

abstract class AbstractScreen(
    val game: Arkanoid,
    val batch: Batch = game.batch
) : KtxScreen