package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.github.imbackt.arkanoid.ecs.component.*
import ktx.ashley.allOf
import ktx.ashley.exclude

class Box2DSystem :
    IteratingSystem(
        allOf(
            TransformComponent::class, Box2DComponent::class
        ).exclude(RemoveComponent::class).get()
    ) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transformComponent = entity.transformComponent
        val box2DBody = entity.box2DComponent.body

        transformComponent.position.set(
            box2DBody.worldCenter.x - transformComponent.width / 2f,
            box2DBody.worldCenter.y - transformComponent.height / 2f
        )
    }
}