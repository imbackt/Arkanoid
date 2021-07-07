package com.github.imbackt.arkanoid.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

class BrickComponent : Component, Pool.Poolable {
    var hitPoints = 1

    override fun reset() {
        hitPoints = 1
    }

    companion object {
        val MAPPER = mapperFor<BrickComponent>()
    }
}

val Entity.brickCmp: BrickComponent
    get() = this[BrickComponent.MAPPER]
        ?: throw GdxRuntimeException("BrickComponent for entity '$this' is null!")