package com.github.imbackt.arkanoid.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor
import ktx.math.vec2

class BallComponent : Component, Pool.Poolable {
    var initialBall = true
    val speed = vec2(1f, 1f)

    override fun reset() {
        initialBall = true
        speed.set(1f, 1f)
    }

    companion object {
        val MAPPER = mapperFor<BallComponent>()
    }
}

val Entity.ballComponent: BallComponent
    get() = this[BallComponent.MAPPER]
        ?: throw GdxRuntimeException("BallComponent for entity '$this' is null!")