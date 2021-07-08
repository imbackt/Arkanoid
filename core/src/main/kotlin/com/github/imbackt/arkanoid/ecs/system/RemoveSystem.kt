package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.imbackt.arkanoid.ecs.component.BrickComponent
import com.github.imbackt.arkanoid.ecs.component.RemoveComponent
import com.github.imbackt.arkanoid.ecs.component.removeComponent
import com.github.imbackt.arkanoid.ecs.component.transformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.debug
import ktx.log.logger

class RemoveSystem(
) : IteratingSystem(allOf(RemoveComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val removeComponent = entity.removeComponent

        removeComponent.delay -= deltaTime
        if (removeComponent.delay <= 0f) {
            engine.removeEntity(entity)
        }
    }

    companion object {
        private val LOG = logger<RemoveSystem>()
    }
}