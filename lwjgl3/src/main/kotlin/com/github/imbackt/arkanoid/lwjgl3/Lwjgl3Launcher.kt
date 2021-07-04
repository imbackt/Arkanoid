package com.github.imbackt.arkanoid.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.github.imbackt.arkanoid.Arkanoid

fun main() {
    Lwjgl3Application(Arkanoid(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("Arkanoid")
        setWindowedMode(9 * 60, 16 * 60)
        setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
    })
}