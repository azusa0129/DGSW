package bxnd.sori.dto;

public record BaseRes<T> (
    boolean ok,
    String message,
    T data
) {}
