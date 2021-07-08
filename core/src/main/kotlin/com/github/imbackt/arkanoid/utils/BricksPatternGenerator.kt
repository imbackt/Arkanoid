package com.github.imbackt.arkanoid.utils

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.github.imbackt.arkanoid.ecs.component.*
import ktx.ashley.entity
import ktx.ashley.with
import ktx.box2d.body
import ktx.box2d.box

private const val offsetX = 1.5f
private const val offsetY = 3.5f

fun generatePattern(width: Int, height: Int, engine: Engine, world: World) {

    val pattern = Array(height) { Array(width) { 0 } }
    var r: Int

    for (i in pattern.indices) {
        for (j in pattern[i].indices) {
            r = (0..3).random()
            if (r > 0) pattern[i][j] = r
        }
    }

    generatePattern(pattern, engine, world)

}

fun generatePattern(pattern: Array<Array<Int>>, engine: Engine, world: World) {
    for (i in pattern.indices) {
        for (j in pattern[i].indices) {
            if (pattern[i][j] > 0) {
                engine.entity {
                    with<BrickComponent> {
                        hitPoints = pattern[i][j]
                    }
                    with<PowerUpComponent>()
                    val transform = with<TransformComponent> {
                        size.set(0.5f, 0.5f)
                    }
                    with<Box2DComponent> {
                        body = world.body {
                            type = BodyDef.BodyType.StaticBody
                            position.set(offsetX + 1f * j, 16f - offsetY - 0.75f * i)
                            box(transform.width, transform.height) {
                                friction = 0f
                                userData = this@entity.entity
                            }
                        }
                    }
                    with<RenderComponent> {
                        sprite.setRegion(Texture("graphics/brick_${pattern[i][j]}.png"))
                        sprite.color = when {
                            pattern[i][j] == 1 -> Color.CYAN
                            pattern[i][j] == 2 -> Color.CORAL
                            pattern[i][j] == 3 -> Color.FIREBRICK
                            else -> Color.WHITE
                        }
                    }
                }
            }
        }
    }
}
