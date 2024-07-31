package controller.audio;

import application.MyApplication;

public class AudioController {
    public static void addBulletShotSound() {
        try {
            new Audio(MyApplication.configs.SHOT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addXPCollectingSound() {
        try {
            new Audio(MyApplication.configs.COLLECTING_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addWinningSound() {
        try {
            new Audio(MyApplication.configs.WINNING_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addGameOverSound() {
        try {
            new Audio(MyApplication.configs.GAME_OVER_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addEnemyDyingSound() {
        try {
            new Audio(MyApplication.configs.ENEMY_DYING_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addCollisionSound() {
        try {
            new Audio(MyApplication.configs.COLLISION_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addWaveEndSound() {
        try {
            new Audio(MyApplication.configs.WAVE_END);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void addEnemyEnteringSound() {
        try {
            new Audio(MyApplication.configs.ENEMY_ENTERING_SOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
