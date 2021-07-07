package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.github.imbackt.arkanoid.ecs.component.b2DCmp
import com.github.imbackt.arkanoid.ecs.component.playerCmp
import ktx.math.vec2

class InputSystem(
    paddle: Entity,
    initialBall: Entity
) : EntitySystem() {
    private val playerCmp = paddle.playerCmp
    private val ballB2DCmp = initialBall.b2DCmp
    override fun update(deltaTime: Float) {
        if (!playerCmp.isGameStarted) {
            when {
                Gdx.input.isButtonPressed(0) -> {
                    playerCmp.isGameStarted = true
                    ballB2DCmp.body.type = BodyType.DynamicBody
                    ballB2DCmp.body.applyForce(vec2(0f, 250f), ballB2DCmp.body.worldCenter, true)
                }
            }
        }
    }
}