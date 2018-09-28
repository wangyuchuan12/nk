package com.ifrabbit.nk.express.service;

public class ProblemException extends RuntimeException{
    private static final long serialVersionUID = 1L;

   /**
    * errCode:错误码
    * @since Ver 1.1
    */
   private String errorCode;
    /**
     * getCode(取错误码)
     * @return
     */
    public String getErrorCode() {
        return errorCode;
    }

    public ProblemException() {
        super();
    }
    public ProblemException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ProblemException(Throwable cause) {
        super(cause);
    }
    public ProblemException(String message, Throwable cause) {
        super(message, cause);
    }
    public ProblemException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
