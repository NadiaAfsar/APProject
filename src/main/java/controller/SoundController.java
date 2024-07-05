package controller;

public class SoundController {
    public static void addBulletShotSound() {
        try {
            new Sound("src/main/resources/shot.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addXPCollectingSound() {
        try {
            new Sound("src/main/resources/collectXP.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addWinningSound() {
        try {
            new Sound("src/main/resources/win.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addGameOverSound() {
        try {
            new Sound("src/main/resources/gameOver.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addEnemyDyingSound() {
        try {
            new Sound("src/main/resources/enemyDying.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addCollisionSound() {
        try {
            new Sound("src/main/resources/collision.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addWaveEndSound() {
        try {
            new Sound("src/main/resources/waveEnd.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addEnemyEnteringSound() {
        try {
            new Sound("src/main/resources/enemyEntering.wav");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
