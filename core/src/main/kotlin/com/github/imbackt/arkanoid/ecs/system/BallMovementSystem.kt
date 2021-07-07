package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.github.imbackt.arkanoid.ecs.component.*
import ktx.ashley.allOf
import ktx.math.vec2

class BallMovementSystem(
    private val paddle: Entity
) : IteratingSystem(allOf(BallComponent::class, Box2DComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val ballCmp = entity.ballCmp
        val playerCmp = paddle.playerCmp
        val b2DCmp = entity.b2DCmp
        val paddleB2DCmp = paddle.b2DCmp
        if (!playerCmp.isGameStarted) {
            followPaddle(entity, b2DCmp, paddleB2DCmp)
            return
        }
    }

    private fun followPaddle(entity: Entity, b2DCmp: Box2DComponent, paddleB2DCmp: Box2DComponent) {
        b2DCmp.body.setTransform(paddleB2DCmp.body.worldCenter.x, b2DCmp.body.worldCenter.y, 0f)
    }

}