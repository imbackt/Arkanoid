package com.github.imbackt.arkanoid.ecs

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.imbackt.arkanoid.Arkanoid
import com.github.imbackt.arkanoid.V_WIDTH
import com.github.imbackt.arkanoid.ecs.component.BallComponent
import com.github.imbackt.arkanoid.ecs.component.Box2DComponent
import com.github.imbackt.arkanoid.ecs.component.PlayerComponent
import com.github.imbackt.arkanoid.ecs.component.b2DCmp
import com.github.imbackt.arkanoid.ecs.system.BallMovementSystem
import com.github.imbackt.arkanoid.ecs.system.Box2DRendererSystem
import com.github.imbackt.arkanoid.ecs.system.InputSystem
import com.github.imbackt.arkanoid.ecs.system.PaddleMovementSystem
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
    var isGameStarted = false

    private val paddle = engine.entity {
        val playerCmp = with<PlayerComponent> {
            width = 2f
        }
        with<Box2DComponent> {
            body = world.body {
                type = BodyDef.BodyType.KinematicBody
                position.set(V_WIDTH / 2f, 2f)
                box(playerCmp.width, 0.25f)
            }
        }
    }

    fun addSystems() {
        engine.addSystem(Box2DRendererSystem(world, gameViewport, box2DDebugRenderer))
        engine.addSystem(PaddleMovementSystem(paddle, gameViewport))
        engine.addSystem(BallMovementSystem(paddle))
    }

    fun spawnWalls() {
        val walls = engine.entity {
            with<Box2DComponent> {
                body = world.body {
                    type = BodyDef.BodyType.StaticBody
                    chain(floatArrayOf(1f, 0f, 1f, 15f, 8f, 15f, 8f, 0f))
                }
            }
        }
    }

    fun spawnFloor() {
        val floor = engine.entity {
            with<Box2DComponent> {
                body = world.body {
                    type = BodyDef.BodyType.StaticBody
                    edge(vec2(0f, 1f), vec2(9f, 1f)) {
                        isSensor = true
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
                    circle(0.125f) {
                        restitution = 1f
                    }
                }
            }
        }
        engine.addSystem(InputSystem(paddle, initialBall))
    }
}