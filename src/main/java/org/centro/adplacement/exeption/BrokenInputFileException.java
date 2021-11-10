package org.centro.adplacement.exeption;

public class BrokenInputFileException extends Exception
{
    public BrokenInputFileException(String message, Throwable err) {
        super(message, err);
    }
}
