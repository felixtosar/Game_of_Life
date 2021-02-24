package life;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Controller implements ActionListener, ItemListener {
    private final long TIME_DEFAULT = 140;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    private Universe currentGeneration; // the model
    private final GameOfLife gameOfLife; // the view
    private int generationCount = 1;
    public final int SIZE_OF_UNIVERSE = 20;


    public Controller(Universe currentGeneration, GameOfLife gameOfLife) {
        this.currentGeneration = currentGeneration;
        this.gameOfLife = gameOfLife;
    }

    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (!running) { // may have changed while waiting to
                    // synchronize on pauseLock
                    break;
                }
                if (paused) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait(); // will cause this Thread to block until
                            // another thread calls pauseLock.notifyAll()
                            // Note that calling wait() will
                            // relinquish the synchronized lock that this
                            // thread holds on pauseLock so another thread
                            // can acquire the lock to call notifyAll()
                            // (link with explanation below this code)
                        }
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) { // running might have changed since we paused
                        break;
                    }
                }
            }
            System.out.println(generationCount);
            currentGeneration.generateNextGeneration();
            gameOfLife.setGenerationLabel(generationCount++);
            gameOfLife.setAliveLabel(currentGeneration.getCellsAlive());
            gameOfLife.updateStatus(currentGeneration.getGenerationStatus());

            try {
                Thread.sleep(TIME_DEFAULT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
        // you might also want to interrupt() the Thread that is
        // running this Runnable, too, or perhaps call:
        resume();
        // to unblock
    }

    public void pause() {
        // you may want to throw an IllegalStateException if !running
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll(); // Unblocks thread
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("RESET_BUTTON")) {
            currentGeneration = new Universe(SIZE_OF_UNIVERSE);
            generationCount = 1;

        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
            resume();
        } else {
            pause();
        }
    }
}