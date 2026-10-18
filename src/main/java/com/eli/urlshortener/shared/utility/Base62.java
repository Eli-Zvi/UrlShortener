package com.eli.urlshortener.shared.utility;

public record Base62(String base62Num) {

    private final static String base62Dic = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final static int base = 62;

    /**
     * Static Constructor using a Long
     * @param num
     * @return an instance of Base62 with the given num converted to Base62
     */
    public static Base62 encode(Long num){
        if(num < 0){
            throw new ArithmeticException("Invalid input, can't encode negative numbers to Base62");
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

        return new Base62(encodedNum.reverse().toString());
    }

    /**
     * Decodes the object's Base62 number to a Long
     * @return a decoded Base62 number in the form of base 10(Long)
     */
    public Long decode(){
        long result = 0L;
        int counter = 0;

        while(counter != this.base62Num.length()){
            char currentChar = this.base62Num.charAt(counter);
            int placeValue = (int) Math.pow(base, this.base62Num.length() - counter - 1); //placeValue offset by 1 to account for last char
            result +=  ((long) base62Dic.indexOf(currentChar) * (placeValue));
            counter++;
        }

        return result;
    }


}
