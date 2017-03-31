package utils;

import components.CheckerBoardPanel;
import sockets.multithreading.SocketProtocol;

import javax.swing.*;

public class GameVariables {

    // Game Options
    public static boolean blackLightModeEnabled;
    public static boolean forceJumpEnabled;
    public static boolean soundsEnabled;
    public static boolean timedTurns;

    public static JFrame gameFrame;
    public static CheckerBoardPanel checkerBoard;

    public static SocketProtocol socketProtocol;
    public static boolean networkGame;
}
