package controller.audio;

import controller.audio.Audio;

public class AudioController {
    public static void addBulletShotSound() {
        try {
            new Audio("src/main/resources/shot.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addXPCollectingSound() {
        try {
            new Audio("src/main/resources/collectXP.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addWinningSound() {
        try {
            new Audio("src/main/resources/win.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addGameOverSound() {
        try {
            new Audio("src/main/resources/gameOver.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addEnemyDyingSound() {
        try {
            new Audio("src/main/resources/enemyDying.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addCollisionSound() {
        try {
            new Audio("src/main/resources/collision.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addWaveEndSound() {
        try {
            new Audio("src/main/resources/waveEnd.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addEnemyEnteringSound() {
        try {
            new Audio("src/main/resources/enemyEntering.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
