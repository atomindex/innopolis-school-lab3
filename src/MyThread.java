/**
 * Created by Atom on 8/7/2015.
 */
public class MyThread implements Runnable {

    private Scene scene;
    private boolean terminated = true;
    private boolean stopped = true;



    public MyThread(Scene scene) {
        this.scene = scene;
    }



    public void run() {
        terminated = false;
        stopped = false;

        while (true) {
            if (terminated) {
                stopped = true;
                return;
            }
            try {
                Thread.sleep(10);
                scene.update();
                scene.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    public void terminate() {
        terminated = true;
    }

}