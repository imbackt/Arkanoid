package com.github.imbackt.arkanoid.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.imbackt.arkanoid.Arkanoid
import com.github.imbackt.arkanoid.ecs.EngineController
import ktx.app.KtxScreen

abstract class AbstractScreen(
    private val game: Arkanoid,
    private val uiViewport: Viewport = game.uiViewport,
    private val gameViewport: Viewport = game.gameViewport,
    protected val engine: PooledEngine = game.engine,
    protected val engineController: EngineController = game.engineController
) : KtxScreen {
    override fun resize(width: Int, height: Int) {
        uiViewport.update(width, height, true)
        gameViewport.update(width, height, true)
    }
}