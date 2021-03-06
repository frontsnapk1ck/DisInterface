package frontsnapk1ck.disinterface.main;

public class Launcher {

    public static final String VERSION;

    private static DisInterface interf;

    static {
        VERSION = findVersion();
    }

    private static String findVersion() {
        return "0.3.1";
    }

    public static void main(String[] args) 
    {
        Launcher l = new Launcher();
        l.launchAll(args);
    }

    public Launcher() 
    {
        System.out.println("Launched DisInterface with version " + Launcher.VERSION);
    }

    private void launchAll(String[] args)
    {
        try
        {
            interf = new DisInterface();
            updateActivityRunnable();
        }
        catch (Exception ignored) {
        }
    }

    private static void updateActivityRunnable() 
    {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                interf.finishInit();
            }
        };

        Thread t = new Thread(r, "Update Delay");
        t.setDaemon(true);
        t.start();
    }

}
