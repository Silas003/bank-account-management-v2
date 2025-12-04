package com.exceptions;

public class CustomExceptions {
     public static class InsufficientFundsExceptions extends RuntimeException{
        public InsufficientFundsExceptions(String message){
            super(message);
        }
    }
    public static class CustomerNameException extends RuntimeException{
        public CustomerNameException(String message){
            super(message);
        }
    }

    public static class CustomerAgeException extends RuntimeException{
         public CustomerAgeException(String message){
             super(message);
         }
    }
    public static class CustomerContactException extends RuntimeException{
        public CustomerContactException(String message){
            super(message);
        }
    }

    public static class CustomerAddressException extends RuntimeException{
        public CustomerAddressException(String message){
            super(message);
        }
    }

    public static class TypeSelectionException extends RuntimeException{
        public TypeSelectionException(String message){
            super(message);
        }
    }

    public static class InvalidAccountException extends RuntimeException{
        public InvalidAccountException(String message){
            super(message);
        }
    }

    public static class OverdraftLimitException extends RuntimeException{
        public OverdraftLimitException(String message){
            super(message);
        }
    }
    public static class IllegalAmountException extends RuntimeException{
        public IllegalAmountException(String message){
            super(message);
        }
    }

}
