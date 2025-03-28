package dev.rubr1c;

import dev.rubr1c.core.EngineManager;
import dev.rubr1c.core.WindowManager;
import dev.rubr1c.core.utils.Constants;

public class Launcher {

    private static WindowManager window;
    private static EngineManager engine;

    public static void main(String[] args) {
        window = new WindowManager(Constants.TITLE, 0, 0, false);
        engine = new EngineManager();

        try {
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }
}
