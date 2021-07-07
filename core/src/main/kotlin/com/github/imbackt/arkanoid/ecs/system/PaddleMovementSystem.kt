package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.imbackt.arkanoid.ecs.component.b2DCmp
import com.github.imbackt.arkanoid.ecs.component.playerCmp
import ktx.math.vec2

class PaddleMovementSystem(
    private val paddle: Entity,
    private val gameViewport: Viewport
) : EntitySystem() {
    override fun update(deltaTime: Float) {
        val tmpVec = vec2()
        val paddleBody = paddle.b2DCmp.body
        val width = paddle.playerCmp.width
        tmpVec.x = Gdx.input.x.toFloat()
        gameViewport.unproject(tmpVec)
        val bounds = MathUtils.clamp(tmpVec.x, width / 2f + 1f, 9f - width / 2f - 1f)
        paddleBody.setTransform(bounds, paddleBody.position.y, 0f)
    }
}