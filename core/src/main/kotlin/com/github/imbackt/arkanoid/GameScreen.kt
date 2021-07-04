package com.github.imbackt.arkanoid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen
import ktx.box2d.*
import ktx.graphics.use

class GameScreen : KtxScreen {
    private val background = Texture("graphics/background.jpg")
    private val paddle = Texture("graphics/paddle.png")
    private val ball = Texture("graphics/ball.png")
    private val batch = SpriteBatch()
    private val viewport = FitViewport(9f, 16f)
    private val world = createWorld(earthGravity, true)
    private val b2Ddr = Box2DDebugRenderer()
    private val tmpVec = Vector2()
    private var isGameStarted = false

    private val paddleB2D = world.body {
        type = BodyDef.BodyType.KinematicBody
        position.set(9f / 2f, 2f)
        gravityScale = 0f
        box(3f, 0.45f)
    }

    private val ballB2D = world.body {
        type = BodyDef.BodyType.DynamicBody
        position.set(9f / 2f, 2.55f)
        circle(0.30f) {
            restitution = 1f
        }
    }

    override fun show() {
        world.body {
            type = BodyDef.BodyType.StaticBody
            gravityScale = 0f
            chain(Vector2(0.58f, 0f), Vector2(0.58f, 14.2f), Vector2(8.42f, 14.2f), Vector2(8.42f, 0f))
        }
    }

    override fun render(delta: Float) {
        world.step(1 / 60f, 6, 2)
        viewport.apply(true)
        batch.use(viewport.camera.combined) {
            it.draw(background, 0f, 0f, 9f, 16f)
            it.draw(paddle, paddleB2D.position.x - 3f / 2f, paddleB2D.position.y - 0.45f / 2f, 3f, 0.45f)
            it.draw(ball, ballB2D.position.x - 0.33f, ballB2D.position.y - 0.66f, 0.85f, 1f)
        }
        b2Ddr.render(world, viewport.camera.combined)

        tmpVec.x = Gdx.input.x.toFloat()
        viewport.unproject(tmpVec)
        val clamp = MathUtils.clamp(tmpVec.x, 0f + 1.5f + 0.58f, 9f - 1.5f - 0.58f)
        paddleB2D.setTransform(clamp, paddleB2D.position.y, 0f)
        if (!isGameStarted)
            ballB2D.setTransform(paddleB2D.position.x, ballB2D.position.y, 0f)

        if (Gdx.input.isButtonPressed(0) and !isGameStarted) {
            isGameStarted = true
            ballB2D.applyLinearImpulse(Vector2(0f, 18f), ballB2D.worldCenter, true)
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}