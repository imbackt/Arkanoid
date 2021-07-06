package com.github.imbackt.arkanoid.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

class RenderComponent : Component, Pool.Poolable {
    val sprite = Sprite()

    override fun reset() {
        sprite.texture = null
        sprite.color = Color.WHITE
    }

    companion object {
        val MAPPER = mapperFor<RenderComponent>()
    }
}

val Entity.renderComponent: RenderComponent
    get() = this[RenderComponent.MAPPER]
        ?: throw GdxRuntimeException("RenderComponent for entity [$this] is null!")