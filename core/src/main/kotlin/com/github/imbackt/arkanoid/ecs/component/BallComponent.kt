package com.github.imbackt.arkanoid.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

class BallComponent : Component, Pool.Poolable {
    var isInitialBall = false
    var speed = 0f

    override fun reset() {
        isInitialBall = false
        speed = 0f
    }

    companion object {
        val MAPPER = mapperFor<BallComponent>()
    }
}

val Entity.ballCmp: BallComponent
    get() = this[BallComponent.MAPPER]
        ?: throw GdxRuntimeException("BallComponent of entity '$this' is null!")