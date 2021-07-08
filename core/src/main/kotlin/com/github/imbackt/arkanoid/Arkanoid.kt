package com.github.imbackt.arkanoid

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.imbackt.arkanoid.screen.AbstractScreen
import com.github.imbackt.arkanoid.screen.GameScreen
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<Arkanoid>()
const val V_WIDTH = 9
const val V_HEIGHT = 16
const val V_WIDTH_PIXELS = 9 * 64
const val V_HEIGHT_PIXELS = 16 * 64

class Arkanoid : KtxGame<AbstractScreen>() {
    val batch: SpriteBatch by lazy { SpriteBatch() }
    val uiViewport = FitViewport(V_WIDTH_PIXELS.toFloat(), V_HEIGHT_PIXELS.toFloat())
    val gameViewport = FitViewport(V_WIDTH.toFloat(), V_HEIGHT.toFloat())

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        uiViewport.update(width, height, true)
        gameViewport.update(width, height, true)
    }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Creating Game Instance." }
        addScreen(GameScreen(this))
        setScreen<GameScreen>()
    }
}