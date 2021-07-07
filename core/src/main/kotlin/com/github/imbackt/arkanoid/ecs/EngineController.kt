package com.github.imbackt.arkanoid.ecs

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.imbackt.arkanoid.Arkanoid
import com.github.imbackt.arkanoid.V_WIDTH
import com.github.imbackt.arkanoid.ecs.component.*
import com.github.imbackt.arkanoid.ecs.system.*
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.*
import ktx.math.vec2

class EngineController(
    private val game: Arkanoid,
    private val gameViewport: Viewport = game.gameViewport,
    private val world: World = game.world,
    private val box2DDebugRenderer: Box2DDebugRenderer = game.box2DDebugRenderer,
    private val engine: PooledEngine = game.engine
) {

    private val paddle = engine.entity {
        val playerCmp = with<PlayerComponent> {
            width = 2f
        }
        with<Box2DComponent> {
            body = world.body {
                type = BodyDef.BodyType.KinematicBody
                position.set(V_WIDTH / 2f, 2f)
                userData = "paddle"
                box(playerCmp.width, 0.25f) {
                    userData = "paddle"
                    friction = 0f
                }
            }
        }
    }

    fun addSystems() {
        val ballMovementSystem = BallMovementSystem(paddle)
        world.setContactListener(ballMovementSystem)
        engine.addSystem(Box2DRendererSystem(world, gameViewport, box2DDebugRenderer))
        engine.addSystem(PaddleMovementSystem(paddle, gameViewport))
        engine.addSystem(ballMovementSystem)
        engine.addSystem(BricksSpawnSystem(world))
    }

    fun spawnWalls() {
        val walls = engine.entity {
            with<Box2DComponent> {
                body = world.body {
                    userData = "walls"
                    type = BodyDef.BodyType.StaticBody
                    chain(floatArrayOf(1f, 0f, 1f, 15f, 8f, 15f, 8f, 0f)) {
                        userData = "walls"
                        friction = 0f
                    }
                }
            }
        }
    }

    fun spawnFloor() {
        val floor = engine.entity {
            with<Box2DComponent> {
                body = world.body {
                    type = BodyDef.BodyType.StaticBody
                    userData = "floor"
                    edge(vec2(0f, 1f), vec2(9f, 1f)) {
                        isSensor = true
                        userData = "floor"
                    }
                }
            }
        }
    }

    fun spawnInitialBall() {
        val initialBall = engine.entity {
            with<BallComponent> {
                isInitialBall = true
            }
            with<Box2DComponent> {
                body = world.body {
                    type = BodyDef.BodyType.KinematicBody
                    position.set(V_WIDTH / 2f, paddle.b2DCmp.body.position.y + 0.125f + 0.125f)
                    userData = "ball"
                    circle(0.125f) {
                        restitution = 1f
                        userData = "ball"
                        friction = 0f
                    }
                }
            }
        }
        engine.addSystem(InputSystem(paddle, initialBall))
    }

    fun spawnBricks() {
        val level = arrayOf(
            intArrayOf(1, 0, 1, 0, 1, 0, 1, 0, 1),
            intArrayOf(0, 1, 0, 1, 0, 1, 0, 1, 0),
            intArrayOf(1, 0, 1, 0, 1, 0, 1, 0, 1),
            intArrayOf(0, 1, 0, 1, 0, 1, 0, 1, 0),
            intArrayOf(1, 0, 1, 0, 1, 0, 1, 0, 1)
        )
        val bricks = Array<Entity>(level.size * level.first().size)
        for (i in level.indices) {
            for (j in 0 until level.first().size) {
                if (level[i][j] > 0) {
                    bricks.add(
                        engine.entity {
                            with<BrickComponent> {
                                hitPoints = level[i][j]
                            }
                            with<Box2DComponent> {
                                body = world.body {
                                    type = BodyDef.BodyType.StaticBody
                                    position.set(1.5f + 0.75f * j, 14.5f - 0.75f * i)
                                    userData = this@entity
                                    box(0.5f, 0.5f) {
                                        userData = this@entity
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}