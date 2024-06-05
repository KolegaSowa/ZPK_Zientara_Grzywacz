
import java.io.Serializable;

public class PointsCounter implements Serializable {

    private static final long serialVersionUID = 1L;

    private float points;
    private float minPoints;
    private float maxPoints = 100f;
    private float progress;
    private float defaultNumberToAddPoints = 1f;

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = Math.max(minPoints, Math.min(points, maxPoints));
        updateProgress();
    }

    public float getMinPoints() {
        return minPoints;
    }

    public void setMinPoints(float minPoints) {
        this.minPoints = minPoints;
        if (points < minPoints) {
            setPoints(minPoints);
        }
    }

    public float getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(float maxPoints) {
        this.maxPoints = maxPoints;
        if (points > maxPoints) {
            setPoints(maxPoints);
        }
    }

    public float getProgress() {
        return progress;
    }

    private void setProgress(float progress) {
        this.progress = progress;
    }

    public float getDefaultNumberToAddPoints() {
        return defaultNumberToAddPoints;
    }

    public void setDefaultNumberToAddPoints(float defaultNumberToAddPoints) {
        this.defaultNumberToAddPoints = defaultNumberToAddPoints;
    }

    public void addPoints() {
        setPoints(points + defaultNumberToAddPoints);
    }

    public void updateProgress() {
        if (maxPoints != 0) {
            setProgress(points / maxPoints);
        } else {
            setProgress(0);
        }
    }

    public void addCustomPoints(float customPoints) {
        setPoints(points + customPoints);
    }

    public void resetPoints() {
        setPoints(minPoints);
    }

    public void resetProgress() {
        setProgress(0);
    }
}