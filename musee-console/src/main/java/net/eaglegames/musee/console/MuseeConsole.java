package net.eaglegames.musee.console;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import net.eaglegames.musee.entity.Musee;
import net.eaglegames.musee.game.Game;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MuseeConsole {
    private static Game game;
    private static Screen screen;

    public static void main(String[] args) throws IOException {
        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);

        TextGraphics tg = screen.newTextGraphics();

        screen.setCursorPosition(new TerminalPosition(5, 5));
        screen.startScreen();
        screen.clear();

        game = new Game();

        render(tg);
        screen.readInput();

        game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(2), game.getCurrentPlayer().getHand().get(0));

        render(tg);
        screen.readInput();

        game.playTurn(game.getCurrentPlayer().getMusee().getUpper().getSpaces().get(3), game.getCurrentPlayer().getHand().get(0));

        render(tg);
        screen.readInput();

        screen.stopScreen();
    }

    private static void render(TextGraphics tg) throws IOException {
        tg.putString(0, 0, "Current player: " + game.getCurrentPlayer().getId());
        tg.putString(0, 1, "Your score: " + game.getCurrentPlayer().getMusee().getScore() + ", opponent's score: " + game.getOpponent().getMusee().getScore());

        String hand = game.getCurrentPlayer().getHand()
                .stream()
                .map(p -> p.getTheme().name().substring(0, 2) + ":" + String.format("%1$2s", p.getValue()))
                .collect(Collectors.joining(","));
        tg.putString(0, 2, "Your hand: " + hand);

        tg.putString(0, 3, "Your Musée");
        tg.putString(0, 4, game.getCurrentPlayer().getMusee().toString());

        screen.refresh();
    }
}
