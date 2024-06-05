public class Main {
    public static void main(String[] args) {
        PointsCounter pc = new PointsCounter();
        pc.setMinPoints(0);
        pc.setMaxPoints(100);
        pc.addPoints();
        System.out.println("Points: " + pc.getPoints()); // Points: 1.0
        pc.updateProgress();
        System.out.println("Progress: " + pc.getProgress()); // Progress: 0.01

        pc.addCustomPoints(50);
        System.out.println("Points: " + pc.getPoints()); // Points: 51.0
        pc.updateProgress();
        System.out.println("Progress: " + pc.getProgress()); // Progress: 0.51

        pc.addCustomPoints(100);
        System.out.println("Points: " + pc.getPoints()); // Points: 100.0
        pc.updateProgress();
        System.out.println("Progress: " + pc.getProgress()); // Progress: 1.0

        pc.resetPoints();
        System.out.println("Points after reset: " + pc.getPoints()); // Points after reset: 0.0
        pc.updateProgress();
        System.out.println("Progress after reset: " + pc.getProgress()); // Progress after reset: 0.0
    }
}