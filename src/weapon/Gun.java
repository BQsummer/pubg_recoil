package weapon;

/**
 * @description:
 * @author: huangxin
 * @create: 2020-07-08 18:26
 **/
public class Gun {

    private String name;

    private double interval;

    private Double[] recoil;

    public Gun() {}

    public Gun(String name) {
        this.name = name;
    }

    public Gun(String name, double interval) {
        this.name = name;
        this.interval = interval;
    }

    public Gun(String name, Double[] recoil) {
        this.name = name;
        this.recoil = recoil;
    }

    public Gun builder() {
        return new Gun();
    }

    public Gun name(String name) {
        this.name = name;
        return this;
    }

    public Gun interval(double interval) {
        this.interval = interval;
        return this;
    }

    public Gun recoil(Double[] recoil) {
        this.recoil = recoil;
        return this;
    }

    public Gun build() {
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public Double[] getRecoil() {
        return recoil;
    }

    public void setRecoil(Double[] recoil) {
        this.recoil = recoil;
    }
}
