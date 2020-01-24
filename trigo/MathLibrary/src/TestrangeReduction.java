public class TestrangeReduction {
    public static void main(String[] args) {
        rangeReduction reduction = new rangeReduction();
        for (int i = 0; i < 3000; i++) {
            float n = (float) Math.pow(2, i);
            float first = reduction.FirstReductionSin(i * (float)Math.PI);
            float second = reduction.SecondReduction(i * (float)Math.PI);
            System.out.println("My reduction : " + first + " ~~~ Ali reduction : " + second);
            //System.out.println(Math.cos(first) + " " + Math.cos(second) + " " + Math.cos(2* Pi));
            //System.out.println(Math.abs(Math.cos(first) - Math.cos(2 *Pi))/Math.ulp(Math.cos(2 * Pi)));
        }

    }
}
