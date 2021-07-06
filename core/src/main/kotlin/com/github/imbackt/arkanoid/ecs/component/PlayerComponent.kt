package com.github.imbackt.arkanoid.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor


class PlayerComponent : Component, Pool.Poolable {
    var lives = 3

    override fun reset() {
        lives = 3
    }

    companion object {
        val MAPPER = mapperFor<PlayerComponent>()
    }
}

val Entity.playerComponent: PlayerComponent
    get() = this[PlayerComponent.MAPPER]
        ?: throw GdxRuntimeException("PlayerComponent for entity[$this] is null!")