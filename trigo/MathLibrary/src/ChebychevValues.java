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

}
