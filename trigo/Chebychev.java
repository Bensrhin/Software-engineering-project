public class Chebychev {
    /*static float ac0 = -5.389860130521907e-19f;
    static float ac1 = 0.25000000000000017f;
    static float ac2 = 1.4409711500821315e-13f;
    static float ac3 = -0.33333333332069687f;
    static float ac4 = -5.939540286359179e-10f;
    static float ac5 = 0.20000001725419048f;
    static float ac6 = -3.3761213235741494e-07f;
    static float ac7 = -0.14285243230971004f;
    static float ac8 =  -4.8772024066364285e-05f;
    static float ac9 =  0.11149678470762456f;
    static float ac10 =  -0.00237878405098088f;
    static float ac11 =  -0.07928986248045526f;
    static float ac12 =  -0.04541742622371549f;
    static float ac13 =  0.2198713666483299f;
    static float ac14 =  -0.36298162350125435f;
    static float ac15 =  0.6747547254333055f;
    static float ac16 =  -1.2068834759572822f;
    static float ac17 =  1.5947995376899502f;
    static float ac18 =  -1.4733355728696031f;
    static float ac19 =  0.9352877337928825f;
    static float ac20 =  -0.38134088133929556f;
    static float ac21 =  0.07262057480329323f;
    static float ac22 =  0.014929873764628284f;
    static float ac23 =  -0.013785080199873786f;
    static float ac24 =  0.0036586674916979805f;
    static float ac25 =  -0.00037353570529063436f;

    static float arctan(float x){
        return ac0 + x * ac1 + x * (ac2 + x * (ac3 + x * (ac4 + x * (ac5 + x * (ac6 + x * (ac7 + x * (ac8 + x * (ac9 + x * (ac10 + x * (ac11 +
                x * (ac12 + x * (ac13 + x * (ac14 + x * (ac15 + x * (ac16 + x * (ac17 + x * (ac18 + x * (ac19 +
                        x * (ac20 + x * (ac21 + x * (ac22 + x * (ac23 + x * (ac24 + x * (ac25))))))))))))))))))))))));
    }*/

    /*static float c1 = 0.9999999999999999999999914771f;
    static float c2 = -0.4999999999999999999991637437f;
    static float c3 = 0.04166666666666666665319411988f;
    static float c4 = -0.00138888888888888880310186415f;
    static float c5 = 0.00002480158730158702330045157f;
    static float c6 = -0.000000275573192239332256421489f;
    static float c7 = 0.000000002087675698165412591559f;
    static float c8 = -0.0000000000114707451267755432394f;
    static float c9 = 0.0000000000000477945439406649917f;
    static float c10 = -0.00000000000000015612263428827781f;
    static float c11 = 0.00000000000000000039912654507924f;
    static float a1 = -0.16666666666666666f;
    static float a2 = 0.008333333333284603f;
    static float a3 = -0.0001984126207673734f;
    static float a4 = 2.7171469680259784e-06f;*/

    static float breakPoint(int j, int k){
        return (float)Math.pow(2, -j)*(1 + k/8);
    }
    /*static float sinus(float r){
        return (float)(r + a1*Math.pow(r,3)+a2*Math.pow(r,5)+a3*Math.pow(r,7)+a4*Math.pow(r,9));
    }*/
    static float approximation1(float r ){
        assert(r >= -1/32 && r <= 1/32 );

        float a1 = (float)-0.16666666666666666666421;
        float a2 = (float) 0.008333333333333312907;
        float a3 = (float) -0.0001984126983563939;
        float a4 = (float) 0.00000275566861;
        return (float) (a1*Math.pow(r, 3) + a2*Math.pow(r, 5) + a3*Math.pow(r, 7)+ a4*Math.pow(r, 9));
    }

    static float approximation2(float r ){
        assert(r >= (float) -1/32 && r <= (float)1/32 );
        float a1 = (float)-0.49999999999999999999999995425696;
        float a2 = (float) 0.0416666666666666666660027496;
        float a3 = (float) -0.001388888888885759632;
        float a4 = (float) 0.0000248015872951505636;
        float a5 = (float) -0.000000275567182072;
        return (float) (a1*Math.pow(r, 2) + a2*Math.pow(r, 4) + a3*Math.pow(r, 6) + a4*Math.pow(r, 8) + a5*Math.pow(r, 10));
    }

    static float sin(float value){
        if(Math.abs(value) <= 0.03125f){
            return approximation1(value)+value;
        }
        float breakP = breakPoint(1, 0);
        int j = 1;
        int k = 0;
        // we choose the minimum for j & k
        while (j <= 4){
            while (k <= 7){
                if(Math.abs(breakP - value) > Math.abs(breakPoint(j, k)-value)){
                    breakP = breakPoint(j, k);

                }
                k = k + 1;
            }
            k = 0;
            j = j + 1;
        }
        float r = value - breakP;

        float a = approximation1(r);
        float b = approximation2(r);

        return (float)Math.sin(breakP)*(b+1) + (float)Math.cos(breakP)*(a+r);

    }
    static float rangeReduction(float x){
        float C1 = 3.140625f;
        float C2 = 0.000967653589793f;
        int k = (int) (x /Math.PI);
        float result = x - k * C1;
        result = result - k * C2;
        return result;
    }

    public static void main(String[] args) {
        /*for (float i = 0; i < Math.PI/1234568765; i+=0.000000000000001) {
            float mine = sin(i);
            float real = (float) Math.sin(i);
            float err = Math.abs(real - mine)/Math.ulp(real);
            System.out.println(" my sinus is : " + mine + " their sinus is : " + real);
            if (err > 1){
                System.out.println(" the error is : " + err);
            }

        }*/
        float target = (float) Math.PI;
        float x = (float)(10 * target);
        System.out.println(rangeReduction(x) + " " + target+" "+);

    }

}
