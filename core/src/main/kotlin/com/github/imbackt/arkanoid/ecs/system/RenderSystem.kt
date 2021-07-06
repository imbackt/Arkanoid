package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.imbackt.arkanoid.ecs.component.RenderComponent
import com.github.imbackt.arkanoid.ecs.component.TransformComponent
import com.github.imbackt.arkanoid.ecs.component.renderComponent
import com.github.imbackt.arkanoid.ecs.component.transformComponent
import ktx.ashley.allOf
import ktx.graphics.use
import ktx.log.logger

class RenderSystem(
    private val batch: SpriteBatch,
    private val gameViewport: Viewport,
    private val uiViewport: Viewport
) : SortedIteratingSystem(allOf(TransformComponent::class, RenderComponent::class).get(),
    compareBy { entity -> entity.transformComponent }) {
    private val background = Sprite(Texture("graphics/background.png"))

    override fun update(deltaTime: Float) {
        uiViewport.apply(true)
        background.setBounds(0f, 0f, 9 * 64f, 16 * 64f)
        batch.use(uiViewport.camera.combined) { background.draw(batch) }
        forceSort()
        gameViewport.apply(true)
        batch.use(gameViewport.camera.combined) { super.update(deltaTime) }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transformComponent = entity.transformComponent
        val renderComponent = entity.renderComponent

        renderComponent.sprite.run {
            setBounds(
                transformComponent.position.x,
                transformComponent.position.y,
                transformComponent.size.x,
                transformComponent.size.y
            )
            draw(batch)
        }
    }

    companion object {
        private val LOG = logger<RenderSystem>()
    }
}