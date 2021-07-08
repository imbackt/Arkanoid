package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.imbackt.arkanoid.ecs.component.PaddleComponent
import com.github.imbackt.arkanoid.ecs.component.RemoveComponent
import com.github.imbackt.arkanoid.ecs.component.box2DComponent
import com.github.imbackt.arkanoid.ecs.component.transformComponent
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.math.vec2

class PaddleMovementSystem(
    private val viewport: Viewport
) : IteratingSystem(allOf(PaddleComponent::class).exclude(RemoveComponent::class).get()) {
    private val tmpVec = vec2()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val body = entity.box2DComponent.body
        val transform = entity.transformComponent
        tmpVec.x = Gdx.input.x.toFloat()
        viewport.unproject(tmpVec)
        val x = MathUtils.clamp(tmpVec.x, 1f + transform.width / 2f, 8f - transform.width / 2f)
        body.setTransform(x, body.position.y, 0f)
    }
}