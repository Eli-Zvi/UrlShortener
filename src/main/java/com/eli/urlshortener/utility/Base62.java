package com.eli.urlshortener.utility;

public record Base62(String base62Num) {

    private final static String base62Dic = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final static int base = 62;

    public static String encode(Long num){
        if(num < 0){
            throw new ArithmeticException("Invalid input, can't encode negative numbers to Base62");
        }

        if(num < 62){
            return String.valueOf(base62Dic.charAt(num.intValue()));
        }

        StringBuilder encodedNum = new StringBuilder();
        int counter = 1;

        while(num > 0){
            int placeValue = (int) Math.pow(base, counter); //calculate the current place value
            int mod = Math.toIntExact(num % placeValue); //find the remainder
            int index = mod / (placeValue / 62); //divide the remainder by the base that it correlates to
            char encodedChar = base62Dic.charAt(index); //get the base62 char for the index
            encodedNum.append(encodedChar);

            num -= (index * (placeValue / 62));
            counter++;
        }

        return encodedNum.reverse().toString();
    }

    public static Long decode(String encodedNum){
        long result = 0L;
        int counter = 0;

        while(counter != encodedNum.length()){
            char currentChar = encodedNum.charAt(counter);
            int placeValue = (int) Math.pow(base, encodedNum.length() - counter - 1); //placeValue offset by 1 to account for last char
            result +=  ((long) base62Dic.indexOf(currentChar) * (placeValue));
            counter++;
        }

        return result;
    }


}
