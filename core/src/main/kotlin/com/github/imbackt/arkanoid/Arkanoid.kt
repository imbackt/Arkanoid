package com.github.imbackt.arkanoid

import ktx.app.KtxGame
import ktx.app.KtxScreen

class Arkanoid : KtxGame<KtxScreen>() {
    override fun create() {
        addScreen(GameScreen())
        setScreen<GameScreen>()
    }
}