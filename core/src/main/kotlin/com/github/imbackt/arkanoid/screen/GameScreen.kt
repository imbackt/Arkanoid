package com.github.imbackt.arkanoid.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.imbackt.arkanoid.Arkanoid.Companion.UNIT_SCALE
import com.github.imbackt.arkanoid.Arkanoid.Companion.V_HEIGHT
import com.github.imbackt.arkanoid.Arkanoid.Companion.V_HEIGHT_PIXELS
import com.github.imbackt.arkanoid.Arkanoid.Companion.V_WIDTH
import com.github.imbackt.arkanoid.Arkanoid.Companion.V_WIDTH_PIXELS
import com.github.imbackt.arkanoid.ecs.component.Box2DComponent
import com.github.imbackt.arkanoid.ecs.component.PlayerComponent
import com.github.imbackt.arkanoid.ecs.component.RenderComponent
import com.github.imbackt.arkanoid.ecs.component.TransformComponent
import com.github.imbackt.arkanoid.ecs.system.Box2DDebugRenderSystem
import com.github.imbackt.arkanoid.ecs.system.PaddleMovementSystem
import com.github.imbackt.arkanoid.ecs.system.RenderSystem
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.body
import ktx.box2d.box
import ktx.box2d.circle
import ktx.box2d.createWorld
import ktx.log.logger
import ktx.math.vec2

class GameScreen : KtxScreen {
    private val paddle = Texture("graphics/paddle.png")
    private val ball = Texture("graphics/ball.png")
    private val batch by lazy { SpriteBatch() }
    private val gameViewport = FitViewport(V_WIDTH.toFloat(), V_HEIGHT.toFloat())
    private val uiViewport = FitViewport(V_WIDTH_PIXELS.toFloat(), V_HEIGHT_PIXELS.toFloat())
    private val world = createWorld(vec2(0f, -9.81f), true)
    private val box2DDebugRenderer = Box2DDebugRenderer()
    private val engine: Engine by lazy {
        PooledEngine().apply {
            addSystem(PaddleMovementSystem(gameViewport))
            addSystem(RenderSystem(batch, gameViewport, uiViewport))
            addSystem(Box2DDebugRenderSystem(world, gameViewport, box2DDebugRenderer))
        }
    }

    override fun show() {
        engine.entity {
            with<PlayerComponent>()
            with<RenderComponent> {
                sprite.setRegion(paddle)
                sprite.color = Color.BLUE
            }
            with<Box2DComponent> {
                body = world.body {
                    type = BodyDef.BodyType.KinematicBody
                    position.set(V_WIDTH / 2f, 2f)
                    box(paddle.width * UNIT_SCALE, paddle.height * UNIT_SCALE)
                }
            }
            with<TransformComponent> {
                size.set(paddle.width * UNIT_SCALE, paddle.height * UNIT_SCALE)
                position.set(
                    V_WIDTH / 2f - (paddle.width * UNIT_SCALE) / 2f,
                    2f - (paddle.height * UNIT_SCALE) / 2f,
                    0f
                )
            }
        }

        engine.entity {
            with<RenderComponent> {
                sprite.setRegion(ball)
                sprite.color = Color.GREEN
            }
            with<Box2DComponent> {
                body = world.body {
                    type = BodyDef.BodyType.DynamicBody
                    position.set(V_WIDTH / 2f, V_HEIGHT / 2f)
                    circle(ball.width * UNIT_SCALE * 0.5f) {
                        restitution = 1.0f
                    }
                }
            }
            with<TransformComponent>() {
                size.set(ball.width * UNIT_SCALE, ball.height * UNIT_SCALE)
                position.set(
                    V_WIDTH / 2f - (ball.width * UNIT_SCALE) / 2f,
                    V_HEIGHT / 2f - (ball.height * UNIT_SCALE) / 2f,
                    0f
                )
            }
        }
    }

    override fun render(delta: Float) {
        engine.update(delta)
        //world.step(delta, 6, 2)
    }

    override fun resize(width: Int, height: Int) {
        uiViewport.update(width, height)
        gameViewport.update(width, height)
    }

    override fun dispose() {
        paddle.dispose()
        batch.dispose()
    }

    companion object {
        private val LOG = logger<GameScreen>()
    }
}