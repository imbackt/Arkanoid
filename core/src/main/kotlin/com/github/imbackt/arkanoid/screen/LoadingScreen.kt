package com.github.imbackt.arkanoid.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.github.imbackt.arkanoid.Arkanoid
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<LoadingScreen>()

class LoadingScreen(game: Arkanoid) : AbstractScreen(game) {
    override fun show() {
        LOG.debug { "${this::class.simpleName} is shown!" }
    }

    override fun render(delta: Float) {
        when {
            Gdx.input.isKeyJustPressed(Input.Keys.A) -> game.setScreen<GameScreen>()
        }
    }
}