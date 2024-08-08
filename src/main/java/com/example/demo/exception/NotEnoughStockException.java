package com.example.demo.exception;

public class NotEnoughStockException extends RuntimeException {

    // Cntrl + O 오버라이딩 자동 완성
    // 예외 클래스를 새로 커스텀하기 위해 RuntimeException 클래스의 메서드들을 오버라이딩한다.

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

}
