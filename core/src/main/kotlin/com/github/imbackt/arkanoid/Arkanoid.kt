package com.github.imbackt.arkanoid

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.imbackt.arkanoid.ecs.EngineController
import com.github.imbackt.arkanoid.ecs.system.InputSystem
import com.github.imbackt.arkanoid.screen.AbstractScreen
import com.github.imbackt.arkanoid.screen.GameScreen
import ktx.app.KtxGame
import ktx.box2d.createWorld
import ktx.log.debug
import ktx.log.logger
import ktx.math.vec2

private val LOG = logger<Arkanoid>()
const val V_WIDTH = 9
const val V_HEIGHT = 16
const val V_WIDTH_PIXELS = 9 * 32
const val V_HEIGHT_PIXELS = 16 * 32

class Arkanoid : KtxGame<AbstractScreen>() {
    val uiViewport = FitViewport(V_WIDTH_PIXELS.toFloat(), V_HEIGHT_PIXELS.toFloat())
    val gameViewport = FitViewport(V_WIDTH.toFloat(), V_HEIGHT.toFloat())
    val world = createWorld(vec2(0f, -0.5f), true)
    val box2DDebugRenderer by lazy { Box2DDebugRenderer() }
    val engine by lazy { PooledEngine() }
    val engineController by lazy { EngineController(this) }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        LOG.debug { "Creating Game Instance." }
        addScreen(GameScreen(this))
        setScreen<GameScreen>()
    }

    override fun dispose() {
        super.dispose()
        world.dispose()
        box2DDebugRenderer.dispose()
    }
}