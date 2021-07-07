package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.Viewport

class Box2DRendererSystem(
    private val world: World,
    private val viewport: Viewport,
    private val box2DDebugRenderer: Box2DDebugRenderer
) : EntitySystem() {
    override fun update(deltaTime: Float) {
        box2DDebugRenderer.render(world, viewport.camera.combined)
        world.step(1/60f, 6, 2)
    }
}