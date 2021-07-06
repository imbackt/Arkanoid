package com.github.imbackt.arkanoid.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.math.vec2
import ktx.math.vec3

class TransformComponent : Component, Pool.Poolable, Comparable<TransformComponent> {
    val position = vec3()
    val size = vec2()

    override fun reset() {
        position.set(0f, 0f, 0f)
        size.set(1f, 1f)
    }

    override fun compareTo(other: TransformComponent): Int {
        val zDiff = other.position.z.compareTo(position.z)
        return if (zDiff == 0) other.position.y.compareTo(position.y) else zDiff
    }

    companion object {
        val MAPPER = mapperFor<TransformComponent>()
    }
}

val Entity.transformComponent: TransformComponent
    get() = this[TransformComponent.MAPPER]
        ?: throw GdxRuntimeException("TransformComponent for entity [$this] is null!")