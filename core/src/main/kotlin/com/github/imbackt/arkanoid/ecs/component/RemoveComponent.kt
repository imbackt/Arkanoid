package com.github.imbackt.arkanoid.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

class RemoveComponent : Component, Pool.Poolable {
    var delay = 0f

    override fun reset() {
        delay = 0f
    }

    companion object {
        val MAPPER = mapperFor<RemoveComponent>()
    }
}

val Entity.removeComponent: RemoveComponent
    get() = this[RemoveComponent.MAPPER]
        ?: throw GdxRuntimeException("Remove Component for entity '$this' is null")

val Entity.isRemoved: Boolean
    get() {
        return this.isRemoving || this[RemoveComponent.MAPPER] != null
    }