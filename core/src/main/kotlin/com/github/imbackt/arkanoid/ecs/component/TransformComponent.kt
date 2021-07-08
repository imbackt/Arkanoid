package com.github.imbackt.arkanoid.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.math.vec2

class TransformComponent : Component, Pool.Poolable {
    val position = vec2()
    val size = vec2(1f, 1f)
    val width: Float
        get() = size.x
    val height: Float
        get() = size.y

    override fun reset() {
        position.set(0f, 0f)
        size.set(1f, 1f)
    }

    companion object {
        val MAPPER = mapperFor<TransformComponent>()
    }
}

val Entity.transformComponent: TransformComponent
    get() = this[TransformComponent.MAPPER]
        ?: throw GdxRuntimeException("TransformComponent for entity '$this' is null!")