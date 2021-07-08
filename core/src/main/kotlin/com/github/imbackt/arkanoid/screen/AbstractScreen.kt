package com.github.imbackt.arkanoid.screen

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.imbackt.arkanoid.Arkanoid
import ktx.app.KtxScreen

abstract class AbstractScreen(
    private val game: Arkanoid,
    val batch: SpriteBatch = game.batch,
    val uiViewport: Viewport = game.uiViewport,
    val gameViewport: Viewport = game.gameViewport,
) : KtxScreen