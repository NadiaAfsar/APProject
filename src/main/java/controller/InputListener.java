package controller;

import model.EpsilonModel;
import model.GameManager;
import model.skills.Skill;
import view.GameView;
import view.Shop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InputListener {
    private static InputMap inputMap;
    private ActionMap actionMap;
    private EpsilonModel epsilon;

    public InputListener(EpsilonModel epsilon) {
        this.epsilon = epsilon;
        createKeyBindings();
        createKeyActions();
    }
    private void createKeyBindings() {

        inputMap = GameView.getINSTANCE().getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMap = GameView.getINSTANCE().getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "moveUpReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "moveDownReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "moveLeftReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "moveRightReleased");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "activateSkill");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "showShop");

    }

    private void createKeyActions() {
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.gameRunning) {
                    epsilon.moveUp(true);
                }
            }
        });
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.gameRunning) {
                    epsilon.moveDown(true);
                }
            }
        });
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.gameRunning) {
                    epsilon.moveRight(true);
                }
            }
        });
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.gameRunning) {
                    epsilon.moveLeft(true);
                }
            }
        });
        actionMap.put("moveUpReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.gameRunning) {
                    epsilon.moveUp(false);
                }
            }
        });
        actionMap.put("moveDownReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.gameRunning) {
                    epsilon.moveDown(false);
                }
            }
        });
        actionMap.put("moveRightReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.gameRunning) {
                    epsilon.moveRight(false);
                }
            }
        });
        actionMap.put("moveLeftReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.gameRunning) {
                    epsilon.moveLeft(false);
                }
            }
        });
        actionMap.put("activateSkill", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Skill skill = GameManager.getINSTANCE().getPickedSkill();
                if (skill != null) {
                    skill.activate();
                }
            }
        });
        actionMap.put("showShop", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.gameRunning) {
                    Controller.gameRunning = false;
                    new Shop();
                }
            }
        });
    }
    public void stop() {
        inputMap = null;
        actionMap = null;
    }


}