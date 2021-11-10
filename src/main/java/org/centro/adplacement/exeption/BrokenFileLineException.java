package org.centro.adplacement.exeption;

public class BrokenFileLineException extends Exception
{
    public BrokenFileLineException(String message, Throwable err) {
        super(message, err);
    }
}
