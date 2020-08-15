package cookerMavenPlugin.fileGenFactory;

public class RandomNumberGenerator {

    /**
     * This method is used to get the random number between AA to ZZ &amp; 00001 to 99999 (XX88888)
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     *
     * @return : formatted random number
     */
    public static String getRandomString() {
        final int minNum = 1;
        final int maxNum = 99999;

        //Get Random Number Between 00001 to 99999
        int random = getRandomNumber(minNum, maxNum);
        String formattedRandomNum = String.format("%05d", random);

        //Get Two numbers between 0 to 25 to convert each number
        final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int minNumAlpha = 1;
        int maxNumAlpha = 26;
        int alpharandom1 = getRandomNumber(minNumAlpha, maxNumAlpha);
        int alpharandom2 = getRandomNumber(minNumAlpha, maxNumAlpha);

        //Create & Return a RandomString in format -> XX88888
        return ("" +
                ALPHA.charAt(alpharandom1 - 1) +
                ALPHA.charAt(alpharandom2 - 1) +
                formattedRandomNum);
    }

    /**
     * Method returns Random Number between Minimum Number &amp; Maximum Number
     *
     * @param minNum minimum Number
     * @param maxNum maximum Number
     * @return randomNumber
     */
    public static int getRandomNumber(int minNum, int maxNum) {
        return (int) (Math.random() * (maxNum) + minNum);
    }

}
