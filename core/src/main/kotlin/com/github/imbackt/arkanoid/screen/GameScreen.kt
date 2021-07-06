package com.github.imbackt.arkanoid.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.imbackt.arkanoid.UNIT_SCALE
import com.github.imbackt.arkanoid.ecs.component.RenderComponent
import com.github.imbackt.arkanoid.ecs.component.TransformComponent
import com.github.imbackt.arkanoid.ecs.component.transformComponent
import com.github.imbackt.arkanoid.ecs.system.RenderSystem
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with
import ktx.graphics.use
import ktx.log.logger

private val LOG = logger<GameScreen>()

class GameScreen : KtxScreen {
    private val vaus = Texture("graphics/vaus_00.png")
    private val sprite = Sprite(vaus)
    private val batch by lazy { SpriteBatch() }
    private val gameViewport = FitViewport(9f, 16f)

    private val engine: Engine by lazy {
        PooledEngine().apply {
            addSystem(RenderSystem(batch, gameViewport))
        }
    }

    override fun show() {
        val paddle = engine.entity {
            with<RenderComponent> {
                sprite.setRegion(vaus)
            }
            with<TransformComponent> {
                position.set(1f, 1f, 0f)
                size.set(vaus.width * UNIT_SCALE, vaus.height * UNIT_SCALE)
            }
        }

        paddle.transformComponent.run {
            sprite.setBounds(
                position.x,
                position.y,
                size.x,
                size.y
            )
        }
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height)
    }

    override fun dispose() {
        vaus.dispose()
        batch.dispose()
    }
}