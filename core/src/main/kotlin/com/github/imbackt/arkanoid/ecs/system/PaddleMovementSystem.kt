package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.imbackt.arkanoid.Arkanoid.Companion.V_WIDTH
import com.github.imbackt.arkanoid.ecs.component.PlayerComponent
import com.github.imbackt.arkanoid.ecs.component.box2DComponent
import com.github.imbackt.arkanoid.ecs.component.transformComponent
import ktx.ashley.allOf
import ktx.math.vec2

class PaddleMovementSystem(
    private val gameViewport: Viewport
) : IteratingSystem(allOf(PlayerComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transformComponent = entity.transformComponent
        val box2DComponent = entity.box2DComponent
        val tmpVec = vec2()
        tmpVec.x = Gdx.input.x.toFloat()
        gameViewport.unproject(tmpVec)
        box2DComponent.body.setTransform(
            MathUtils.clamp(
                tmpVec.x,
                transformComponent.size.x / 2f + 1f,
                V_WIDTH - transformComponent.size.x / 2f - 1f
            ),
            box2DComponent.body.position.y,
            0f
        )
        transformComponent.position.set(
            box2DComponent.body.position.x - transformComponent.size.x / 2f,
            box2DComponent.body.position.y - transformComponent.size.y / 2f,
            0f
        )
    }
}