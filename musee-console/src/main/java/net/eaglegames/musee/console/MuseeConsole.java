package net.eaglegames.musee.console;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import net.eaglegames.musee.entity.Gallery;
import net.eaglegames.musee.entity.Musee;
import net.eaglegames.musee.entity.Painting;
import net.eaglegames.musee.entity.Space;
import net.eaglegames.musee.game.Game;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MuseeConsole {
    private static Game game;
    private static Screen screen;
    private static TextGraphics tg;

    public static void main(String[] args) throws IOException {
        // Setup terminal and screen layers
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(60, 40));
        Terminal terminal = defaultTerminalFactory.createTerminal();
        screen = new TerminalScreen(terminal);

        tg = screen.newTextGraphics();

        screen.setCursorPosition(new TerminalPosition(3, 5));
        screen.startScreen();
        screen.clear();

        game = new Game();
        Game.setupRandomStaircases(game.getPlayer1(), game.getPlayer2());

        while(!game.hasWinner()) {
            render();

            tg.fillRectangle(new TerminalPosition(0, 4), new TerminalSize(60, 1), ' ');
            tg.putString(0, 4, "Select a painting from your hand (1-5) or 'q' to quit");
            screen.refresh();
            KeyStroke keyStroke = screen.readInput();
            if (String.valueOf(keyStroke.getCharacter()).equals("q")) {
                break;
            }

            Integer selectedPainting = Integer.valueOf(String.valueOf(keyStroke.getCharacter())) - 1;
            Painting paintingToPlay = game.getCurrentPlayer().getHand().get(selectedPainting);
            tg.fillRectangle(new TerminalPosition(0, 4), new TerminalSize(60, 1), ' ');
            tg.putString(0, 4, "You've selected " + paintingToPlay + ", select a gallery (u,m,l)");
            screen.refresh();

            keyStroke = screen.readInput();

            Gallery.Type galleryType = Gallery.Type.UPPER;
            switch (String.valueOf(keyStroke.getCharacter()).toLowerCase()) {
                case "u":
                    galleryType = Gallery.Type.UPPER;
                    break;
                case "m":
                    galleryType = Gallery.Type.MIDDLE;
                    break;
                case "l":
                    galleryType = Gallery.Type.LOWER;
                    break;
            }

            tg.fillRectangle(new TerminalPosition(0, 4), new TerminalSize(60, 1), ' ');
            tg.putString(0, 4, "Place " + paintingToPlay + " in the " + galleryType.name() + " on which space (1-6)?");
            screen.refresh();

            keyStroke = screen.readInput();

            Integer selectedSpace = Integer.valueOf(String.valueOf(keyStroke.getCharacter())) - 1;

            game.playTurn(game.getCurrentPlayer().getMusee().getGallery(galleryType).getSpaces().get(selectedSpace), paintingToPlay);
        }

        if (game.hasWinner()) {
            screen.clear();
            tg.putString(10, 10, "Player " + game.getWinner().getId() + " has won!");
            tg.putString(10, 12, "Final Score: Player 1: " + game.getPlayer1().getMusee().getScore() + ", Player 2: " + game.getPlayer2().getMusee().getScore());
            screen.refresh();
            screen.readInput();
        }

        screen.stopScreen();
    }

    private static void render() throws IOException {
        screen.clear();
        tg.putString(0, 0, "Current player: " + game.getCurrentPlayer().getId());
        tg.putString(0, 1, "Your score: " + game.getCurrentPlayer().getMusee().getScore() + ", opponent's score: " + game.getOpponent().getMusee().getScore());
        tg.putString(0, 2, "Valid moves left: " + game.getCurrentPlayer().validMoves().size());

        String hand = game.getCurrentPlayer().getHand()
                .stream()
                .map(p -> "[" + p.getTheme().name().substring(0, 2) + ":" + String.format("%1$2s", p.getValue()) + "]")
                .collect(Collectors.joining(", "));
        tg.putString(0, 3, "Your hand: " + hand);

        tg.putString(0, 5, "> ");
        tg.putString(0, 6, "Your Musée");
        drawMusee(game.getCurrentPlayer().getMusee(), 7);

        tg.putString(0, 20, "Opponents Musée");
        drawMusee(game.getOpponent().getMusee(), 21);
    }

    private static void drawMusee(final Musee musee, final int pos) {
        tg.putString(0, pos, "_______________________________________________");
        drawGallery(musee.getUpper(), pos + 1);
        drawGallery(musee.getMiddle(), pos + 5);
        drawGallery(musee.getLower(), pos + 9);
        tg.putString(0, pos + 12, "_______________________________________________");
    }

    private static void drawGallery(final Gallery gallery, int pos) {
        tg.putString(0, pos, gallery.getSpaces().stream().map(Space::toString).collect(Collectors.joining(" ")));

        if (gallery.isBonus()) {
            tg.putString(48, pos, "GALLERY");
            tg.putString(48, pos + 1, " BONUS");
        }

        tg.putString(0, ++pos, "|     | |     | |     | |     | |     | |     |");
        tg.putString(0, ++pos, "|     | |     | |     | |     | |     | |     |");

        StringBuilder stairCases = new StringBuilder();
        gallery.getSpaces().stream().forEach(space -> {
            if (gallery.getType().equals(Gallery.Type.UPPER) || gallery.getType().equals(Gallery.Type.MIDDLE)) {
                if (space.isLowerStaircase())
                    stairCases.append(">     < ");
                else
                    stairCases.append("------- ");
            }
        });

        tg.putString(0, ++pos, stairCases.toString());
    }
}
