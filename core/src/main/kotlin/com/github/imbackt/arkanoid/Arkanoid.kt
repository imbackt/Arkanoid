package com.github.imbackt.arkanoid

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.github.imbackt.arkanoid.screen.GameScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<Arkanoid>()
const val UNIT_SCALE = 1 / 64f

class Arkanoid : KtxGame<KtxScreen>() {

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Create game instance" }
        addScreen(GameScreen())
        setScreen<GameScreen>()
    }
}