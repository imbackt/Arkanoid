package com.github.imbackt.arkanoid.screen

import com.github.imbackt.arkanoid.Arkanoid
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<GameScreen>()

class GameScreen(game: Arkanoid) : AbstractScreen(game) {

    override fun show() {
        LOG.debug { "Showing GameScreen" }
        engineController.addSystems()
        engineController.spawnWalls()
        engineController.spawnFloor()
        engineController.spawnInitialBall()
        engineController.spawnBricks()
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }
}