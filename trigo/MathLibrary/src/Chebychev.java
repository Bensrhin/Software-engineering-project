public class Chebychev {
    static ChebychevValues values = new ChebychevValues();

    static class couple {
        int x;
        int y;
    }

    public static final float Pi = 3.1415926f;

    static methods m = new methods();
    static rangeReduction range = new rangeReduction();

    static float breakPoint(int j, int k){
        return m.power(2, -j)*(1 + k/8);
    }

    static couple BPOptVal(float value){
        couple c = new couple();
        float BP = breakPoint(1, 0);
        int temoin = 0;
        int j = 1;
        int k = 0;
        while (j < 5){
            while (k < 8){
                if(m.abs(BP - value) > m.abs(breakPoint(j, k) - value)){
                    temoin = 1;
                    BP = breakPoint(j, k);
                    c.x = j;
                    c.y = k;
                }
                k += 1;
            }
            k = 0;
            j += 1;
        }
        if (temoin == 0){
            c.x = 1;
            c.y = 0;
        }

        return c;
    }

    static float ChebychevActan(float x){

        float c1 = 137.772398556992967072803545f;
        float c2 = 311.6092089800060900355248812f;
        float c3 = 243.2657933898208264206733648f;
        float c4 = 76.1179448121611233450964866f;
        float c5 = 8.1327059582002624490996398f;
        float c6 = 0.1342077928230059190697038f;
        float q1 = 137.7723985569929670728035493f;
        float q2 = 357.5333418323370790597777492f;
        float q3 = 334.8890942892012593679882767f;
        float q4 = 135.9227450335766811043244001f;
        float q5 = 22.23061618444266557469256796f;

        return (c1 * x + c2 * m.power(x, 3) + c3 * m.power(x, 5) + c4 * m.power(x, 7) + c5 * m.power(x, 9) + c6 * m.power(x, 11))
                /(q1 + q2 * m.power(x, 2) + q3 * m.power(x, 4) + q4 * m.power(x, 6) + q5 * m.power(x, 8));
    }

    static float ChebychevSinus(float r ){

        float a1 = -0.16666666666666666666421f;
        float a2 = 0.008333333333333312907f;
        float a3 = -0.0001984126983563939f;
        float a4 = 0.00000275566861f;

        return r + a1*m.power(r, 3) + a2*m.power(r, 5) + a3*m.power(r, 7)+ a4*m.power(r, 9);
    }

    static float ChebychevCosinus(float r ){

        float a1 = -0.49999999999999999999999995425696f;
        float a2 = 0.0416666666666666666660027496f;
        float a3 = -0.001388888888885759632f;
        float a4 = 0.0000248015872951505636f;
        float a5 = -0.000000275567182072f;

        return 1 + a1*m.power(r, 2) + a2*m.power(r, 4) + a3*m.power(r, 6) + a4*m.power(r, 8) + a5*m.power(r, 10);
    }

    static float arctan(float value){
        float tanCoef = 0.09849140335716425f;
        float Pi32 = 0.098174766f;

        if (value < 0){
            return -arctan(-value);
        }

        else if (value >= 0 && value <= tanCoef){
            return ChebychevActan(value);
        }

        else {
            int i = 1;
            while (i < 9) {
                if (value <= values.ChebyValuesActan(i) && value >= values.ChebyValuesActan(i - 1)) {
                    break;
                }
                i = i + 1;
            }

            float x = values.ChebyValuesActan(i - 1);
            float t = 1/x - (m.power(x, -2) + 1)/(m.power(x, -1)+value);

            return (2*i - 2)*Pi32 + arctan(t);
        }
    }

    static float sin(float value){
        if (value <= Pi/4 && value >= 0){

            if(m.abs(value) <= 0.03125f){
                return ChebychevSinus(value);
            }
            couple c = BPOptVal(value);
            float BP = breakPoint(c.x, c.y);
            float r = value - BP;
            System.out.println(" the coefficient is : " + values.ChebyValuesSin(c.x, c.y));
            return (float) (values.ChebyValuesSin(c.x, c.y)*ChebychevCosinus(r) + values.ChebyValuesCos(c.x,c.y)*ChebychevSinus(r));
        }

        else if (value < 0){
            return -sin(-value);
        }

        else {
            if (value > Pi){
                float newValue = range.FirstReductionSin(value);
                return sin(newValue);
            }

            else if (value > Pi/2){
                return cos(Pi/2 - value);
            }

            else {
                return 2 * sin(value/2) * cos(value/2);
            }
        }

    }

    static float cos(float value){

        if (value <= Pi/4 && value >= 0){
            if(m.abs(value) <= 0.03125f){
                return ChebychevCosinus(value);
            }

            couple c = BPOptVal(value);
            float BP = breakPoint(c.x, c.y);
            float r = value - BP;
            return (float) (values.ChebyValuesCos(c.x, c.y)*ChebychevCosinus(r) - values.ChebyValuesSin(c.x, c.y)*ChebychevSinus(r));
        }

        else if (value < 0){
            return cos(-value);
        }

        else {
            if (value > 2* Pi){
                float newValue = range.FirstReductionCos(value);
                return cos(newValue);
            }
            else if (value > Pi && value < 2*Pi){
                return -cos(value - Pi);
            }

            else if (value > Pi/2){
                return sin(Pi/2 - value);
            }

            else {
                return 1 - 2 * m.power(sin(value/2), 2);
            }

        }

    }

    public static void main(String[] args) {
        int count = 0;
        /*for (float i = 0; i < 127; i++) {
            float j = (float) m.power(2, -i);
            float actan = arctan(j);
            float realactan = (float)Math.atan(j);
            float err = Math.abs(actan - realactan)/Math.ulp(realactan);
            System.out.println(" my arctan is : " + actan + " ~~~~ Java arctan is : " + realactan);
            if (err > 1){
                count ++;
            }
            System.out.println(err);
        }
        */
        for (float i = (Pi/64); i < 2* Pi; i+=0.01) {
            float mine = sin(i);
            float real = (float) Math.sin(i);
            float err = Math.abs(real - mine)/Math.ulp(real);
            System.out.println(" my sinus is : " + mine + " their sinus is : " + real);
            if (err > 5){
                count++;
                System.out.println(" the error is : " + err);
            }

        }
        System.out.println(count);

        /*for (float i = 0; i < 10 * Pi / 25; i+=0.01) {
            float mine = cos(i);
            float real = (float) Math.cos(i);
            float err = Math.abs(real - mine)/Math.ulp(real);
            System.out.println(" my cosinus is : " + mine + " their cosinus is : " + real);
            if (err > 5){
                count++;
                System.out.println(" the error is : " + err);
            }

        }
        System.out.println(count);*/
        /*float target = 3.140625f;
        float x = (float)(10 * target);
        System.out.println(rangeReduction(x) + " " + target);*/

    }

}
