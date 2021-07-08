package com.github.imbackt.arkanoid.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.github.imbackt.arkanoid.Arkanoid
import com.github.imbackt.arkanoid.ecs.component.*
import com.github.imbackt.arkanoid.ecs.system.*
import com.github.imbackt.arkanoid.utils.generatePattern
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.*
import ktx.math.vec2

class GameScreen(game: Arkanoid) : AbstractScreen(game) {
    private val engine = PooledEngine()
    private val world = createWorld(vec2(0f, -0.5f), true)
    private val box2DDebugRenderer = Box2DDebugRenderer()

    init {
        engine.run {
            addSystem(PaddleMovementSystem(gameViewport))
            addSystem(BallMovementSystem())
            addSystem(Box2DSystem())
            addSystem(RenderSystem(batch, gameViewport, uiViewport))
            addSystem(Box2DDebugRenderSystem(world, gameViewport, box2DDebugRenderer))
            addSystem(RemoveSystem())
        }
    }

    override fun show() {
        world.setContactListener(CollisionSystem())
        spawnWalls()
        spawnPaddle()
        val pattern = arrayOf(
            arrayOf(0, 0, 0, 0, 0, 0, 3),
            arrayOf(0, 0, 0, 0, 0, 3, 3),
            arrayOf(0, 0, 0, 0, 3, 3, 3),
            arrayOf(0, 0, 0, 2, 2, 2, 2),
            arrayOf(0, 0, 2, 2, 2, 2, 2),
            arrayOf(0, 1, 1, 1, 1, 1, 1),
            arrayOf(1, 1, 1, 1, 1, 1, 1)
        )
        generatePattern(pattern, engine, world)
        spawnBall()
    }

    private fun spawnWalls() {
        engine.entity {
            with<Box2DComponent> {
                body = world.body {
                    type = BodyDef.BodyType.StaticBody
                    chain(floatArrayOf(1f, 0f, 1f, 15f, 8f, 15f, 8f, 0f)) {
                        friction = 0f
                        userData = this@entity.entity
                    }
                    chain(floatArrayOf(0f, 1.5f, 9f, 1.5f)) {
                        isSensor = true
                        userData = this@entity.entity
                    }
                }
            }
        }
    }

    private fun spawnPaddle() {
        engine.entity {
            with<PaddleComponent>()
            val transform = with<TransformComponent> {
                size.set(2f, 0.25f)
            }
            with<Box2DComponent> {
                body = world.body {
                    type = BodyDef.BodyType.KinematicBody
                    position.set(4.5f, 2f)
                    box(transform.width, transform.height) {
                        friction = 0f
                        userData = this@entity.entity
                    }
                }
            }
            with<RenderComponent> {
                sprite.setRegion(Texture("graphics/paddle.png"))
            }
        }
    }

    private fun spawnBall() {
        engine.entity {
            with<BallComponent> {
                initialBall = true
            }
            val transform = with<TransformComponent> {
                size.set(0.25f, 0.25f)
            }
            with<Box2DComponent> {
                body = world.body {
                    type = BodyDef.BodyType.DynamicBody
                    position.set(4.5f, 2.3f)
                    circle(transform.width / 2f) {
                        friction = 0f
                        restitution = 1f
                        userData = this@entity.entity
                    }
                }
            }
            with<RenderComponent> {
                sprite.setRegion(Texture("graphics/ball.png"))
                sprite.color = Color.SLATE
            }
        }
    }

    override fun render(delta: Float) {
        engine.update(delta)
        world.step(1 / 60f, 6, 2)
    }

    override fun dispose() {
        world.dispose()
        box2DDebugRenderer.dispose()
    }
}