package com.github.imbackt.arkanoid.screen

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.github.imbackt.arkanoid.Arkanoid
import ktx.graphics.use
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<GameScreen>()

class GameScreen(game: Arkanoid) : AbstractScreen(game) {
    private val texture = Texture("graphics/paddle.png")
    private val sprite = Sprite(texture)
    override fun show() {
        LOG.debug { "${this::class.simpleName} is shown!" }
        sprite.setPosition(1f, 1f)
    }

    override fun render(delta: Float) {
        batch.use {
            sprite.draw(it)
        }
    }

    override fun dispose() {
        texture.dispose()
    }
}