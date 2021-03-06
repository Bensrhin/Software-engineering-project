public class ChebychevValues {

    public float ChebyValuesCos(int j, int k){
        if (j == 1){
            return 0.8775825618903728f;
        }
        else if (j == 2) {
            return 0.9689124217106447f;
        }
        else if (j == 3) {
            return 0.992197667229329f;
        }
        else {
            return 0.9980475107000991f;
        }
    }

    public float ChebyValuesSin(int j, int k){
        if (j == 1){
            return 0.479425538604203f;
        }
        else if (j == 2) {
            return 0.24740395925452294f;
        }
        else if (j == 3) {
            return 0.12467473338522769f;
        }
        else {
            return 0.0624593178423802f;
        }
    }

    public double ChebyValuesActan(int i){
        if (i == 0) {
            return -0.09849139859277231f;
        }
        else if (i == 1) {
            return -2.909727065921128f;
        }
        else if (i == 2) {
            return 0.9508941577113765f;
        }
        else if (i == 3) {
            return -0.4009904007516215f;
        }
        else if (i == 4) {
            return -20.88540204971548f;
        }
        else if (i == 5) {
            return 0.5168632143106962f;
        }
        else if (i == 6) {
            return -0.7834144432251993f;
        }
        else if (i == 7) {
            return 4.170396957285327f;
        }
        else if (i == 8) {
            return 0.1963273487588419f;
        }
        else {
            return 0.0f;
        }

    }

    public float otherActanVal(int i){
        if (i == 0) {
            return 0.0f;
        }
        else if (i == 1) {
            return 0.19891237f;
        }
        else if (i == 2) {
            return 0.41421357f;
        }
        else if (i == 3) {
            return 0.6681786f;
        }
        else if (i == 4) {
            return 1.0f;
        }
        else if (i == 5) {
            return 1.4966058f;
        }
        else if (i == 6) {
            return 2.4142137f;
        }
        else if (i == 7) {
            return 5.0273395f;
        }
        else if (i == 8) {
            return 1.63312395E16f;
        }
        else {
            return 0.0f;
        }
    }


}
