package com.github.imbackt.arkanoid

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.github.imbackt.arkanoid.screen.AbstractScreen
import com.github.imbackt.arkanoid.screen.GameScreen
import com.github.imbackt.arkanoid.screen.LoadingScreen
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<Arkanoid>()

class Arkanoid : KtxGame<AbstractScreen>() {
    val batch: Batch by lazy { SpriteBatch() }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Create game instance" }
        addScreen(GameScreen(this))
        addScreen(LoadingScreen(this))
        setScreen<GameScreen>()
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
    }
}