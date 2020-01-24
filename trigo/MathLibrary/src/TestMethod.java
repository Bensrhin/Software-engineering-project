public class TestMethod {
    public static void main(String[] args) {
        methods m = new methods();
        int count = 0;
        for (int i = 0; i < Math.pow(2,32); i+=Math.pow(2,31)) {
            System.out.println("My ulp : " + m.ulp(i) + " ~~~~~ java ulp : " + Math.ulp(i));
            if (Math.abs(m.ulp(i) - Math.ulp(i)) > 0){
                count++;
            }
        }
        System.out.println(count);

    }
}
