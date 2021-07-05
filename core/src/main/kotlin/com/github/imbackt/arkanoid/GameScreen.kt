package com.github.imbackt.arkanoid

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen
import ktx.box2d.*
import ktx.graphics.use
import kotlin.math.abs

class GameScreen : KtxScreen, ContactListener {
    private val background = Texture("graphics/background.jpg")
    private val paddle = Texture("graphics/paddle.png")
    private val ball = Texture("graphics/ball.png")
    private val batch = SpriteBatch()
    private val viewport = FitViewport(9f, 16f)
    private val world = createWorld(Vector2(0f, -0.5f), true)
    private val b2Ddr = Box2DDebugRenderer()
    private val tmpVec = Vector2()
    private var isGameStarted = false

    private val paddleB2D = world.body {
        type = BodyDef.BodyType.KinematicBody
        position.set(9f / 2f, 2f)
        userData = "PADDLE"
        box(3f, 0.45f) {
            friction = 0f
            userData = "PADDLE"
        }
    }

    private val ballB2D = world.body {
        type = BodyDef.BodyType.KinematicBody
        position.set(9f / 2f, 2.4f)
        userData = "BALL"
        circle(0.15f) {
            restitution = 1f
            friction = 0f
            userData = "BALL"
        }
    }

    override fun show() {
        world.body {
            type = BodyDef.BodyType.StaticBody
            userData = "WALL"
            chain(Vector2(0.58f, 0f), Vector2(0.58f, 14.2f), Vector2(8.42f, 14.2f), Vector2(8.42f, 0f)) {
                friction = 0f
                userData = "WALL"
            }
        }
        world.setContactListener(this)
    }

    override fun render(delta: Float) {
        world.step(1 / 60f, 6, 2)
        viewport.apply(true)
        batch.use(viewport.camera.combined) {
            it.draw(background, 0f, 0f, 9f, 16f)
            it.draw(paddle, paddleB2D.position.x - 3f / 2f, paddleB2D.position.y - 0.45f / 2f, 3f + 0.06f, 0.45f)
            it.draw(ball, ballB2D.position.x - 0.16f, ballB2D.position.y - 0.33f, 0.425f, 0.5f)
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
            ballB2D.type = BodyDef.BodyType.DynamicBody
            ballB2D.applyForceToCenter(Vector2(0f, 300f), true)
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    private var hitPoint = Vector2()

    override fun beginContact(contact: Contact) {
        var paddleBody: Body? = null
        if (contact.fixtureA.userData == paddleB2D.userData) {
            paddleBody = contact.fixtureA.body
        } else if (contact.fixtureB.userData == paddleB2D.userData) {
            paddleBody = contact.fixtureB.body
        }

        if (paddleBody != null) {
            hitPoint = contact.worldManifold.points[0]
        }
    }

    override fun endContact(contact: Contact) {
        val ballBody: Body
        var paddleBody: Body? = null
        if (contact.fixtureA.userData == ballB2D.userData) {
            ballBody = contact.fixtureA.body
            if (contact.fixtureB.userData == paddleB2D.userData) {
                paddleBody = contact.fixtureB.body
            }
        } else {
            ballBody = contact.fixtureB.body
            if (contact.fixtureA.userData == paddleB2D.userData) {
                paddleBody = contact.fixtureA.body
            }
        }

        if (paddleBody != null) {
            ballBody.linearVelocity = Vector2.Zero
            val difference = paddleBody.worldCenter.x - hitPoint.x
            if (hitPoint.x < paddleBody.worldCenter.x) {
                ballB2D.applyForceToCenter(Vector2(-abs(difference * 200f), 300f), true)
            } else {
                ballB2D.applyForceToCenter(Vector2(abs(difference * 200f), 300f), true)
            }
        }
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {

    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

    }
}