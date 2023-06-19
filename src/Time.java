public class Time extends Object {
    int seconds;
    int minutes;

    public Time(int minutes, int seconds) {
        this.seconds = seconds;
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        if(seconds<10)
            return minutes+":0"+seconds;
        if (seconds==60)
            return (minutes+1)+":00";
        if(seconds==0)
            return minutes+":00";
        return minutes+":"+seconds;
    }
    public void setTime(int seconds) {
        this.seconds = seconds % 60;
        this.minutes = seconds / 60;
    }

    public int getSeconds() {
        return seconds;
    }
    public int getMinutes() {
        return minutes;
    }
}
