package com.github.imbackt.arkanoid.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

enum class PowerUpType {
    NONE, LASER, ENLARGE, CATCH, SLOW, BREAK, DISRUPTION, PLAYER
}

class PowerUpComponent : Component, Pool.Poolable {
    var type = PowerUpType.NONE

    override fun reset() {
        type = PowerUpType.NONE
    }

    companion object {
        val MAPPER = mapperFor<PowerUpComponent>()
    }
}

val Entity.powerUpComponent: PowerUpComponent
    get() = this[PowerUpComponent.MAPPER]
        ?: throw GdxRuntimeException("PowerUpComponent for entity '$this' is null!")