package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.physics.box2d.World

class BricksSpawnSystem(
    private val world: World
) : EntitySystem() {

    override fun update(deltaTime: Float) {

    }
}