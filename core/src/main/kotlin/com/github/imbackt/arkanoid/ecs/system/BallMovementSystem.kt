package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.github.imbackt.arkanoid.ecs.component.*
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.math.vec2

class BallMovementSystem : IteratingSystem(allOf(BallComponent::class).exclude(RemoveComponent::class).get()) {
    private var isGameStarted = false

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val body = entity.box2DComponent.body
        val speed = entity.ballComponent.speed
        if (!isGameStarted) {
            val playerEntity =
                engine.getEntitiesFor(allOf(PaddleComponent::class).exclude(RemoveComponent::class).get()).first()
            if (playerEntity != null) {
                val playerBody = playerEntity.box2DComponent.body
                body.setTransform(playerBody.position.x, playerBody.position.y + 0.3f, 0f)
                when {
                    Gdx.input.isButtonPressed(0) -> {
                        body.linearVelocity = Vector2.Zero
                        body.applyForce(vec2(0f, 250f), body.worldCenter, true)
                        isGameStarted = true
                    }
                }
            }
        }
    }
}