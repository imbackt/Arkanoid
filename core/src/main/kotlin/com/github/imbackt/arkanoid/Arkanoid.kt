package com.github.imbackt.arkanoid

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.github.imbackt.arkanoid.screen.GameScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.log.debug
import ktx.log.logger

class Arkanoid : KtxGame<KtxScreen>() {

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Create game instance" }
        addScreen(GameScreen())
        setScreen<GameScreen>()
    }

    companion object {
        private val LOG = logger<Arkanoid>()
        const val UNIT_SCALE = 1 / 64f
        const val V_WIDTH = 9
        const val V_HEIGHT = 16
        const val V_WIDTH_PIXELS = 9 * 64
        const val V_HEIGHT_PIXELS = 16 * 64
    }
}