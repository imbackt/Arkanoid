package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.imbackt.arkanoid.ecs.component.*
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.graphics.use

class RenderSystem(
    private val batch: Batch,
    private val gameViewport: Viewport,
    private val uiViewport: Viewport
) :
    IteratingSystem(allOf(TransformComponent::class, RenderComponent::class).exclude(RemoveComponent::class).get()) {
    private val background = Sprite(Texture("graphics/background.png"))

    override fun update(deltaTime: Float) {
        uiViewport.apply()
        batch.use(uiViewport.camera.combined) {
            background.draw(it)
        }
        gameViewport.apply()
        batch.use(gameViewport.camera.combined) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transformComponent = entity.transformComponent
        val renderComponent = entity.renderComponent
        renderComponent.sprite.run {
            setSize(transformComponent.width, transformComponent.height)
            setPosition(transformComponent.position.x, transformComponent.position.y)
            draw(batch)
        }
    }
}