package com.github.imbackt.arkanoid.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.box2d.fixture

class Box2DComponent : Component, Pool.Poolable {
    lateinit var body: Body
    override fun reset() {
        body.world.destroyBody(body)
        body.userData = null
    }

    companion object {
        val MAPPER = mapperFor<Box2DComponent>()
    }
}

val Entity.b2DCmp: Box2DComponent
    get() = this[Box2DComponent.MAPPER]
        ?: throw GdxRuntimeException("Box2DComponent for entity '$this' is null!")