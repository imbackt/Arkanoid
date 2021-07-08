package com.github.imbackt.arkanoid.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.github.imbackt.arkanoid.ecs.component.*
import ktx.ashley.get
import ktx.math.vec2
import kotlin.math.abs

class CollisionSystem : EntitySystem(), ContactListener {
    private val hitPoint = vec2()
    private var difference = 0f

    private fun Any.isPaddleEntity() = this is Entity && this[PaddleComponent.MAPPER] != null && !this.isRemoved
    private fun Any.isBallEntity() = this is Entity && this[BallComponent.MAPPER] != null && !this.isRemoved
    private fun Any.isBrickEntity() = this is Entity && this[BrickComponent.MAPPER] != null && !this.isRemoved

    override fun beginContact(contact: Contact) {
        hitPoint.set(contact.worldManifold.points[0])
    }

    override fun endContact(contact: Contact) {
        val userDataA = contact.fixtureA.userData
        val userDataB = contact.fixtureB.userData

        if (userDataA.isBallEntity() && userDataB.isPaddleEntity()) {
            directBall(userDataA as Entity, userDataB as Entity)

        } else if (userDataB.isBallEntity() && userDataA.isPaddleEntity()) {
            directBall(userDataB as Entity, userDataA as Entity)
        } else if (userDataA.isBallEntity() && userDataB.isBrickEntity()) {
            destroyBrick(userDataB as Entity)
        } else if (userDataB.isBallEntity() && userDataA.isBrickEntity()) {
            destroyBrick(userDataA as Entity)
        }
    }

    private fun directBall(ballEntity: Entity, paddleEntity: Entity) {
        val ballBody = ballEntity.box2DComponent.body
        val paddleBody = paddleEntity.box2DComponent.body
        ballBody.linearVelocity = Vector2.Zero
        difference = paddleBody.worldCenter.x - hitPoint.x
        if (hitPoint.x < paddleBody.worldCenter.x) {
            ballBody.applyForce(vec2(-abs(difference * 200f), 250f), ballBody.worldCenter, true)
        } else {
            ballBody.applyForce(vec2(abs(difference * 200f), 250f), ballBody.worldCenter, true)
        }
    }

    private fun destroyBrick(brickEntity: Entity) {
        val brickComponent = brickEntity.brickComponent
        val renderComponent = brickEntity.renderComponent
        if (brickComponent.hitPoints > 1) {
            brickComponent.hitPoints--
            renderComponent.sprite.setRegion(Texture("graphics/brick_${brickComponent.hitPoints}.png"))
            renderComponent.sprite.color = when (brickComponent.hitPoints) {
                1 -> Color.CYAN
                2 -> Color.CORAL
                3 -> Color.FIREBRICK
                else -> Color.WHITE
            }
        } else {
            brickEntity.add(RemoveComponent())
        }
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) = Unit

    override fun postSolve(contact: Contact, impulse: ContactImpulse) = Unit

}